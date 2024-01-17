package org.RentalProject.RentalService.member.controllers;

import lombok.RequiredArgsConstructor;
import org.RentalProject.RentalService.member.repositories.MemberRepository;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


// 스프링의 관리 객체로 등록하기 위해 사용
@Component
// 의존성을 주입하기 위해 사용
@RequiredArgsConstructor
public class JoinValidator implements Validator {
    // 추상메소드가 정의되어 있어, 필수로 재정의 해준다.

    // 이메일, 아이디의 중복 체크를 하기 위해 DB에 접근할 수 있게 해주는 Repository 주입
    // 정의된 메소드가 없기 때문에, 직접 repository에 가서 정의해주어야 한다.
    private final MemberRepository memberRepository;


    @Override
    public boolean supports(Class<?> clazz) {
        // supports는 검증하려는 대상을 한정하려는 것으로 정해져 있다.
        // isAssignableFrom : 주어진 클래스나 인터페이스가 현재 클래스나 인터페이스로부터
        //                    상속되었거나 구현되었는지 여부 검사
        // 따라서 clazz가 RequestJoin 클래스나 그 하위클래스 일 때, supports 메소드는
        // true를 반환한다.
        // 이는 Validator가 RequestJoin의 클래스나 하위 클래스의 객체를 검증할 수 있다는 뜻이다.
        // Validator가 특정 클래스의 객체를 검증할 수 있는 지 확인하는 것
        return clazz.isAssignableFrom(RequestJoin.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        /*
        1. 이메일, 아이디 중복 여부 체크
        2. 비밀번호 복잡성 체크 - 대소문자 각각 1개 포함, 숫자 1개 이상 포함, 특수문자 1개 이상 포함
           * 비밀번호 같은 경우는 나중에 활용할 경우가 많아서 따로 공통 부분으로 뺄 예정
        3. 비밀번호, 비밀번호 확인 일치 여부 체크
        */

        // 검증의 대상인 타겟을 RequestJoin으로 형변환하여 form 변수에 할당한다.
        RequestJoin form = (RequestJoin)target;
        
        // 검증을 하기 위해 필요한 것들 가져오기
        String email = form.getEmail();
        String id = form.getId();
        String password = form.getPassword();
        String confirmPassword = form.getConfirmPassword();

        // 1. 이메일, 아이디 중복 여부 체크


    }
}
