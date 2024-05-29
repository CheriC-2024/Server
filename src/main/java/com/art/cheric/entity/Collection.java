package com.art.cheric.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@EntityListeners(value = {AuditingEntityListener.class})
@Table(name = "Collection")
@NoArgsConstructor
@Getter
public class Collection extends BaseTime {
    @Id
    @Column(name = "collection_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 50, nullable = false, unique = true)
    @Comment("컬렉션 이름")
    private String name;

    @Column(name = "description", length = 500, nullable = false)
    @Comment("컬렉션 설명")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @Comment("컬렉션 제작자")
    private User user;

    @OneToMany(mappedBy = "collection", cascade = CascadeType.ALL, orphanRemoval = true)
    @Comment("컬렉션 작품 리스트")
    private List<CollectionArt> collectionArtList = new ArrayList<>();

    @Builder
    public Collection(String name, String description, User user, List<CollectionArt> collectionArtList) {
        this.name = name;
        this.description = description;
        this.user = user;
        this.collectionArtList = collectionArtList;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Collection collection)) return false;

        return Objects.equals(this.user, collection.getUser()) &&
                Objects.equals(this.name, collection.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, name);
    }
}