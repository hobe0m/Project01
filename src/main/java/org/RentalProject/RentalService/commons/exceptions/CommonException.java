package org.RentalProject.RentalService.commons.exceptions;

import org.springframework.http.HttpStatus;

// RuntimeException : 모든 Runtime의 부모 클래스
// Runtime 예외는 주로 개발자의 실수나 프로그램의 오류를 나타내기 때문에 발생하면 코드를 검토 후 수정해야 한다.
// RuntimeException은 예외 처리를 강제하지 않음 즉, 예외를 처리하지 않아도 컴파일 가능
//  - 개발자가 예외 처리를 선택적으로 수행할 수 있음
// 컴파일까지는 가능하지만 실행중에 오류가 발생하면서 프로그램이 중단된다.
// 특정한 상황에서의 에러에 대한 처리를 하기 위해 사용
public class CommonException extends RuntimeException {

    // 오류의 코드를 가져온다

    // HttpStatus를 사용하기 위해 status를 변수로 하는 필드 생성
    private HttpStatus status;


    // 메소드 설정
    // message와 status 두 가지의 매개변수 설정
    // message는 부모 클래스인 RuntimeException에서 전달받아 예외 객체에 설정
    // status는  CommonException 객체의 status 필드에서 전달 받아 예외 객체에 설정
    // 즉, message는 부모 클래스꺼, status는 본인꺼를 받아서 CommonException 객체에 설정
    public CommonException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }


    // HttpStatus의 메소드를 통해 Status를 가져온다.
    // 위에서 super(message);를 통해 RuntimeException의 생성자 호출을 했기 때문에
    // 따로 message에 대한 return값을 가져오는 코드를 설정할 필요는 없다.
    public HttpStatus getStatus() {
        return status;
    }
}