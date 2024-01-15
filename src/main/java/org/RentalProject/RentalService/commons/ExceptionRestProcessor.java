package org.RentalProject.RentalService.commons;

import org.RentalProject.RentalService.commons.exceptions.CommonException;
import org.RentalProject.RentalService.commons.rests.JSONData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

// RestController쪽에 넣어 줄 에러페이지, 공통적인 내용
public interface ExceptionRestProcessor {
    @ExceptionHandler(Exception.class)

    // ResponseEntity는 Spring에서 HTTP 응답을 표현하는 클래스
    // <JSONData>는 응답의 본문에 해당하는 데이터 타입
    // 여기서는 데이터 부분에 Object를 사용하여 어떤 타입의 객체도 담을 수 있음을 나타낸다.
    default ResponseEntity<JSONData<Object>> errorHandler(Exception e) {


        // 예외가 발생 했을 때, 기본 값은 500번 에러
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        // e가 CommonException의 객체(타입)이면, CommonException에서 상태 코드를 가져와서 설정
        if (e instanceof CommonException) {
            CommonException commonException = (CommonException) e;
            status = commonException.getStatus();
        }

        // JSONData 타입의 data 생성, Object는 이 데이터가 어떤 타입이든 담을 수 있음을 나타낸다.
        JSONData<Object> data = new JSONData<>();

        // 예외가 발생했기 때문에 즉, 응답을 못했기 때문에 false로 설정
        data.setSuccess(false);

        // 응답의 HTTP 상태 코드 즉, 에러 코드 설정
        data.setStatus(status);

        // 에러 메세지 설정
        data.setMessage(e.getMessage());

        // 콘솔에 출력
        e.printStackTrace();

        // 위에서 설정한 상태코드(status)와 데이터(data)를 사용하여 ResponseEntity 객체 생성
        // ResponseEntity.status(status)는 상태 코드를 설정하고, .body(data)는 응답 본문에 data 객체를 설정
        return ResponseEntity.status(status).body(data);

        // 이 메소드는 예외가 발생했을 때, 그 정보를 JSONData 객체에 담아서 클라이언트에게 응답으로 보내준다.
        // 클라이언트는 응답을 받아서 상태, 성공 여부, 메시지 등을 확인할 수 있다.
    }
}
