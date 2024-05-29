package com.art.cheric.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Objects;


@Entity
@EntityListeners(value = {AuditingEntityListener.class})
@Table(name = "ExhibitionArt")
@NoArgsConstructor
@Getter
public class ExhibitionArt extends BaseTime {
    @Id
    @Column(name = "exhibition_art_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "exhibition_id", nullable = false)
    @Comment("전시")
    private Exhibition exhibition;

    @OneToOne
    @JoinColumn(name = "art_id", nullable = false)
    @Comment("전시 작품")
    private Art art;

    @Builder
    public ExhibitionArt(Exhibition exhibition, Art art) {
        this.exhibition = exhibition;
        this.art = art;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof ExhibitionArt exhibitionArt)) return false;

        return Objects.equals(this.id, exhibitionArt.getId()) &&
                Objects.equals(this.art, exhibitionArt.getArt());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, art);
    }

}