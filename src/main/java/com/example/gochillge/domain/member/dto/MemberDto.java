package com.example.gochillge.domain.member.dto;

import com.example.gochillge.domain.member.entity.Member;
import com.example.gochillge.domain.member.enumeration.AccountType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberDto {

    private String nickname;
    private String name;
    private AccountType role;

    // 비밀번호를 제외한 MemberDto 생성
    public static MemberDto of(Member member){
        return MemberDto.builder()
                .nickname(member.getNickname())
                .name(member.getName())
                .role(member.getRole())
                .build();
    }
}
