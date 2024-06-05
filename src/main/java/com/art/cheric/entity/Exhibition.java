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
@Table(name = "Exhibition")
@NoArgsConstructor
@Getter
public class Exhibition extends BaseTime {
    @Id
    @Column(name = "exhibition_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 30, nullable = false, unique = true)
    @Comment("전시 이름")
    private String name;

    @Column(name = "description", length = 100, nullable = false, unique = true)
    @Comment("전시 설명")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @Comment("전시 제작자")
    private User user;

    @OneToMany(mappedBy = "exhibition", cascade = CascadeType.ALL, orphanRemoval = true)
    @Comment("전시 작품 리스트")
    private List<ExhibitionArt> exhibitionArtList = new ArrayList<>();

    @Builder
    public Exhibition(String name, String description, User user, List<ExhibitionArt> exhibitionArtList) {
        this.name = name;
        this.description = description;
        this.user = user;
        this.exhibitionArtList = exhibitionArtList;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Exhibition exhibition)) return false;

        return Objects.equals(this.user, exhibition.getUser()) &&
                Objects.equals(this.name, exhibition.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, name);
    }
}
