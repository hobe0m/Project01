package org.RentalProject.RentalService.member.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.RentalProject.RentalService.commons.Utils;
import org.RentalProject.RentalService.commons.exceptions.ExceptionProcessor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

// ExceptionProcessor : 발생하는 에러를 에러페이지로 연결시켜주는 역할을 한다.
// @Controller : 스프링에서 관리하는 Controller라는 것을 알려주고 컴포넌트 스캔에 의해
//               찾아져 관리되며, 스프링 MVC에서 웹 요청을 처리하는 컨트롤러로 동작한다.
// @RequesteMapping : 요청 경로를 지정하는 애노테이션(데이터 조회, 요청)
//  - ("/member") : 웹 애플리케이션에서 들어오는 HTTP 요청 중 /member 경로로 시작하는
//                  요청을 이 컨트롤러가 처리하게끔 한다.
// 따라서 /member/list 라는 요청이 오면 이 컨트롤러의 메소드가 그 요청을 처리한다.
// 여러 메소드가 있을 경우, 메소드에 추가적인 @RequestMapping 어노테이션을 붙여 특정
// 메소드가 특정 경로를 처리할 수 있게 한다.
// @RequesteMapping을 붙이고 경로 설정을 하지 않으면 디폴트 경로인 /(루트) 경로가 설정된다.

// 클래스와 메소드 모두에 @RequesteMapping을 사용하여 클래스는 공통 경로, 메소드는
// 추가경로로 하여 member로 오는 요청에서 세부적으로(list, file) 나눌 수 있다.
// 제일 처음으로 오는 경로가 다르다면 두 가지의 클래스를 사용하거나 오는 요청을 다 받은 뒤
// 메소드에 경로를 지정하여 사용할 수 있다.

// @RequiredArgsConstructor : final이나 @NonNull인 필드를 가지는 생성자 생성
//  - 클래스 상단에 private final로 선언된 필드에 대한 생성자를 자동으로 생성
// final : 초기화 된 이후에는 값 변경 불가 즉, 필드 변경 불가
// @NonNull : 해당 필드에는 null 값 X

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController implements ExceptionProcessor {
    private final Utils utils; // 의존성 주입
    
    // 회원가입쪽
    @GetMapping("/join")
    public String join(@ModelAttribute RequestJoin form) {
        /*
         RequestJoin이 커맨드 객체이다.
          - 요청 데이터(클라이언트가 입력한 데이터) 처리에 특화된 객체
          - 따라서 RequestJoin 객체를 만든다.
         @ModelAttribute를 통해 클라이언트로부터 입력 받은 값을 묶어서 RequestJoin 형태의
         객체인 form을 만든다. 이 때 RequestJoin에 만들어 놓은 필드에 대응하는 값이 없어도
         기본적으로는 @ModelAttribute로 인해 오류가 발생하지 않고 null 혹은 기본값이 있다면
         기본값으로 설정된다.
         하지만 @NotNull 혹은 @Size와 같은 검증 애노테이션들이 있다면, MVC는 이를 통해 데이터
         의 유효성을 검증하고 검증에 실패하면 오류 정보를 저장한다.
         */
        return utils.tpl("member/join");

        // 해당 메소드는 member/join으로 들어오는 요청에 대한 처리를 하고 그 결과를
        // utils.tpl("member/join")에서 반환된 템플릿에 넣어서 화면으로 띄운다.
    }

    // 회원가입 처리는 post 방식, 처리하는 부분은 뒤에 Ps를 붙여 표시한다.
    // POST 요청 : 클라이언트가 서버에 데이터를 제출할 때 사용하는 HTTP 요청 메소드(데이터 처리)
    //  - 데이터를 HTTP 요청의 본문(Body)에 담아서 서버로 보낸다.
    //  - 이를 통해 클라이언트는 서버로 데이터를 보내고, 서버는 그걸 처리할 수 있다.
    // @PostMapping : 지정한 경로에 Post 요청이 들어왔을 때 처리하는 컨트롤러 혹은
    //                메소드 지정
    @PostMapping("/join")
    public String joinPs(@Valid RequestJoin form, Errors errors) {
        // @Valid를 통해 받은 데이터를 RequestJoin 객체로 만들고 그것을 검증한다.
        // 이후 실패했을 때의 결과를 Errors 객체에 담아준다.
        // Errors가 아닌 Errors의 자식인 BindingResult를 사용해도 된다.
        //  - 사용하면 동일한 결과를 내지만 더 확장성 있고, 보다 많은 메소드를 활용할 수 있다.
        // @Valid : 정의해놓은 제약조건에 따라 유효성 검사를 실시한다.

        if (errors.hasErrors()) {
            return utils.tpl("member/join");
            // 유효성 검사 결과에 에러가 있다면, 사용자에게 양식데이터 와 에러메세지를
            // 보여주기 위해 "member/join" 템플릿을 반환한다.
            // 에러가 없다면, 회원가입이 성공했으므로 "redirect:/member/login"
            // 로그인 페이지로 리다이렉션한다.
            // 이렇게 함으로써 사용자에게 어떤 입력이 문제인지 힌트를 제공하고, 에러가
            // 발생한 필드를 강조하여 수정할 수 있는 기회를 제공한다.
        }

        return "redirect:/member/login";
    }

    // 해당 메소드는 클라이언트가 "/join"의 경로로 POST 요청을 보내면 처리한 뒤
    // 서버로 보내고, 서버에서 메소드에게 /member/login으로 리다이렉션 즉 페이지
    // 이동을 하게끔 지시해 요청 결과에 맞게 적절한 페이지로 보낸다.
    
    // 로그인 페이지
    // 로그인 처리는 스프링 시큐리티가 해주기 때문에 로그인 양식만 있으면 된다.
    @GetMapping("/login")
    public String login() {

        return utils.tpl("member/login");
    }

}
