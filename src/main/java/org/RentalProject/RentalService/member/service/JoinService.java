package org.RentalProject.RentalService.member.service;

import lombok.RequiredArgsConstructor;
import org.RentalProject.RentalService.member.controllers.JoinValidator;
import org.RentalProject.RentalService.member.controllers.RequestJoin;
import org.RentalProject.RentalService.member.entities.Member;
import org.RentalProject.RentalService.member.repositories.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;


// @Service : 비즈니스 로직이나 트랜잭션 관련 작업을 수행하는 서비스 레이어의 구현 클래스
//  - 해당 클래스를 스프링 컨테이너서 빈으로 관리하게 된다.
//  - 쉽게 말해 서비스 역할(기능을 구현)을 수행하는 역할임을 알린다
//  - JoinService이므로 회원가입 기능을 구현할 수 있게 한다.
@Service
@RequiredArgsConstructor
public class JoinService {

    // 회원가입 기능을 원활하게 구현하기 위해서는 의존성 세 가지가 필요하다.
    // 회원 정보를 데이터 베이스에 저장하고 조회, 이것을 통해 JoinService가 데이터베이스와 상호작용 가능
    private final MemberRepository memberRepository; // 검증을 위한 의존성 주입

    // Validator도 이 안에서 처리할 예정
    // 회원 가입에 필요한 검증 로직을 수행, 이것을 통해 JoinService가 회원가입에 필요한 유효성 검증 수행
    private final JoinValidator validator; // 검증을 위한 의존성 주입
    
    // 회원 가입 시 사용자의 비밀번호를 해싱하여 저장하거나, 로그인 시 입력된 비밀번호를 확인할 때 사용
    private final PasswordEncoder encoder; // 검증을 위한 의존성 주입

    // 커맨드 객체 형태로 값이 들어갈 수 있기 때문에 그 부분도 처리
    // RequestJoin - MemberController - JoinService
    // process 메소드는 RequestJoin 타입의 form과, Errors 타입의 errors를
    // 매개변수로 받는다.
    public void process(RequestJoin form, Errors errors) {
        // validator 객체를 사용해서, form 데이터를 검증
        // form은 회원가입 폼에 입력된 데이터를 담고 있는 객체, errors는 검증 결과를 담을 객체
        validator.validate(form,errors);
        if(errors.hasErrors()) {
            // 다음 로직을 실행시키지 않고 종료시킨다.
            // 검증을 실패했기 때문에 return(함수의 종료)으로 종료
            return;
        }
        
        // 비밀번호 BCrypy로 해시화
        // encode는 주어진 문자열을 해시화한 결과 반환
        // 즉 form 데이터 중 password를 해시화한 결과를 encoder 객체를 통해
        // 받아서 hash라는 변수에 저장한다
        String hash = encoder.encode(form.getPassword());

        // Member 객체 생성
        // form에서의 getter와 setter Member.class에서의 getter와 setter를 비교해보고
        // 데이터를 치환(동일한 형식은 값이 바뀌어있게 된다)한다.
        // map : 변환메소드(소스, 클래스 패스(원하는 클래스명))
        //  - 각별히 원하는 클래스가 명시하면 동일한 패턴의 getter와 setter가 있으면
        //  - 데이터가 알아서 치환된다.

        // Member member = new ModelMapper().map(form, Member.class);
        // 현재 modelMapper가 작동하지 않아 사용 X
        Member member = new Member();
        member.setEmail(form.getEmail());
        member.setName(form.getName());
        member.setPassword(hash);
        member.setId(form.getId());
        process(member);

        // form 데이터(회원가입 폼에 입력된 데이터를 담고 있는 객체)에서 email값을
        // 가지고 온 뒤 생성했던 member의 이메일의 값에 넣는다.
        // 회원쪽은 양식이 몇개 되지 않아서 set과 get을 사용해서 가져와서 담으면 되지만
        // 주문서에는 항목이 많아서 setter를 너무 많이 써야한다.
        // 따라서 번거로움을 해결하기 위해 ModelMapper이다.

        // ModelMapper 의존성 : 동일한 getter, setter 패턴이 있으면 알아서 데이터를 치환
        // - member.setEmail(form.getEmail()); : 이메일 뿐만 아니라 id, password 등을 전부
        //                                       가져와야 할 때, 자동으로 처리

        /*
         process 메소드에서 하는 일
         RequestJoin의 form 데이터에서 검증, 비밀번호 해시화를 하고,
         데이터도 바꿔주고 해시화 된 비밀번호만 set해서 넣어준다.
        */
    }

    // 회원 가입 처리 결과를 DB에 저장
    // 나중에 최종적으로 Member라는 데이터를 가지고 저장하기 때문에 (Member member)
    public void process(Member member) {
        // saveAndFlush : 엔티티를 데이터베이스에 저장 후 즉시 변경사항을 반영
        // save만 사용하면 트랜잭션이 커밋될 때까지 변경사항이 데이터베이스에 반영 X
        memberRepository.saveAndFlush(member);
    }
}

/*
Hashing
어떤 데이터에 대해 일정한 길이의 고정된 크기의 문자열을 반환하는 과정
반환된 문자열을 해시 값, 또는 해시코드라고 부른다.

고정된 크기의 출력 : 어떤 크기의 입력이든 항상 동일한 크기의 해시코드가 생성된다.
동일한 입력에 대해 동일한 해시 코드 : 동일한 객체나 데이터에 대해 항상 동일한 해시코드가 생성
약간의 입력 변경은 크게 다른 해시 코드로 변환 : 입력 값의 작은 변화는 해시 코드에 큰 차이를 일으킴

hashCode : Object 클래스에서 상속받은 메소드
 - 오버라이딩 필요, 오버라이딩을 하지 않으면 Object 클래스의 기본 구현이 사용된다.
 - equals와 관련, 두 객체가 equals로 동일하다면 hashCode 메소드도 동일한 값을 반환
 - 서로 다른 객체가 동일한 해시코드를 가질 수 있으므로 이를 최소화 하기 위해 객체의 여러 속성을 고려해서 생성해야 한다.

import java.util.Objects;

public class MyClass {
    private int id;
    private String name;

    // 생성자, getter, setter 등 생략

    // hashCode 메소드 재정의, 이 메소드는 객체의 해시 코드를 생성하는 역할을 하는데
    // 재정의를 하지않으면 Object의 기본 구현을 가져오기 때문에 재정의를 해주어야 한다.
    // Object.hash(id,name)은 id와 name의 해시 코드를 조합하여 하나의 해시코드를 만든다는 것
    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    // equals 메소드 재정의
    // equals는 원래 두 객체의 동일성 비교를 담당
    // this == obj로 참조 동일성을 확인하고, 그 후에 getClass()로 클래스 동일성 확인
    // 마지막으로 id와 name이 같은지 비교 후 두 객체가 동일한 지 여부 결정
    @Override
    public boolean equals(Object obj) {
    // this는 myClass, obj는 equals의 매개변수로 받은 다른 객체
        if (this == obj) return true;

    // obj가 null이거나 myClass와 obj의 클래스가 다르면 false 반환        
        if (obj == null || getClass() != obj.getClass()) return false;
        
    // obj를 myClass 타입으로 캐스팅, id와 name을 비교하기 위해서    
        MyClass myClass = (MyClass) obj;
        
    // 마지막으로 두 객체의 id가 같고, name이 같다면 true 반환
        return id == myClass.id &&
                Objects.equals(name, myClass.name);
    }

    두 객체의 id와 name을 비교해 객체가 동일한지 아닌지 파악
    주로 컬렉션에서 객체를 찾거나 비교할 때 사용한다.
}

*/