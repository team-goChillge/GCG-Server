package com.example.gochillge.domain.member.dto.request;

import com.example.gochillge.domain.member.entity.Member;
import com.example.gochillge.domain.member.enumeration.AccountType;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminJoinRequest {

    private String nickname;
    private String password;
    private String name;
    private String number;
    private AccountType role;

    public static UserJoinRequest of(Member member){
        return UserJoinRequest.builder()
                .nickname(member.getNickname())
                .password(member.getPassword())
                .name(member.getName())
                .number(member.getNumber())
                .role(AccountType.ROLE_ADMIN) // 유저 권한 부여
                .build();
    }

}
