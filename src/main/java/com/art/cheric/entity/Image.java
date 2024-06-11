package com.art.cheric.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@Table(name = "Image")
@NoArgsConstructor
@Getter
@EntityListeners(AuditingEntityListener.class)
public class Image extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "art_id")
    @Comment("작품")
    private Art art;

    @Column(name = "file", nullable = false)
    @Comment("파일")
    private String file;

    @Column(name = "filePath", nullable = false)
    @Comment("파일 저장 경로")
    private String filePath;

    @Column(name = "fileSize", nullable = false)
    @Comment("파일 사이즈")
    private Long fileSize;

    @Builder
    public Image(Art art, String file, String filePath, Long fileSize) {
        this.art = art;
        this.file = file;
        this.filePath = filePath;
        this.fileSize = fileSize;
    }

}
