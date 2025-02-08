package com.example.gochillge.domain.member.dto.request;

import com.example.gochillge.domain.member.entity.Member;
import com.example.gochillge.domain.member.entity.User;
import com.example.gochillge.domain.member.enumeration.AccountType;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserJoinRequest {

    private String nickname;
    private String password;
    private String name;
    private String room;
    private String number;
    private AccountType role;

    // Member 엔티티로부터 UserJoinRequest를 생성
    public static UserJoinRequest fromMember(Member member){
        return UserJoinRequest.builder()
                .nickname(member.getNickname())
                .password(member.getPassword())
                .name(member.getName())
                .number(member.getNumber())
                .role(AccountType.ROLE_USER) // 유저 권한 부여
                .build();
    }

    // User 엔티티로부터 UserJoinRequest를 생성
    public static UserJoinRequest fromUser(User user){
        return UserJoinRequest.builder()
                .room(user.getRoom()) // User에만 존재하는 room 정보
                .build();
    }
}
