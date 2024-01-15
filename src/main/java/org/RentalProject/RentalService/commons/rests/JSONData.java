package org.RentalProject.RentalService.commons.rests;

// JSONData를 제네릭 타입으로 지정
// 제네릭(Generic) 타입은 변수 또는 메소드의 타입을 미리 지정하지 않고 사용 시점에 결정
// 새로운 객체를 생성해서 사용 시 여러 타입을 지정해서 사용할 수 있다.
// 일반적으로 필드의 변수나 메소드의 타입에 관한 것

// JSON(JavaScript Object Notation) : 데이터를 표현하는 경량의 데이터 교환 형식
// 텍스트 형태로 이루어져 있으며, 여러 프로그래밍 언어에서 파싱하기 쉽다.
// 일반적으로 키-값 쌍의 집합으로 표현되며, 배열과 중첩된 객체도 지원합니다.

/*
JSON 데이터의 예시

{
  "이름": "John",
  "나이": 30,
  "도시": "New York",
  "친구들": ["Alice", "Bob"],
  "주소": {
    "거리": "123 Main Street",
    "우편번호": "10001"
  }
}
 */


// @Date : Lombok에서 제공하는 어노테이션으로 자동으로
// 필드의 getter,setter,equals,hashcode,toSting 생성
import lombok.Data;

// @NoArgsConstructor : Lombok에서 제공하는 어노테이션으로 파라미터가 없는 기본 생성자 생성
import lombok.NoArgsConstructor;

// @AllArgsConstructor : Lombok에서 제공하는 어노테이션으로 모든 필드를 인자로 받는 생성자 생성
// 모든 필드를 인자로 받는다 : 모든 필드를 매개 변수로 갖는 생성자 생성
import lombok.AllArgsConstructor;
import org.hibernate.query.sql.internal.ParameterRecognizerImpl;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
// 제네릭스를 사용하여, 어떤 타입의 데이터라도 받을 수 있게 설계
public class JSONData<T> {
    // status : HTTP 응답 상태 코드를 나타내며, 기본 값은 HttpStatus.OK로 설정
    private HttpStatus status = HttpStatus.OK;

    // success : 요청이 성공적인지 여부를 나타낸다, 기본값은 true이다.
    private boolean success = true;

    // data : 실제 데이터를 담는 부분으로, Generics로 선언된 타입의 객체를 받는다.
    private T data;

    // message : 요청에 대한 추가적인 메세지를 나타낸다.
    private String message;
}
