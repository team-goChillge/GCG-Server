package com.example.gochillge.domain.member.controller;

import com.example.gochillge.domain.member.dto.request.AdminJoinRequest;
import com.example.gochillge.domain.member.dto.request.LoginRequest;
import com.example.gochillge.domain.member.dto.request.RefreshTokenReq;
import com.example.gochillge.domain.member.dto.request.UserJoinRequest;
import com.example.gochillge.domain.member.dto.response.LoginResponse;
import com.example.gochillge.domain.member.dto.response.RefreshTokenRes;
import com.example.gochillge.domain.member.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@ResponseBody
@Tag(name = "auth", description = "로그인, 회원가입")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/join/user")
    @Operation(summary = "유저 회원가입", description = "유저 회원가입을 합니다.")
    public ResponseEntity<String> joinUser(@Valid @RequestBody UserJoinRequest userJoinRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("유효성 검사 오류: " + bindingResult.getAllErrors());
        }
        try {
            authService.joinUser(userJoinRequest);
            return ResponseEntity.ok("회원가입을 성공적으로 완료했습니다.");
        } catch (Exception e) {
            e.printStackTrace(); // 예외 스택 트레이스를 콘솔에 출력
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("회원가입을 하는 도중 오류가 발생했습니다.");
        }
    }

    @PostMapping("/join/admin")
    @Operation(summary = "관리자 회원가입", description = "관리자 회원가입을 합니다.")
    public ResponseEntity<String> joinAdmin(@Valid @RequestBody AdminJoinRequest adminJoinRequest) {
        try {
            authService.joinAdmin(adminJoinRequest);
            return ResponseEntity.ok("회원가입을 성공적으로 완료했습니다.");
        } catch (Exception e) {
            e.printStackTrace(); // 예외 스택 트레이스를 콘솔에 출력
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("회원가입을 하는 도중 오류가 발생했습니다.");
        }
    }

    @PostMapping("/login")
    @Operation(summary = "로그인", description = "유저, 관리자으로 로그인 합니다.")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {

        LoginResponse loginResponse = authService.login(loginRequest);
        return ResponseEntity.status(HttpStatus.OK).body(loginResponse);
    }

    @PostMapping("/refresh")
    @Operation(summary = "액세스 토큰 얻기", description = "리프레쉬 토큰으로 엑세스 토큰을 얻습니다.")
    public RefreshTokenRes refresh(@Validated @RequestBody RefreshTokenReq refreshTokenReq){
        return authService.refresh(refreshTokenReq);
    }
}
