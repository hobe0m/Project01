package org.RentalProject.RentalService.entities;

// Base는 공통으로 사용할 속성이기 때문에 상속받는다.
// 그것이 Base의 생성이유이다.

import jakarta.persistence.Entity;
import lombok.Data;

@Data
/*
 @Builder
 Lombok에서 제공하는 애노테이션으로 빌더 패턴(Buider Pattern)을 자동으로 생성해준다.
 빌더 패턴은 객체를 생성할 때 사용되며, 가독성을 높이고 필요한 속성만 설정할 수 있도록 하는 패턴이다.
 빌더 패턴을 사용해서 만든 객체는 불변하고, 필요한 속성만 설정하여 객체를 설정할 수 있어 가독성이 좋아진다.
 다양한 필드 중 쓰고 싶은 것만 사용할 수 있지만, 사용하지 않은 필드들은 기본값으로 초기화된다.

빌더 클래스의 예시

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class Person {
    private String firstName;
    private String lastName;
    private int age;
}

Person person = Person.builder()
    .firstName("John")
    .lastName("Doe")
    .age(25)
    .build();

    빌더를 사용해 객체 생성 .으로 이어붙여주고 변수의 값을 바로 넣어준다.
    마지막에는 .build();로 마무리한다.
 */
@Entity

public class Member extends Base{
}
