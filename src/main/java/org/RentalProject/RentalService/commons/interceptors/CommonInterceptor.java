package org.RentalProject.RentalService.commons.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

// @Component : Spring에서 관리하는 객체라는 것을 알린다.
@Component
// implements : 인터페이스를 구현
public class CommonInterceptor implements HandlerInterceptor {

    // 부모 인터페이스의 preHandle 메소드 호출, ()안에는 매개변수(요청, 응답, 처리)
    // return을 true로 바꿈으로써 모든 클라이언트의 요청이 컨트롤러로 전달되도록 허용
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        checkDevice(request);

        return true;
    }

    /**
     * PC, 모바일 수동 변경 처리
     *
     * @param request
     */
    // 세션도 처리해야 하고 값도 받아와야 하기 때문에 request를 사용
    private void checkDevice(HttpServletRequest request) {
        
    }
}
