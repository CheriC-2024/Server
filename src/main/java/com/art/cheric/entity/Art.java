package com.art.cheric.entity;

import com.art.cheric.constant.ArtStatus;
import com.art.cheric.constant.MaterialSort;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@EntityListeners(value = {AuditingEntityListener.class})
@Table(name = "Art")
@Getter
@NoArgsConstructor
public class Art extends BaseTime {
    @Id
    @Column(name = "art_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 50, nullable = false)
    @Comment("작품 이름")
    private String name;

    @OneToMany(mappedBy = "art", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private List<Image> imageList = new ArrayList<>();

    @Column(name = "series", length = 30)
    @Comment("작품 시리즈")
    private String series;

    @Column(name = "description", length = 500)
    @Comment("작품 설명")
    private String description;

    @Column(name = "widthSize")
    @Comment("작품 가로 사이즈")
    private Integer widthSize;

    @Column(name = "heightSize")
    @Comment("작품 세로 사이즈")
    private Integer heightSize;

    @Column(name = "materialStatus")
    @Comment("작품 사용 재료")
    private MaterialSort materialStatus;

    @Column(name = "artStatus")
    @Comment("작품 사용 권한")
    @Enumerated(EnumType.STRING)
    private ArtStatus artStatus;

    @Column(name = "notice", length = 1000)
    @Comment("작품 이용 주의사항")
    private String notice;

    @Column(name = "cherryNum", nullable = false)
    @Comment("체리 가격")
    @ColumnDefault("0")
    private int cherryNum;

    @Builder
    public Art(String name, List<Image> imageList, String series, String description,
               Integer widthSize, Integer heightSize, MaterialSort materialStatus,
               ArtStatus artStatus, String notice, int cherryNum) {
        this.name = name;
        this.imageList = imageList;
        this.series = series;
        this.description = description;
        this.widthSize = widthSize;
        this.heightSize = heightSize;
        this.materialStatus = materialStatus;
        this.artStatus = artStatus;
        this.notice = notice;
        this.cherryNum = cherryNum;
    }

    public void addImageList(List<Image> imageList) {
        this.imageList.addAll(imageList);
    }

    public void addImage(Image image) {
        if (this.imageList == null) {
            this.imageList = new ArrayList<>();
        }
        this.imageList.add(image);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Art art)) return false;

        return Objects.equals(this.id, art.getId()) &&
                Objects.equals(this.name, art.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
