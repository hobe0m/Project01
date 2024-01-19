package org.RentalProject.RentalService.member.service;

// UserDetails의 구현체

import lombok.Builder;
import lombok.Data;
import org.RentalProject.RentalService.member.entities.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import java.util.Collection;

@Data
@Builder
public class MemberInfo implements UserDetails {
    // 추상클래스이기 때문에 추상메소드를 구현(재정의)해주어야 한다.
    // Implement Method로 자동 작성한다.
    // 아래는 필수로 구현해야 하는 메소드들(자동 작성)
    // 메소드들은 값만 바꿔주면 알아서 구현된 내용대로 동작한다.

    /* 회원의 기본 정보 */
    private String email; // 기본 정보

    private String id; // 기본 정보

    private String password; // 기본 정보

    private Member member;
    // 추가로 필요한 정보가 있을 때, 엔티티 내에서 보고 가져올 수 있게 설정

    /* Authority(권한) 부분 */
    private Collection<? extends GrantedAuthority> authorities;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
        // 권한과 인가를 담당한다.
    }

    @Override
    public String getPassword() {
        return password;
        // 비밀번호
    }

    @Override
    public String getUsername() {
        return StringUtils.hasText(email) ? email : id;
        // 이메일이 있으면 이메일로 없으면 아이디로
        // 우선 순위는 이메일이 먼저
        // 아이디
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
        // 계정의 만료 여부
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // 기본값 false에서 변경
        // 계정의 잠금 여부
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 기본값 false에서 변경
        // 비밀번호의 만료 여부
    }

    @Override
    public boolean isEnabled() {
        return true; // 기본값 false에서 변경
        // 계정의 활성화 여부
    }

    // UserDetails를 통해 회원정보를 넣어주게 되면 스프링 시큐리티에서 알아서 필요한
    // 정보만 찾아서 사용하게 된다.
    // 회원가입 할 때 필요한 아주 중요한 인터페이스(UserDetails)
}
