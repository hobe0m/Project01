package org.RentalProject.RentalService.member.service;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.RentalProject.RentalService.member.MemberUtil;
import org.RentalProject.RentalService.member.entities.Member;
import org.codehaus.groovy.util.StringUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.util.StringUtils;

import java.io.IOException;

// 요청과 응답에 대한 핵심적인 객체가 포함되어 있다, 따라서 사용 가능
// 성공 시에 단순히 주소만 이동하는 것이 아니고
//                  상세히 조정할 것이 있을 때 AuthenticationSuccessHandler 사용
// Authentication authentication : 예외가 발생했을 때, 예외 객체
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
                                        throws IOException, ServletException {

        //  Authentication authentication 회원의 로그인 데이터를 활용할 수 있게 해준다.

        HttpSession session = request.getSession();

        // 성공 시에 데이터 비워주기
        MemberUtil.clearLoginData(session);
        
        /* 회원 정보 조회 편의 구현 */
        // Spring Security의 Authentication 객체로부터 현재 사용자의
        //                              Member 정보를 얻어 세션에 저장하는 역할
        MemberInfo memberInfo = (MemberInfo) authentication.getPrincipal();
        Member member = memberInfo.getMember();
        session.setAttribute("member", member);


        // redirectURL=주소 : 성공 시 이동 페이지
        // member/login?redirectURL=/mypage : 로그인 성공 시 마이페이지로 보낸다.
        // 없을 때는 로그인 성공 시 그냥 메인페이지로 보낸다.
        // 여기서 마이페이지와 메인페이지는 그저 예시일 뿐 다른 페이지도 가능하다.


        // 당연히 해야하는 상식적인 부분이다.
        // redirectURL의 값을 request.getParameter를 통해 가져오기
        String redirectURL = request.getParameter("redirectURL");

        // 있으면 redirectURL로 이동하고, 없으면 메인페이지(/)로 이동
        redirectURL = StringUtils.hasText(redirectURL) ? redirectURL : "/";

        // 현재 웹 어플리케이션의 컨텍스트 경로 : 웹 서버에 배포될 때 사용하는 기본 경로
        response.sendRedirect(request.getContextPath() + redirectURL);
    }
}
