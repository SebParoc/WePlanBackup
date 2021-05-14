package com.bakaful.project2021.user_security;

import com.bakaful.project2021.domains.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class WePlanUserDetails implements UserDetails{

    private User user;

    public WePlanUserDetails (User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities () {
        return null;
    }

    @Override
    public String getPassword () {
        return user.getPassword();
    }

    @Override
    public String getUsername () {
        return user.getUsername();
    }

    public String getEmail() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired () {
        return true;
    }

    @Override
    public boolean isAccountNonLocked () {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired () {
        return true;
    }

    @Override
    public boolean isEnabled () {
        return true;
    }

    public List<String> getFriendList() {
        return user.getFriendList();
    }

    public String getFullName() {
        return user.getFirstName() + " " + user.getLastName();
    }
}
