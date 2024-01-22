package org.RentalProject.RentalService.member;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.RentalProject.RentalService.member.entities.Member;
import org.springframework.stereotype.Component;

// 회원쪽 편의 기능
@Component
@RequiredArgsConstructor
public class MemberUtil {

    private final HttpSession session;

    public boolean isLogin() {
        // 세션 데이터가 있으면 로그인 상태, 아니면 안 한 상태
        return getMember() != null;
    }

    // session에서 Member로 저장된 회원정보를 바로 조회할 수 있게 해준다.
    public Member getMember() {
        Member member = (Member) session.getAttribute("Member");

        return member;
    }

    public static void clearLoginData(HttpSession session) {
        // 세션에 남아 있는 로그인 기록 삭제
        session.removeAttribute("username");
        session.removeAttribute("NotBlank_username");
        session.removeAttribute("NotBlank_password");
        session.removeAttribute("Global_error");
    }
}
