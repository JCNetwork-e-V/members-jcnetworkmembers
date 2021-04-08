package com.jcnetwork.members.security;

import com.jcnetwork.members.security.model.AccountRole;
import com.jcnetwork.members.security.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

public class AccountDetails implements UserDetails {

    private User user;

    public AccountDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<AccountRole> roles = user.getRoles();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        if (roles != null) {
            for (AccountRole role : roles) {
                authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName()));
            }
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getAccount().getPassword();
    }

    @Override
    public String getUsername() {
        return user.getAccount().getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.getAccount().getIsAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.getAccount().getIsAccountEnabled();
    }
}