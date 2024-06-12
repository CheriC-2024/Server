package com.art.cheric.entity;

import com.art.cheric.constant.MaterialSort;
import com.art.cheric.constant.PartSort;
import com.art.cheric.constant.Role;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@EntityListeners(AuditingEntityListener.class)
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

    @OneToMany(mappedBy = "art", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> imageList = new ArrayList<>();

    @Column(name = "artist", length = 30, nullable = false)
    @Comment("작가 이름")
    private String artist;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "UTC")
    @Column(name = "madeAt", nullable = false)
    @Comment("제작 시기")
    private Date madeAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "partSort", nullable = false)
    @Comment("작품 장르")
    private PartSort partSort;

    @Column(name = "series", length = 30, nullable = false)
    @Comment("작품 시리즈")
    private String series;

    @Column(name = "description", length = 500, nullable = false)
    @Comment("작품 설명")
    private String description;

    @Column(name = "widthSize", nullable = false)
    @Comment("작품 가로 사이즈")
    private Integer widthSize;

    @Column(name = "heightSize", nullable = false)
    @Comment("작품 세로 사이즈")
    private Integer heightSize;

    @Column(name = "materialStatus", nullable = false)
    @Comment("작품 사용 재료")
    private MaterialSort materialStatus;

    @Column(name = "register", nullable = false)
    @Comment("작품 등록")
    private Role register;

    @Column(name = "cherryNum")
    @Comment("체리 가격")
    private Integer cherryNum;

    @Builder
    public Art(String name, String artist, Date madeAt, PartSort partSort, List<Image> imageList, String series, String description,
               Integer widthSize, Integer heightSize, MaterialSort materialStatus, Integer cherryNum, Role register) {
        this.name = name;
        this.artist = artist;
        this.madeAt = madeAt;
        this.partSort = partSort;
        this.imageList = imageList;
        this.series = series;
        this.description = description;
        this.widthSize = widthSize;
        this.heightSize = heightSize;
        this.materialStatus = materialStatus;
        this.cherryNum = cherryNum;
        this.register = register;
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
