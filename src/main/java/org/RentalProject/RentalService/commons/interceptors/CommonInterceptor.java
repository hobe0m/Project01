package org.RentalProject.RentalService.commons.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
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
     * device : get형식의 쿼리스트링 값이 들어오면 PC와 MOBILE 구분
     * PC 일 때 : PC 뷰,  -  MOBILE 일 때 : MOBILE 뷰 수동 지정
     * 쿼리스트링 : url뒤에 ?device=mobile를 적어 모바일로 수동 변경 가능(대소문자 구분 X)
     * @param request
     */
    // 세션도 처리해야 하고 값도 받아와야 하기 때문에 request를 사용
    // 직접 request를 통해 파라미터 값을 가져와야 한다.
    // checkDevice 메소드에 HttpServletRequest request를 통해 객체를 가져와 참조
    // 메소드에 매개변수에 객체를 넣는 것은 일반적인 방법이고, 호출 시 객체 기능 사용 가능
    // 객체를 정의한 클래스(설계도) 내부에 필드(속성)과 메소드 사용 가능

    // StringUtils.hasText : 값이 있는 것을 찾으므로, 반대는 null x, 없음 x
    // return으로 값이 없을 때는 메소드를 끝낸다.
    private void checkDevice(HttpServletRequest request) {
        String device = request.getParameter("device");
        if (!StringUtils.hasText(device)) {
            return;
        }

        // 값이 있을 때, session 가지고 값을 넣어준다.
        // 대문자로 바꾼 후, 모바일인지 PC인 지 확인
        device = device.toUpperCase().equals("MOBILE") ? "MOBILE" : "PC";

        // HttpSession 객체를 request.getSession()를 통해 가져와서
        // session이라는 변수에 넣었다.
        // session의 메소드인 setAttribute를 통해 앞서 처리한 device의 이름을
        // device로 저장한다.
        HttpSession session = request.getSession();

        // setAttribute(String name(속성명), Object value(값))이 기본
        session.setAttribute("device", device);
    }
}