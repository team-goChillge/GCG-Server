package com.example.gochillge.global.security.config;

import com.example.gochillge.domain.member.service.MemberDetailsService;
import com.example.gochillge.global.security.jwt.JwtFilter;
import com.example.gochillge.global.security.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;


import static com.example.gochillge.domain.member.enumeration.AccountType.ROLE_ADMIN;
import static com.example.gochillge.domain.member.enumeration.AccountType.ROLE_USER;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtUtil jwtUtil;
    private final MemberDetailsService memberDetailsService;

    @Bean
    public JwtFilter jwtFilter(){
        return new JwtFilter(memberDetailsService, jwtUtil);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors((corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource() {

                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {

                        CorsConfiguration configuration = new CorsConfiguration();

                        configuration.setAllowedOrigins(Collections.singletonList("*"));
                        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
//                        configuration.setAllowCredentials(true);
                        configuration.setAllowedHeaders(Collections.singletonList("*"));
                        configuration.setMaxAge(3600L);

                        configuration.setExposedHeaders(Collections.singletonList("Authorization"));

                        return configuration;
                    }
                })));

        http
                .csrf(AbstractHttpConfigurer::disable)  // CSRF 비활성화

                .authorizeHttpRequests((configurer) -> configurer
                        .requestMatchers("/auth/**").anonymous()
                        .requestMatchers("/users/**").hasRole(ROLE_USER.getRole())
                        .requestMatchers("/admins/**", "mains/**").hasRole(ROLE_ADMIN.getRole())
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                )

                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)  // 세션 사용 안함
                )
                .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }

}
