package com.art.cheric.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;
import java.util.Objects;

@Entity
@EntityListeners(AuditingEntityListener.class)
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

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "UTC")
    @Column(name = "gotten_date")
    @Comment("소장 일시")
    private Date gottenDate;

    @Column(name = "gotten_path", length = 30)
    @Comment("소장 경로")
    private String gottenPath;

    @Column(name = "gotten_price", length = 30)
    @Comment("소장 가격")
    private String gottenPrice;


    @Builder
    public MyArt(Art art, User user, Date gottenDate, String gottenPath, String gottenPrice) {
        this.art = art;
        this.user = user;
        this.gottenDate = gottenDate;
        this.gottenPath = gottenPath;
        this.gottenPrice = gottenPrice;
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
