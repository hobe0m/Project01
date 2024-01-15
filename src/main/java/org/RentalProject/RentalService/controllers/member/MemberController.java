package org.RentalProject.RentalService.controllers.member;

import lombok.RequiredArgsConstructor;
import org.RentalProject.RentalService.commons.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
// 클라이언트의 요청을 처리하고 응답을 반환하는 컨트롤러로 지정

@RequestMapping("/member")
@RequiredArgsConstructor
// ()안의 URL 패턴으로 요청이 들어왔을 때 이 클래스 또는 메소드가 처리하도록 지정
// 따라서 member는 이 컨트롤러가 처리하는 요청의 기본 경로
// /member로 시작하는 URL이 이 MemberController에게 전달되면 해당하는 메소드가 실행된다.
public class MemberController {

    // 필드 주입이라는 의존성 주입 방법으로 관계를 형성
    private final Utils utils;

    // join 경로로 들어오는 GET 요청이 발생하면, front/member/join이라는 뷰를 클라이언트
    // 에게 보여준다.
    @GetMapping("/join")
    public String join() {
        return utils.tpl("member/join");
    }
}
