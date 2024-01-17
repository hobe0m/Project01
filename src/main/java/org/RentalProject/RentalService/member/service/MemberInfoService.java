package org.RentalProject.RentalService.member.service;

// UserDedatailsService의 구현체

import lombok.RequiredArgsConstructor;
import org.RentalProject.RentalService.member.entities.Member;
import org.RentalProject.RentalService.member.repositories.MemberRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberInfoService implements UserDetailsService {
    // 마찬가지로 추상클래스이기 때문에 추상메소드들을 다시 재정의 해주어야 한다.

    // DB에서 조회를 해야하기 때문에 Repository의 의존성을 주입
    private final MemberRepository memberRepository;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 이메일이 우선, 이메일이 없으면 아이디
        Member member = memberRepository.findByEmail(username)
                .orElseGet(() -> memberRepository.findById(username)
                        .orElseThrow(() -> new UsernameNotFoundException(username)));

        // 람다를 제거하고 .orElseThrow(UsernameNotFoundException::new); 이렇게 쓸 수도 있다.
        // MemberNotFoundException도 좋지만, 이미 정해져 있는 UsernameNotFoundException을 사용한다.
        // UsernameNotFoundException는 orElseThrow에 의해 호출될때만 실행된다.

        // () -> : 람다식
        // orElseGet(() -> ...) : 이메일으로 조회한 결과가 없으면, ... 실행
        // 여기서는 memberRepository.findById(username) 이후 둘 다 없으면
        // orElseThrow를 통해 MemberNotFoundException을 발생시킨다.

        // 람다식 제거 버전 orElse 메소드 사용
        /*
        Member member = memberRepository.findByEmail(username)
                .orElse(memberRepository.findById(username)
                .orElseThrow(MemberNotFoundException::new));
         */

        // (MemberNotFoundException::new) : 람다식 사용 X 버전

        // 람다식 사용 버전
        /*
        .orElseThrow(() -> new MemberNotFoundException());
         */

        // Optional을 쓰지 않고도 말할 수 있는데 코드가 너무 길어진다.

        // 여기부터는 회원의 정보가 있을 때
        // UserDetails의 구현체기 때문에 반환하는 것이 중요
        // MemberInfo가 UserDetails의 구현체
        // UserDetailsService의 구현체는 주로 사용자 정보를 로드하고,
        //                      해당 정보를 UserDetails 객체로 감싸서 반환하는 역할을 수행

        return MemberInfo.builder()
                .email(member.getEmail())
                .id(member.getId())
                .password(member.getPassword())
                .member(member)
                .build();

        // 이미 정해져 있는 메소드, 회원의 정보 조회 시 사용

    }
}
