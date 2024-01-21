package org.RentalProject.RentalService.member.service;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.RentalProject.RentalService.commons.Utils;
import org.RentalProject.RentalService.member.MemberUtil;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.util.StringUtils;

import java.io.IOException;

// 요청과 응답에 대한 핵심적인 객체가 포함되어 있다, 따라서 사용 가능
// 실패 시에 단순히 주소만 이동하는 것이 아니고
//                  상세히 조정할 것이 있을 때 AuthenticationFailureHandler 사용
// Authentication authentication : 예외가 발생했을 때, 예외 객체
public class LoginFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception)
                                        throws IOException, ServletException {

        // 현재 요청과 연관된 세션을 가져온다.
        HttpSession session = request.getSession();

        // 세션 로그인 실패 메세지 일괄 삭제
        MemberUtil.clearLoginData(session);

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        // 로그인 아이디 유지
        session.setAttribute("username", username);

        if (!StringUtils.hasText(username)) {
            session.setAttribute("NotBlank_username", Utils.getMessage("NotBlank.id"));
        }

        if (!StringUtils.hasText(password)) {
            session.setAttribute("NotBlank_password", Utils.getMessage("NotBlank.password"));
        }

        // request 범위는 페이지 이동 시 속성 값 유지가 안돼서 세션을 사용
        // session은 페이지 간 이동이 이루어져도 유지되고, 브라우저가 닫히거나
        // 유효기간이 만료될 때까지 사용가능하다.
        // 각 HTTP 요청은 독립적이고, 서버는 이전 요청과 현재 요청 간 어떤 상태도
        // 유지하지 않으므로 속성값이 이동되지 않는 것은 자연스러운 동작이다.

        // 다시 로그인 페이지로 이동하고, 실패 원인이 무엇인지 알려주면 된다.

        // 아이디, 비번이 있지만 실패한 경우 : 아이디로 조회되는 회원이 없거나 비밀번호가 일치하지 않을 때
        if (StringUtils.hasText(username) && StringUtils.hasText(password)) {
           session.setAttribute("Global_error",
                   Utils.getMessage("Fail.login", "errors"));
        }

        // 로그인 페이지로 이동
        // request.getContextPath : 현재 경로에 뒤에 오는 경로를 추가하여 리다이렉트한다.
        // 따라서 조금 더 정확하게 보낼 수 있다.
        // ex) www.google.com/member/login 구글에서 실패했다면 이런식으로 보낼 수 있다.
        response.sendRedirect(request.getContextPath() + "/member/login");
    }
}
