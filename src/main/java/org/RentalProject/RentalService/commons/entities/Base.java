package org.RentalProject.RentalService.commons.entities;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Setter // 필드 생성 시 Setter 메소드 자동 생성(보이진 않음), set.*(사용 가능)
@Getter // 필드 생성 시 Getter 메소드 자동 생성(보이진 않음), get.*(사용 가능)
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
/*
 @EntityListeners(AuditingEntityListener.class)
  * 그냥은 못쓰고 설정을 해주어야 쓸 수 있다 - MvcConfig *
 엔티티의 생명주기 이벤트를 감지하고 처리하는 리스너를 지정하는 애노테이션(날짜와 시간 설정)
 엔티티의 생성(CREATE), 수정(UPDATE) 시점에 자동으로 특정 동작을 수행 할 수 있다.
 엔티티에 변경이 발생할 때, 그에 따라 특정 동작을 수행하도록 설정하는 것
 주로 생성일자(createdAt), 수정일자(updatedAt)을 자동으로 업데이트 하는데에 사용
 @CreatedDate, @LastModifiedDate등을 컬럼에 사용해서 정의

 @MappedSuperclass
 @Entity가 있고, @MappedSuperclass를 가진 클래스를 상속하면 @MappedSuperclass의 매핑 정보를 상속받는다.
 즉, 부모 클래스에 정의된 매핑 정보가 하위 엔티티 클래스에 적용되어 데이터베이스 테이블에 매핑된다.
 부모 클래스는 테이블을 생성하지 않고, 매핑 정보만 제공한다.
 따라서 하나의 클래스로 여러 매핑정보를 생성하여, 그것을 여러 하위 클래스의 데이터베이스에 적용할 수 있다.

*/

// 매핑(Mapping) : 객체와 데이터베이스 간의 관계를 정의
// 구체적인 예시 : 엔티티 클래스의 필드가 데이터베이스의 테이블의 컬럼에 매핑되고, 객체 간 관계가 데이터베이스의 관계로 표현되는 것
// 객체 간의 관계는 동일한 열을 가질 때, 그것을 연결해주는 것을 의미
// 로그인 시 아이디와 회원 가입 시 아이디는 같은 것들을 말한다.

// 매핑 정보(Mapping information) : 매핑을 지정하는 설정이나 규칙
// 구체적인 예시 : @Entity, @Table, @Column, @JoinColumn 등을 사용하여 매핑 정보 지정
/* @Entity
   해당 클래스가 JPA 엔티티임을 나타낸다.

   @Entity가 붙은 클래스는 데이터베이스의 테이블과 매핑되고, 이 클래스의 객체가 데이터베이스의 엔티티로 관리된다.
    * 테이블(Table) : 데이터베이스에 실제로 저장되는 구조, 열과 행으로 이루어져 있으며 각 열은 특정 데이터 타입의 속성을 가진다.
    * 엔티티(Entity) : 데이터베이스의 테이블과 매핑되는 자바 객체
    * JPA 엔티티 : 데이터베이스에 저장되거나 검색되어지는 Java 객체, @Entity가 붙은 클래스는 JPA에 의해 엔티티로 인식되고
                  JPA는 엔티티 객체들을 데이터베이스와 연결하여 데이터베이스 작업을 수행한다.

   @Table
   엔티티 클래스와 데이터베이스 테이블 간 매핑 정보를 제공
   @Table(name = 테이블명) - 클래스명이 매핑된다.
   클래스가 연결되는 테이블의 이름이나 기타 속성을 지정할 때 사용한다.

   @Column
   엔티티 클래스의 필드와 데이터베이스 테이블 컬럼 간의 매핑정보를 제공
   특정 필드가 데이터베이스 테이블의 어떤 컬럼에 매핑되는지 지정
   컬럼의 이름, 길이, Null 값 허용 여부 등을 설정할 수 있다.

   @JoinColumn
   연관 관계에서 외래 키를 가지는 컬럼을 지정
   주로 @ManyToOne, @OneToOne 등의 연관 관계에서 사용되고, 어떤 컬럼을 외래 키로 사용할 지 명시
    * 외래 키는 다른 테이블의 기본 키와 연결되어 두 테이블의 참조를 나타낸다.
    * 외래 키를 가진 쪽이 자식 테이블, 기본 키를 가진 쪽이 부모 테이블
    * 관계 정의, 무결성 제약(참조하는 기본키와 일치하는 값만 가짐), 조인, 캐스케이드 삭제 및 업데이트(기본 키가 바뀌면 외래 키도 자동으로 바뀜)
*/

// @EnalbleJpaAuditing에 의해, 두 가지 모두 자동으로 관리된다.

public abstract class Base {
    @CreatedDate // 생성 일자
    @Column(updatable = false) // 생성 일자는 업데이트 되면 안되기 때문에 false로 지정
    private LocalDateTime createdAt;

    @LastModifiedDate // 마지막 수정 일자
    @Column(insertable = false) // 수정 일자는 생성될 때는 넣으면 안되기 때문에 false로 지정
    private LocalDateTime modifiedAt;

}