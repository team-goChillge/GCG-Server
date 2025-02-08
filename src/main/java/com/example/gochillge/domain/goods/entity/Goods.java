package com.example.gochillge.domain.goods.entity;

import com.example.gochillge.domain.member.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_goods")
public class Goods {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private String thing;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Goods(String thing, LocalDateTime createdAt) {
        this.thing = thing;
        this.createdAt = createdAt;
    }
}
