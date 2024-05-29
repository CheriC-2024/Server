package com.art.cheric.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(value = {AuditingEntityListener.class})
@Table(name = "CherryArt")
@NoArgsConstructor
@Getter
public class CherryArt extends BaseTime {
    @Id
    @Column(name = "cherry_art_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "art_id", nullable = false)
    @Comment("소유 작품")
    private Art art;

    @Column(name = "cherryNum", nullable = false)
    @Comment("체리 개수")
    private int cherryNum;
}
