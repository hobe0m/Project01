package org.RentalProject.RentalService.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.RentalProject.RentalService.commons.exceptions.CommonException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

// 여러 컨트롤러가 공통으로 가져야 하는 설정(경로 지정 해주어야 한다), 예외 처리, 모델 속성 추가 등을 정의
// @ControllerAdvice는 경로를 지정해주어야 그 경로에 있는 컨트롤러들이 영향을 받는다.
// "org.RentalProject.RentalService.controllers" 이 경로에 있는 컨트롤러는 영향을 받음
@ControllerAdvice("org.RentalProject.RentalService.controllers")

public class CommonController {

    // Exception 또는 Exception의 하위 클래스에서 발생한 예외 처리
    // @ExceptionHandler : 특정 컨트롤러 메소드에서 발생하는 예외를 처리하고
    // 사용자에게 적절한 응답 생성을 할 수 있게 도와준다.
    @ExceptionHandler(Exception.class)
    // Exception e : 발생한 예외 객체(메소드 호출 시 어떤 예외가 발생했는지에 대한
    //               정보를 담고 있는 Exception 타입 객체 e가 전달된다.
    // Model model : 데이터를 뷰로 전달, 주로 에러 메세지나 예외 정보를 뷰로 전달
    //               서버에서 받은 데이터를 사용자가 볼 수 있게 뷰로 전달
    // HttpServlerResponse response : 서버에서 전송되는 응답을 다양하게 제어
    //                                예외 상황에 대한 적절한 응답 코드 설정 및
    //                                리다이렉션(페이지 이동) 수행
    public String errorHandler(Exception e, HttpServletResponse response,
                               HttpServletRequest request, Model model) {

        // 에러 코드 500번을 우선 기본값으로 설정
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        // CommonException만 가져오는 코드(응답코드를 설정했기 때문, e는 Exception 전체에 해당)
        // CommonException인 경우 해당 예외에서 가져온 상태 코드를 설정
        if (e instanceof CommonException) {
            CommonException commonException = (CommonException) e;
            status = commonException.getStatus();
        }

        // 응답코드 설정(정수 값)
        // status.value() 중 .value() 메소드를 통해 정수 값을 뽑아온다.
        response.setStatus(status.value());

        // 에러 코드 콘솔에 출력
        e.printStackTrace();

        // model.addAtrribute() : 지정된 매개변수는 없지만
        // 보통 Sring 모델에 추가되는 데이터 이름, Object 모델에 추가되는 데이터의 값
        // 으로 많이 쓰인다.
        // 또한 값은 null이 될 수도 있다(@Nullable)
        // Attribute : 속성, 따라서 addAttribute는 속성 추가이다.
        // 모델에 status라는 이름으로 status.value() 값을 추가
        model.addAttribute("status", status.value());
        model.addAttribute("path", request.getRequestURI());
        model.addAttribute("method", request.getMethod());
        model.addAttribute("message", e.getMessage());

        // 데이터를 전송하는 경로(띄울 뷰의 페이지 경로)
        // 모델에서 처리한 데이터를 해당 경로의 뷰 페이지로 보내 띄운다.
        // 컨트롤러가 뷰를 띄울 템플릿까지 결정 후 경로(템플릿의 경로)를 반환한다.
        return "error/common";
    }
}
