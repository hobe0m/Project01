package org.RentalProject.RentalService.member.service;

import org.RentalProject.RentalService.commons.exceptions.CommonException;
import org.springframework.http.HttpStatus;

// CommonException을 상속받아 오류의 코드와 메세지를 전달
// 공통적으로 사용하는 코드와 메세지를 정의해놓고 상속받아 쓰는 형식
public class MemberNotFoundException extends CommonException {

    public MemberNotFoundException() {
        // CommonException의 부모 RuntimeException의 메세지를 받고
        // CommonException에서 status 즉, 코드를 받아온다.
        super("등록된 회원이 아닙니다.", HttpStatus.NOT_FOUND);

        // 이후에 문구도 계속 지정하는 것이 아닌 메세지 코드 형태로 빼서 가져다 쓸 수 
        // 있게 관리할 예정(resource 쪽)
    }
}
