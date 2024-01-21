package org.RentalProject.RentalService.configs;

import org.RentalProject.RentalService.member.service.LoginFailureHandler;
import org.RentalProject.RentalService.member.service.LoginSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


// 스프링 시큐리티 설정
@Configuration // 설정 클래스임을 표시
public class SecurityConfig {

    // 무력화
    // localhost:3000을 검색했을 때, 시큐리티에 의한 로그인 화면이 뜨지 않게 한다.
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        /* 인증 설정 S - 로그인 */

        // 시큐리티는 인증(로그인)과 인가(주소 접근 시 통제, 회원만 혹은 관리자만)가 핵심
        // formLogin 내부에 있는 모든 것은 로그인 관련된 설정을 하는 것
        // 람다식 형태 사용이 도메인 특화 언어 사용의 중요한 부분이다.
        // 바뀔 수 있는 부분(5가지 설정)들은 직접 설정을 해주어야 한다.
        http.formLogin(f -> {
            // 로그인 페이지를 처리할 url
            f.loginPage("/member/login")
                    // 아이디 혹은 이메일 즉, 이름
                    // 다른 사람이 썼을 때 바뀔 수 있으니까 username으로 지정
                    .usernameParameter("username")
                    // 패스워드
                    // 다른 사람이 썼을 때 바뀔 수 있으니까 password으로 지정
                    .passwordParameter("password")
                    // 성공시 이동할 url
                    // 상세하게 설정하기 위해 .defaultSuccessUrl("/")를 지우고
                    .successHandler(new LoginSuccessHandler()) // 사용
                    // 실패시 이동할 url
                    // 상세하게 설정하기 위해 .failureUrl("/member/login?error=true");를 지우고
                    .failureHandler(new LoginFailureHandler()); // 사용

            // 간단하게 url만 연동해서 설정할 수도 있지만, handler를 사용하면 더 상세하게
            // 설정할 수 있다.
            // 뭐가 문제인지도 알려주기 위해, handler를 사용한다.

            // 사용하기 위한 인터페이스 정의
            // 성공 시 - SuccessHandler 메소드
            //            : AuthenticationSuccessHadler 인터페이스의 구현체를 만들어주면 된다.
            // 실패 시 - failureHandler 메소드
            //            : AuthenticationFailureHadler 인터페이스의 구현체를 만들어주면 된다.

        });
        /* 인증 설정 E - 로그인 */

        return http.build();
    }

    // 비밀번호 해시화를 위한 메소드 생성
    // 비밀번호 해시화도 스프링 시큐리티에 탑재되어 있어 가져와서 쓴다.
    // 관리 객체 일때만, 의존성 주입이 가능하므로 @Bean 사용
    // BCrypt를 사용하기 위해 BCryptPasswordEncoder 객체 생성 후 빈으로 등록 
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 레이어 팝업 S //
    public SecurityFilterChain filterChain1(HttpSecurity http) throws Exception {


        /* 같은 서버 자원 주소는 iframe 허용 처리 */
        http.headers(c -> c.frameOptions(f -> f.sameOrigin()));

        return http.build();
    }
    // 레이어 팝업 E //
}

// 왜 로그인 페이지가 계속 나오는지 질문, 무력화 구현 X