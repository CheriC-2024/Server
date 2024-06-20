package com.art.cheric.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Objects;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "CollectionArt")
@NoArgsConstructor
@Getter
public class CollectionArt extends BaseTime {
    @Id
    @Column(name = "collection_art_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "collection_id")
    @Comment("컬렉션")
    private Collection collection;

    @ManyToOne
    @JoinColumn(name = "art_id")
    @Comment("컬렉션 작품")
    private Art art;

    @Builder
    public CollectionArt(Collection collection, Art art) {
        this.collection = collection;
        this.art = art;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof CollectionArt collectionArt)) return false;

        return Objects.equals(this.id, collectionArt.getId()) &&
                Objects.equals(this.art, collectionArt.getArt())&&
                Objects.equals(this.collection, collectionArt.getCollection());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, art, collection);
    }

}