package org.RentalProject.RentalService.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;


// 스프링 시큐리티 설정
@Configuration // 설정 클래스임을 표시
public class SecurityConfig {

    // 무력화
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        return http.build();
    }
}

// 왜 로그인 페이지가 계속 나오는지 질문, 무력화 구현 X