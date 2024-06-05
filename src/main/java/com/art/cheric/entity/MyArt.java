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
@Table(name = "MyArt")
@NoArgsConstructor
@Getter
public class MyArt extends BaseTime {
    @Id
    @Column(name = "my_art_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "art_id")
    @Comment("소유 작품")
    private Art art;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @Comment("작품 소유자")
    private User user;

    @Builder
    public MyArt(Art art, User user) {
        this.art = art;
        this.user = user;
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
