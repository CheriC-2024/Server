package com.art.cheric.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Objects;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "ArtistArt")
@NoArgsConstructor
@Getter
public class ArtistArt extends BaseTime {
    @Id
    @Column(name = "artist_art_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "art_id")
    @Comment("작가 등록 작품")
    private Art art;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @Comment("작가")
    private User user;

    @Column(name = "artist_description", length = 500)
    @Comment("작가의 작품 설명")
    private String artistDescription;

    @Column(name = "artist", length = 30, nullable = false)
    @Comment("저작권자")
    private String copyright;

    @Column(name = "notice", length = 1000, nullable = false)
    @Comment("작품 이용 주의사항")
    private String notice;

    @Builder
    public ArtistArt(Art art, User user, String artistDescription, String copyright, String notice) {
        this.art = art;
        this.user = user;
        this.artistDescription = artistDescription;
        this.copyright = copyright;
        this.notice = notice;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof MyArt myArt)) return false;

        return Objects.equals(this.user, myArt.getUser()) &&
                Objects.equals(this.art, myArt.getArt());
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, art);
    }

}
