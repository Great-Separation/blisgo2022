package com.blisgo.security.auth;

import com.blisgo.domain.entity.Account;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

public class PrincipalDetails implements UserDetails, OAuth2User {
    private final Account account;
    private Map<String, Object> attributes;

    // 일반 시큐리티 로그인시 사용
    public PrincipalDetails(Account account) {
        this.account = account;
    }

    // OAuth2.0 로그인시 사용
    public PrincipalDetails(Account account, Map<String, Object> attributes) {
        this.account = account;
        this.attributes = attributes;
    }

    public Account getUser() {
        return account;
    }

    @Override
    public String getPassword() {
        return account.getPass();
    }

    @Override
    public String getUsername() {
        return account.getNickname();
    }

    public String getProfileImage(){return account.getProfileImage();}

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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        Collection<GrantedAuthority> collet = new ArrayList<GrantedAuthority>();
//        collet.add(()->{ return account.getRole();});
//        return collet;
        return null;
    }

    // 리소스 서버로 부터 받는 회원정보
    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    // User의 PrimaryKey
    @Override
    public String getName() {
        return account.getMemNo() + "";
    }
}
