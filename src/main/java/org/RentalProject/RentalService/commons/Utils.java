package org.RentalProject.RentalService.commons;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ResourceBundle;

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

    // 세션값에 모바일이 있을 때는 PC이더라도 모바일 뷰로 볼 수 있게 강제로 수동 전환
    // 따라서 세션 의존성 주입
    // request를 사용해서 바로 받아와도 된다.
    private final HttpSession session;

    // 메세지를 가져오기 위해 의존성 주입, 바꾸지 않으니까 static
    // static은 객체를 만들지 않아도 클래스가 로드될 때 실행된다.
    // 편하게 쓰기 위해서 즉, 객체를 만들지 안하도 메소드를 사용할 수 있게 정적 메소드로 추가
    // 따로 객체를 만들지 않아도 조회가 가능하다.
    private static final ResourceBundle commonsBundle;
    private static final ResourceBundle validationsBundle;
    private static final ResourceBundle errorsBundle;

    // static쪽 초기화, 처음부터 할 수 있다.
    // 객체를 만들지 않아도 클래스가 로드돨 때 시작되기 때문에
    static {
        commonsBundle = ResourceBundle.getBundle("messages.commons");
        validationsBundle = ResourceBundle.getBundle("messages.validations");
        errorsBundle = ResourceBundle.getBundle("messages.errors");
    }

    public boolean isMobile() {
        // 모바일 수동 전환 모드 체크
        // getAttribute(String name(속성의 이름), 기본 매개변수가 속성의 이름
        // 가져올 속성의 이름을 나타내고 존재하면 그 값을 반환하고, 존재하지 않으면
        // null 값을 반환한다.
        // 따라서 여기서는 device를 가져와서 값을 반환한다.
        // 기존에 저장된 setAttribute의 값에서만 찾는다.
        String device = (String)session.getAttribute("device");

        // device라는 문자열이 있다면, 문자열이 MOBILE과 같은지 비교해서 true 혹은
        // false를 반환해라(equals 메소드의 기본 반환타입이 boolean)
        if (StringUtils.hasText(device)) {
            return device.equals("MOBILE");
        }

        
        // 요청 헤더 - User-Agent 내용을 가지고 파악
        // User-Agent의 요청 헤더를 가져와서 모바일을 알리는 정규 표현식이 있으면 모바일 장비로 판단
        String ua = request.getHeader("User-Agent");

        // 대소문자 구분 확실히 하기
        String pattern = ".*(iPhone|iPod|iPad|BlackBerry|Android|Windows CE|LG|MOT|SAMSUNG|SonyEricsson).*";

        /*
        boolean isMobile = ua.matches(pattern);

        return isMobile;

        위쪽 코드의 변화(수동전환 추가)로 간결하게 return문 작성
        생략된 코드는 같은 가독성을 높이고 isMobile을 다른데서도 쓸 수 있는 장점이 있지만
        간결함이 떨어진다.
        */

        // device 값이 없으면, 기기의 종류에 따라 MOBILE과 PC를 분류
        // 수동전환되면 값에 따라 바뀐다.
        return ua.matches(pattern);
    }

    // tpl = templates
    public String tpl(String path) {
        // 앞에 front나 mobile을 붙이지 않고, 이 메소드를 통해 모바일과 PC 구분
        // 여기서의 prefix : 빈 문자열을 알리는 변수, 할당된 값을 문자열에 넣는다.
        // prefix가 isMobile()에 부합하면 "mobile/" + path 아니면 "front/" + path
        String prefix = isMobile() ? "mobile/" : "front/";

        return prefix + path;
    }

    // 객체를 만들지 않아도 직접 접근할 수 있게 하고,
    //                  스프링에서 관리하는 객체가 아니더라도 사용 가능하게 만든다.
    // messages에 있는 파일들을 각각 키 값을 통해 가져올 수 있도록 메소드를 만든다.
    public static String getMessage(String code, String type) {
        // type이 주어지지 않거나 비어있는 경우에는 validations 사용
        // 그렇지 않으면 주어진 type을 사용
        // 기본값을 validations로 사용하기 위함, 제일 많이 하는게 유효성 검사기 때문
        // 입력을 하면 입력한대로 번들에 맞는 메세지를 찾게 한다.
        type = StringUtils.hasText(type) ? type : "validations";

        // 타입에 따라서 다른 번들을 가져온다.
        ResourceBundle bundle = null;
        if (type.equals("commons")) {
            bundle = commonsBundle;
        } else if (type.equals("errors")) {
            bundle = errorsBundle;
        } else {
            bundle = validationsBundle;
        }

        return bundle.getString(code);
    }

    public static String getMessage(String code) {
        // 기본적으로 유효성 검사(validations)쪽 코드를 가져오게 설정
        return getMessage(code, null);
    }
}
