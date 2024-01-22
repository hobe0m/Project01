package org.RentalProject.RentalService.member.entities;

// Base는 공통으로 사용할 속성이기 때문에 상속받는다.
// 그것이 Base의 생성이유이다.

import jakarta.persistence.*;
import lombok.Data;
import org.RentalProject.RentalService.commons.entities.Base;

import java.util.ArrayList;
import java.util.List;

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
@Data
@Entity
public class Member extends Base {
    // 기본 키는 wrapper class 형태로 만드는 것이 좋다.
    // wrapper class는 숫자, 문자, boolean 등을 포장해주는 역할을 한다.
    // wrapper class의 예시
    // int = Integer, char = Character만 특별 케이스이고
    //      long, double, float, boolean은 첫 글자를 대문자로 바꿔서 사용한다.
    // wrapper class로 감싸면,
    //      데이터 자체를 객체로 보고 그 객체는 메소드 등 여러 기능을 활용할 수 있다.
    // 따라서 유연성이 늘어나기 때문에 사용한다.

    @Id @GeneratedValue
    // @GeneratedValue : 데이터가 하나씩 생겨날 때마다 1씩 증가하는 시퀀스 자동 생성
    // 기본키가 숫자형태로 증가하는 것이 일반적이다.
    private Long seq; /* 회원번호 */

    // @Column : 엔티티의 필드를 데이터베이스 컬럼에 매핑할 때 사용한다.
    // length, nullable, unique 등과 같은 속성을 이용해 컬럼의 속성을 설정한다.
    @Column(length = 80, nullable = false, unique = true)
    private String email; /* 이메일 */

    @Column(length = 50, nullable = false, unique = true)
    private String id; /* 아이디 */

    @Column(length = 100, nullable = false)
    private String password; /* 비밀번호 */

    @Column(length = 30, nullable = false)
    private String name; /* 이름 */

    @Column(length = 50)
    private String userName; /* 닉네임 */

    @Column(length = 50)
    private String birth; /* 생년월일 */

    @Column(length = 150)
    private String address; /* 주소 */

    @Column(length = 50)
    private int tel; /* 전화번호 */

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<Authorities> authorities = new ArrayList<>();
    // authorities는 Authoritie 객체들을 담는 리스트이며
    // 이 리스트는 초기에는 비어있지만, 필요한 경우 authorities에
    // Authoritie 객체를 추가하거나 제거할 수 있다.
    // OneToMany이기 때문이다.
    // Authority가 주인이므로 member에 mappedBy = "member"를 써준다.
}
