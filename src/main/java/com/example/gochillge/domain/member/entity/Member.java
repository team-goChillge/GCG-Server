package com.example.gochillge.domain.member.entity;

import com.example.gochillge.domain.member.enumeration.AccountType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @JsonIgnore
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    @NotNull
    private String name;

    @Column(nullable = false)
    private String number;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AccountType role;

    @Builder
    public Member(String nickname, String password, String name, String number, AccountType role) {
        this.nickname = nickname;
        this.password = password;
        this.name = name;
        this.number = number;
        this.role = role;

    }


}
