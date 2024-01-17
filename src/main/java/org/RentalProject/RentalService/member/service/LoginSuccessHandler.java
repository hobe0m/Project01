package org.RentalProject.RentalService.member.service;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.RentalProject.RentalService.member.MemberUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

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

        HttpSession session = request.getSession();

        MemberUtil.clearLoginData(session);
    }
}
