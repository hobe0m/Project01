package org.RentalProject.RentalService.commons.exceptions;

import org.springframework.http.HttpStatus;

// 요청 시 잘못된 것이 있을 때, 잘못된 파라미터가 있을 때 주로 발생
// CommonException(RuntimeException 상속)을 통해 둘의 기능을 상속
// 특정한 예외에 대한 처리(Runtime) 그리고 오류 코드 및 오류 메세지 출력(Common)
public class BadRequestException extends CommonException {
    public BadRequestException(String message) {
        // 부모 클래스는 CommonException이므로 매개변수의 값을 설정
        super(message, HttpStatus.BAD_REQUEST);
    }

}