package org.RentalProject.RentalService.commons;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

// 편의 기능 만들기

// @Component : Spring이 관리하는 객체라는 것을 알린다.
@Component
@RequiredArgsConstructor
public class Utils {
    // 모바일과 PC는 헤더 정보를 보고 구분할 수 있다.
    // 개발자 모드 - 네트워크 - 도메인(www.naver.com) - 헤더 - 요청 헤더 - User-Agent로 구분
    // 사용중인 장비가 나타나고, 크롬의 버전도 나타나기 때문에 확인할 수 있다.
    // . : 문자 1개, .* : 0개 이상(앞뒤에 문자가 있어도 되고, 없어도 된다.), .+(앞뒤에 문자가 한 개 이상 있어야 한다.)
    // .* : 최소 매칭 , .+ : 최대 매칭
    // ".*(iphone|ipod|ipad|BlackBerry|Android|Windows CE|LG|MOT|SAMSUNG|SonyEricsson)*"
    // 위의 것들이 포함되면 모바일이다.

    // 이 클래스 내에서만 접근 가능하고 불변 속성을 가진 필드 생성
    // 보통 Servlet 클래스가 사용하며, 클라이언트의 HTTP 요청에 대한 정보를 담는다.
    // 따라서 HTTP 요청에 대한 다양한 정보를 처리하고 응답을 생성할 수 있다.
    // 예를 들어 클라이언트가 요청한 정보를 읽거나, 요청에 포함된 파라미터 확인 등에 사용된다.
    private final HttpServletRequest request;

    public boolean isMobile() {
        // 요청 헤더 - User-Agent 내용을 가지고 파악
        // User-Agent의 요청 헤더를 가져와서 모바일을 알리는 정규 표현식이 있으면 모바일 장비로 판단
        String ua = request.getHeader("User-Agent");

        // 대소문자 구분 확실히 하기
        String pattern = ".*(iPhone|iPod|iPad|BlackBerry|Android|Windows CE|LG|MOT|SAMSUNG|SonyEricsson).*";

        boolean isMobile = ua.matches(pattern);

        return isMobile;
    }

    // tpl = templates
    public String tpl(String path) {
        // 앞에 front나 mobile을 붙이지 않고, 이 메소드를 통해 모바일과 PC 구분
        // 여기서의 prefix : 빈 문자열을 알리는 변수, 할당된 값을 문자열에 넣는다.
        // prefix가 isMobile()에 부합하면 "mobile/" + path 아니면 "front/" + path
        String prefix = isMobile() ? "mobile/" : "front/";

        return prefix + path;
    }
}
