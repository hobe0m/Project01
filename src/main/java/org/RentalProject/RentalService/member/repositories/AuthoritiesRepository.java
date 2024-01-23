package org.RentalProject.RentalService.member.repositories;

import org.RentalProject.RentalService.member.entities.Authorities;
import org.springframework.data.jpa.repository.JpaRepository;
// AuthoritiesRepository : 권한에 대한 데이터베이스 조작을 담당 즉, 권한 정보를 다루는 리포지토리
// Spring Data Jpa가 Authorities 엔티티와 관련된 데이터베이스 조작을 수행하는 메소드를 자동 생성 및 구현
// JpaRepository<Authorities, Long> : AuthoritiesRepository가 JpaRepository 확장
// Authorities : 데이터베이스에서 다루고자 하는 엔티티
// Long : 엔티티의 식별자(ID)가 Long 형식을 가지고 있다는 것을 나타낸다.
//  - 각 권한은 데이터베이스에서 고유한 Long 형식의 식별자를 가진다.

/* 
Spring Data JPA
 - save : 엔티티를 저장하거나 업데이트, 이미 존재한다면 업데이트 없다면 새로운 엔티티 저장
 - findById(ID id) : 주어진 ID로 엔티티를 찾는다
 - findAll() : 모든 엔티티를 조회
 - deleteById(ID id): 주어진 ID의 엔티티를 삭제
 - count(): 엔티티의 총 수를 반환
 - findBy{Property}({Property} value): 특정 속성의 값을 기반으로 엔티티를 조회
  - {Property}는 엔터티의 필드명
  - findBy(찾고 싶은 엔티티) (엔티티의 데이터형, 엔티티) : 이런식으로 쿼리 메소드 생성 가능
  - 예를 들어 findByEmail(String email) 이런 식!
*/
public interface AuthoritiesRepository extends JpaRepository<Authorities, Long> {
}
