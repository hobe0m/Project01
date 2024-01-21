package org.RentalProject.RentalService.configs;

import lombok.RequiredArgsConstructor;
import org.RentalProject.RentalService.commons.interceptors.CommonInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


// WebMvcConfigurer 인터페이스의 addInterceptors 메소드를 통해 인터셉터 추가

// @Configuration : 설정 클래스임을 나타냄, 여기서는 인터셉터의 설정 클래스
@Configuration
@RequiredArgsConstructor
public class InterceptorConfig implements WebMvcConfigurer {

    // CommonInterceptor 필드 생성(의존성 주입을 위함, 객체 간 관계 형성)
    private final CommonInterceptor commonInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // commonInterceptor(사용자가 정의한 인터셉터 객체)를 registy에 등록
        registry.addInterceptor(commonInterceptor);

        // "/**"는 모든 경로를 의미하는데, 따로 쓰지 않아도 모든 주소가 기본값이다.
        // 내가 추가한 commonInterceptor에 정의된 작업들이 모든 요청에 대해 수행하게끔 한다.
        // 전이나 후에 수행(지정해야 한다)
    }
}