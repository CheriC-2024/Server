package com.art.cheric.entity;

import com.art.cheric.constant.ArtStatus;
import com.art.cheric.constant.MaterialSort;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Objects;

@Entity
@EntityListeners(value = {AuditingEntityListener.class})
@Table(name = "Art")
@NoArgsConstructor
@Getter
public class Art extends BaseTime {
    @Id
    @Column(name = "art_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 50, nullable = false)
    @Comment("작품 이름")
    private String name;

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

    @Column(name = "materialStatus", nullable = false)
    @Comment("작품 사용 재료")
    private MaterialSort materialStatus;

    @Column(name = "materialStatus", nullable = false)
    @Comment("작품 사용 권한")
    @Enumerated(EnumType.STRING)
    private ArtStatus artStatus;

    @Column(name = "notice", length = 1000)
    @Comment("작품 이용 주의사항")
    private String notice;

    @Builder
    public Art(String name, String series, String description,
               Integer widthSize, Integer heightSize, MaterialSort materialStatus,
               ArtStatus artStatus, String notice, boolean isSomeoneOwn) {
        this.name = name;
        this.series = series;
        this.description = description;
        this.widthSize = widthSize;
        this.heightSize = heightSize;
        this.materialStatus = materialStatus;
        this.artStatus = artStatus;
        this.notice = notice;
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
