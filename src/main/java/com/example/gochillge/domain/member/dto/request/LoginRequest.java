package com.example.gochillge.domain.member.dto.request;

import com.example.gochillge.domain.member.entity.Member;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    private String nickname;
    private String password;

    public static LoginRequest of(Member member){
        return LoginRequest.builder()
                .nickname(member.getNickname())
                .password(member.getPassword())
                .build();
    }
}
