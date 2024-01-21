package org.RentalProject.RentalService.member;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;

// 회원쪽 편의 기능
@Component
public class MemberUtil {

    public static void clearLoginData(HttpSession session) {
        // 세션에 남아 있는 로그인 기록 삭제
        session.removeAttribute("username");
        session.removeAttribute("NotBlank_username");
        session.removeAttribute("NotBlank_password");
        session.removeAttribute("Global_error");
    }
}
