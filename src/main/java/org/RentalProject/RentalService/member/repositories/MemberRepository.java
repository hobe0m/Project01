package org.RentalProject.RentalService.member.repositories;

import org.RentalProject.RentalService.member.entities.Member;
import org.RentalProject.RentalService.member.entities.QMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;

// 엔티티가 있으면 DB 접근이 필요하기 때문에 Repository가 필요하다.
// 추가적인 조건이 많을 때, QuerydslPredicateExecutor를 사용한다.

// Repository는 데이터베이스와 상호작용하는데 사용된다.
// MemberRepository : Member 엔티티를 다루기 위한 저장소
// extends JpaRepository<Member, Long> : Long을 기본 키로 가지는 Member를 추가하여 확장
//  - 구현할 레포지토리에 특정 데이터 타입의 엔티티를 추가해 확장
// extends 인터페이스 A , 인터페이스 B : 인터페이스 A,B를 둘 다 구현
// Querydsl을 사용해서, 동적 쿼리를 작성하고 실행하는 기능을 레포지토리에 추가

// 종합적으로 MemberRepository 인터페이스는 Spring Data JPA를 사용하여 'Member'
// 엔티티와 관련된 기본적인 CRUD 작업을 수행하고, 동시에 Querydsl을 사용해 동적 쿼리를
// 실행할 수 있다.

// 정적 쿼리 : 실행전에 쿼리문이 정해져 있는 경우
// 동적 쿼리 : 실행 시점에 검색 조건 및 정렬 조건 등에 따라 쿼리문이 생성되거나 변경되는 것
public interface MemberRepository extends JpaRepository<Member, Long>,
        QuerydslPredicateExecutor<Member> {

    // QuerydslPredicateExecutor를 상속받으면 Predicate가 붙은 메소드들이 사용 가능
    // Executor : 실행자,  Predicate : 예측
    // WHERE를 사용하는 조건식을 추가할 수 있다.
    // 기본적인 틀만 쓸 때는 필요가 없지만, 조건식을 많이 사용할 때 추가해주어야 한다.
    // 조회할 때는 필요한 것만 조회하는 것이 중요

    // UserDetailsService에서 이메일과 회원의 아이디를 통해 회원정보를 조회할 수 있는 쿼리 생성
    Optional<Member> findByEmail(String email);
    // 이메일을 통해 그에 맞는 멤버를 찾는 쿼리

    // Optional은 Java에서 null을 다룰 때 유용한 클래스
    // findByEmail : 메소드의 이름, 주어진 이메일을 기반으로 멤버를 찾는다
    // String email : 이메일을 나타내는 문자열 파라미터
    // Optional<Member> : 메소드의 반환타입으로, 찾은 멤버를 Optional로 감싸서 반환
    //  - 이것은 멤버가 존재하지 않을 수 도 있음을 나타낸다.
    //  - 만약 이메일에 해당하는 멤버가 존재하지 않으면 Optional.empty()가 반환된다.

    Optional<Member> findById(String id);
    // 아이디를 통해 그에 맞는 멤버를 찾는 쿼리

    // default 메소드 : 인터페이스에 정의하는 메소드
    //  - 구현 시 메소드 재정의를 하지 않고, 자식 객체에서 '객체 이름.디폴트 메소드명'으로 사용 가능
    // 디폴트 메소드의 구현은 선택 사항, 구현하지 않아도 인터페이스에 정의된 디폴트 값으로 구현된다.
    // 재정의를 하면 재정의 된 내용으로 사용할 수 있다.
    // A 인터페이스를 구현한 클래스들이 모두 구현하지 않고 기본 구현을 상속받을 수 있다는 장점이 있다.

    /*
     인스턴스 변수와 로컬 변수
      인스턴스 변수는 클래스 내부에서 선언되고, 객체(인스턴스) 생성 시에 메모리에 할당되는 변수
      각 인스턴스(객체)마다 별도의 값을 갖는다.
      주로 클래스의 속성이나 상태를 표현하기 위해 사용한다.

      public class MyClass {
            private int value; // 인스턴스 변수
      }

      로컬변수는 메소드나 블록 내에서 선언되고, 해당 블록이나 메소드의 실행이 끝나면 소멸되는 변수
      메소드 내에서 선언된 변수이기 때문에 중괄호({})안에서만 유효하다.
      주로 메소드 내에서 임시적인 데이터를 저장하거나 연산을 수행하기 위해 사용

      public void someMethod() {
            int localVar = 10; // 로컬 변수
      }

     this와 super
      this는 현재 객체를 나타내는 참조
      인스턴스 메소드나 생성자 내에서 현재 객체에 접근할 때 사용
      인스턴스 메소드에서 사용하는 매개변수와 멤버변수의 이름이 같고, 멤버변수에 접근하고 싶을 때 사용
       - 인스턴스 메소드 내에서 멤버 변수와 메소드의 충돌을 방지하기 위해 사용

      public class MyClass {
        private int value;

        public void setValue(int value) {
        this.value = value; // 현재 객체의 value에 접근
        }

        public void printValue() {
        System.out.println(this.value); // 현재 객체의 value를 출력
        }
      }

      super는 부모클래스를 나타내는 참조, 현재 클래스가 상속한 부모 클래스를 의미
      서브 클래스에서 부모 클래스의 멤버에 접근할 때 사용
      서브 클래스에서 부모 클래스의 생성자를 호출하거나, 부모 클래스의 메소드를 호출할 때 사용
      오버라이딩한 메소드에서도 필요에 따라 super 사용
       - 부모의 메소드도 쓰고, 오버라이딩한 메소드도 써야할 때 사용
       - 부모의 메소드를 그대로 쓰면 오버라이딩을 하지 않고, super만 사용하면 된다.

      필드와 생성자
       필드는 클래스 내에서 사용되는 데이터를 저장하는 공간
       name과 age는 private 접근제어자를 사용하여 클래스 외부에서 직접 접근 불가
       각각 String(name) 타입과 int(age) 타입을 가진다

       생성자는 클래스의 객체를 초기화 하는 역할
       생성자는 클래스와 동일한 이름을 가지고, 반환타입이 없다
       public Person(String name, int age)는 Person 클래스의 생성자
       매개변수로 받은 name과 age를 사용해 객체의 초기상태를 설정한다.
       객체를 생성 시 (String name, int age)를 무조건 매개변수로 가져야한다.
       this를 사용해서, 객체의 멤버변수(필드)를 참조한다는 것을 알린다.
       생성자를 만들지 않으면 매개변수가 없는 생성자가 자동으로 생성된다.
       이 때, 필드가 있을 때에는 필드의 기본값(0, null 등)을 넣어서 만들어준다.

       따라서 person 객체 생성 시
       Person person = new Person("John", 30) 이런 식으로 초기화 할 수 있다.


      public class Person {
        // 필드 (멤버 변수)
        private String name;
        private int age;

        // 생성자
      public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
    */

    /*
    ==================================== 에러 코드 ====================================
    */

    default boolean existsByEmail(String email) {
        // Qmember는 Querydsl이 생성한 Query Type 클래스이며, 엔티티 클래스 Member를 기반으로 생성
        // QMember 인스턴스 생성 후, member 변수에 할당
        QMember member = QMember.member;
        
        // Querydsl의 Predicate로, email 필드가 주어진 email 값과 동일한지 검사
        // member.email = Member의 email 필드,  eq(email) : 사용자가 전달한 이메일 값
        // 해당 조건을 만족하는 데이터가 존재하는지 여부를 반환
        // 쉽게 말해 주어진 이메일과 일치하는 멤버가 데이터베이스에 존재하는지 확인
        // true라면 이미 해당 이메일을 가진 멤버가 존재
        return exists(member.email.eq(email));
    }

    default boolean existsById(String id) {
        QMember member = QMember.member;

        return exists(member.id.eq(id));
    }
}
