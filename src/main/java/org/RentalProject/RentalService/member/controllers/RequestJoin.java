package org.RentalProject.RentalService.member.controllers;

// 요청 데이터를 처리하는 커맨드 객체
// member에도 비슷한 게 있는데 만들 필요가 있을까?
//  - 역할이 다르기 때문에 만들어줘야 한다.
//  - 엔티티는 데이터를 담고, 커맨드 객체는 데이터를 담고 후처리(검증 등)까지 한다.
//     * 커맨드 객체의 특화된 기능 등을 수행한다.
//     * 따라서 다르게 하는 것이 관리하기에 훨씬 수월하다.

// 커맨드 객체 검증은 기본적으로 애노테이션으로 하고, 커버가 안되는 부분은 Validator를 정의한다.

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RequestJoin {
    @NotBlank // 필수(빈 칸 X)
    @Email // 이메일 형식으로 작성되어야 한다.
    private String email; // 가입할 때 이메일을 받아야 하기 때문에 작성

    @NotBlank
    @Size(min = 6)
    // @Size : 주로 데이터의 크기(길이)를 검증할 때 사용한다.
    //         특히 문자열의 길이를 검증할 때 자주 사용한다.
    private String id; // 이메일이 없을 때는 아이디로 대신하기 때문에 작성

    @NotBlank
    @Size(min = 8)
    private String password; // 비밀번호

    @NotBlank
    private String confirmPassword; // 비밀번호 확인

    @NotBlank
    private String name; // 회원명

    @AssertTrue // 참 값인지 확인하는 애노테이션
                // 만약 false라면 유효성 검증에 실패한다.
                // agree 필드가 true여야 하기 때문에 즉, 동의가 필수이기 때문에 사용한다.
    private boolean agree; // 정보 제공 약관 동의


    }
