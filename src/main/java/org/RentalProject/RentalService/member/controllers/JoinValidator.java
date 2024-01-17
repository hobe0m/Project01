package org.RentalProject.RentalService.member.controllers;

import lombok.RequiredArgsConstructor;
import org.RentalProject.RentalService.commons.validators.PasswordValidator;
import org.RentalProject.RentalService.member.repositories.MemberRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


// 스프링의 관리 객체로 등록하기 위해 사용
@Component
// 의존성을 주입하기 위해 사용
@RequiredArgsConstructor
public class JoinValidator implements Validator, PasswordValidator {
    // 추상메소드가 정의되어 있어, 필수로 재정의 해준다.
    // 유효성 확인을 위한 Validator, PasswordValidator 구현

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

        // errors가 가지고 있는 메소드로 rejectValue(String field, String errorCode);라는
        // 매개변수를 기본값으로 갖는다.
        // 오류가 발생하면 객체 생성 후 해당 필드에 에러 코드의 오류를 추가한다.
        // 에러 코드에 해당하는 실제 오류 메세지는 메세지 소스에서 찾는다.
        // StringUtils.hasText(email) && memberRepository.exists(email)
        //  - email의 값이 null 혹은 비어있거나, 이미 DB에 있는 값이면 true 즉, 오류 발생
        if(StringUtils.hasText(email) && memberRepository.existsByEmail(email)) {
            errors.rejectValue("email", "Duplicated");
        }

        if (StringUtils.hasText(id) && memberRepository.existsById(id)) {
            errors.rejectValue("id", "Duplicated");
        }

        // 2. 비밀번호 복잡성 체크 - 대소문자 각각 1개 포함, 숫자 1개 이상 포함, 특수문자 1개 이상 포함
        // 비회원 주문서 확인 시, 비회원 게시글 조회 시에도 사용한다.
        // 그 때의 비밀번호의 복잡성 설정 가능

        // 공통 Validator에 3가지로 나눠서 설정해놓아서, 쓰고 싶은 것만 사용해도 된다.
        if (StringUtils.hasText(password)
           && (!alphaCheck(password, true) && !numberCheck(password)
           && !specialCharsCheck(password))) {

            errors.rejectValue("password", "Complexity");
        }




        // 3. 비밀번호, 비밀번호 확인 일치 여부 체크
        
        // 우선 비밀번호와 비밀번호 확인 값이 존재하는 지 확인
        // 그 후 비밀번호와 비밀번호 확인의 값이 일치하지 않으면 에러 발생
        // password와 confirmPassword의 필드가 다르기 때문에 Mismatch.password로 필드를 지정해준다.

        if (StringUtils.hasText(password) && StringUtils.hasText(confirmPassword)
            && !password.equals(confirmPassword)) {
            errors.rejectValue("confirmPassword", "Mismatch.password");
        }

    }
}
