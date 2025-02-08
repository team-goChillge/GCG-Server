package com.example.gochillge.domain.member.service;

import com.example.gochillge.domain.member.dto.MemberDto;
import com.example.gochillge.domain.member.dto.request.AdminJoinRequest;
import com.example.gochillge.domain.member.dto.request.LoginRequest;
import com.example.gochillge.domain.member.dto.request.RefreshTokenReq;
import com.example.gochillge.domain.member.dto.request.UserJoinRequest;
import com.example.gochillge.domain.member.dto.response.LoginResponse;
import com.example.gochillge.domain.member.dto.response.RefreshTokenRes;
import com.example.gochillge.domain.member.entity.Member;
import com.example.gochillge.domain.member.entity.User;
import com.example.gochillge.domain.member.enumeration.AccountType;
import com.example.gochillge.domain.member.repo.AdminRepository;
import com.example.gochillge.domain.member.repo.MemberRepository;
import com.example.gochillge.domain.member.repo.UserRepository;
import com.example.gochillge.global.security.jwt.JwtUtil;
import com.example.gochillge.global.security.jwt.TokenType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtUtil jwtUtil;
    private final MemberRepository memberRepository;
    private final PasswordEncoder encoder;
    private final AdminRepository adminRepository;
    private final UserRepository userRepository;

    // 중복 로직을 공통 메소드로 리팩토링
    private Member createMember(String password, String name, AccountType role) {
        return Member.builder()
                .password(encoder.encode(password))
                .name(name)
                .role(role)
                .build();
    }

    private User createUser(Member member, String room) {
        return User.builder()
                .member(member)
                .room(room)
                .build();
    }

    // 중복된 아이디 체크를 위한 공통 메소드
    private void checkNicknameExists(String nickname) {
        memberRepository.findByNickname(nickname)
                .ifPresent(member -> {
                    throw new UsernameNotFoundException("이미 존재하는 아이디입니다.");
                });
    }

    // 유저 회원가입
    public void joinUser(UserJoinRequest joinRequest) {

        if (joinRequest.getNumber() == null || joinRequest.getNumber().trim().isEmpty()) {
            throw new IllegalArgumentException("Number cannot be null or empty");
        }
        checkNicknameExists(joinRequest.getNickname());

        Member member = createMember(joinRequest.getPassword(), joinRequest.getName(), AccountType.ROLE_USER);
        memberRepository.save(member);

        User user = createUser(member, joinRequest.getRoom());
        userRepository.save(user);
    }

    // 관리자 회원가입
    public void joinAdmin(AdminJoinRequest joinRequest) {
        checkNicknameExists(joinRequest.getNickname());

        Member member = createMember(joinRequest.getPassword(), joinRequest.getName(), AccountType.ROLE_ADMIN);
        memberRepository.save(member);

        User user = createUser(member, null);  // Admin은 room 정보가 필요없을 수 있음
        userRepository.save(user);
    }

    // 로그인 처리
    public LoginResponse login(LoginRequest loginRequest){
        String nickname = loginRequest.getNickname();
        String password = loginRequest.getPassword();

        Member member = memberRepository.findByNickname(nickname)
                .orElseThrow(() -> new UsernameNotFoundException("아이디가 존재하지 않습니다."));

        if (!encoder.matches(password, member.getPassword())) {
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
        }

        MemberDto memberDto = MemberDto.of(member);

        String accessToken = jwtUtil.generateAccessToken(memberDto, jwtUtil.getAccessTokenExpTime());
        String refreshToken = jwtUtil.generateRefreshToken(memberDto, jwtUtil.getRefreshTokenExpTime());

        return new LoginResponse(accessToken, refreshToken);
    }

    // 리프레시 토큰 처리
    public RefreshTokenRes refresh(RefreshTokenReq refreshTokenReq) {
        Jws<Claims> claims = jwtUtil.parseClaims(jwtUtil.extractToken(refreshTokenReq.getRefreshToken()));

        if (jwtUtil.isWrongType(claims, TokenType.REFRESH)) {
            throw new IllegalArgumentException("잘못된 토큰.");
        }

        MemberDto member = memberRepository.findByNickname(jwtUtil.getMemberNickname(refreshTokenReq.getRefreshToken()))
                .map(MemberDto::of)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저"));

        return RefreshTokenRes.builder()
                .accessToken(jwtUtil.generateAccessToken(member, jwtUtil.getAccessTokenExpTime()))
                .build();
    }
}
