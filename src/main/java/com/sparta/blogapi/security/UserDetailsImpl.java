package com.sparta.blogapi.security;

import com.sparta.blogapi.entity.User;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

//UserDetails 인터페이스 구현
// 사용자 인증과 권한 관리를 위한 정보를 제공하는 클래스
public class UserDetailsImpl implements UserDetails {

    private final User user; // User 객체를 필드로 지정

    public UserDetailsImpl(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //사용자의 권한 정보 반환 -> 아직 권한정보 구현 안함
        return null;
    }


    // 사용자 계정 상태확인 메서드
    // true => 사용 가능
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
