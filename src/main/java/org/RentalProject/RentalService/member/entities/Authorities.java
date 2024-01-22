package org.RentalProject.RentalService.member.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.RentalProject.RentalService.member.Authority;

@Data
@Entity
@Table(indexes = @Index(name="uq_member_authority"
        , columnList = "member_seq, authority", unique = true))
// 데이터 베이스의 테이블에서 indexes를 통해 복합 인덱스를 만든다.
// 복합 인덱스는 두 개 이상의 열에 대한 인덱스
// member_seq와 authority 열을 함께 고려한 인덱스 생성
// 이 인덱스의 이름을 uq_member_authority로 지정
// unique = true로 이 인덱스가 고유해야 한다고 정의, member_seq와 authority의
// 조합열이 중복되어서는 안된다.
// 회원 하나당 권한은 하나만 있어야 하기 때문에 설정
public class Authorities {
    // 상수로 권한을 추가

    @Id
    @GeneratedValue
    private Long seq;

    // 회원쪽은 한 개, 권한쪽은 여러 개(ManyToOne)

    @ManyToOne(fetch = FetchType.LAZY)
    // 연관된 엔티티를 지연 로딩 방식으로 가져온다.
    // 프로그램이 특정 엔티티를 조회할 때까지 해당 엔티티의 관계 엔티티를 데이터베이스에서
    // 가져오지 않는다.
    // Member 엔티티가 다른 엔티티와 연관이 되어 있다면, Member를 조회할 때 바로 가져
    // 오는 것이 아니라 실제로 필요한 순간에 가져온다.
    // 따라서 데이터베이스 쿼리 및 자원 사용을 최적화하여 성능을 향상시킨다.

    @JoinColumn(name = "member_seq")
    // Authority 엔티티에서 Member 엔티티를 참조한다
    // 이 때, member_seq라는 외래 키 컬럼을 사용해 두 엔티티를 연결한다.
    // Authority에 member_seq라는 컬럼이 있고, 그게 외래 키로 Member와 연결된다.
    // Authority가 주인
    private Member member;

    @Enumerated(EnumType.STRING)
    // Enum 타입임을 알리고, String 형태라는 것을 알려준다.
    @Column(length = 15, nullable = false)
    private Authority authority;
}
