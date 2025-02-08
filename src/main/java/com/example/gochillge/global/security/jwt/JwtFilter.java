package com.example.gochillge.global.security.jwt;

import com.example.gochillge.domain.member.service.MemberDetailsService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final MemberDetailsService memberDetailsService;
    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");

        // Authorization 헤더가 없거나 Bearer로 시작하지 않는 경우
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);  // 다음 필터로 넘기기
            return;
        }

        String token = authorizationHeader.substring(7); // "Bearer " 부분을 제거

        try {
            // JWT 유효성 검증
            if (jwtUtil.validateToken(token)) {
                String email = jwtUtil.getMemberNickname(token);

                // 유저와 토큰 일치 시 userDetails 생성
                UserDetails userDetails = memberDetailsService.loadUserByUsername(email);

                if (userDetails != null) {
                    // UsernamePasswordAuthenticationToken 생성
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                    // SecurityContext에 접근 권한 설정
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
        } catch (ExpiredJwtException e) {
            logger.warn("JWT Token is expired", e);
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "JWT Token is expired");
            return;  // 예외 처리 후 더 이상 진행하지 않음
        } catch (MalformedJwtException | UnsupportedJwtException e) {
            logger.error("Invalid JWT Token", e);
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT Token");
            return;  // 예외 처리 후 더 이상 진행하지 않음
        } catch (Exception e) {
            logger.error("Authentication failed", e);
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication failed");
            return;  // 예외 처리 후 더 이상 진행하지 않음
        }

        filterChain.doFilter(request, response); // 정상적인 경우, 다음 필터로 넘기기
    }
}
