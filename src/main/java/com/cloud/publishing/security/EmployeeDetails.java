package com.cloud.publishing.security;

import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class EmployeeDetails implements UserDetails {
    private final String email;
    private final String password;
    private final boolean chiefEditor;
    private static final String ROLE_CHIEF_EDITOR = "ROLE_CHIEF_EDITOR";
    private static final String ROLE_EMPLOYEE = "ROLE_EMPLOYEE";

    public EmployeeDetails(String email, String password, boolean chiefEditor) {
        this.email = email;
        this.password = password;
        this.chiefEditor = chiefEditor;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (chiefEditor) {
            return List.of(new SimpleGrantedAuthority(ROLE_CHIEF_EDITOR));
        } else {
            return List.of(new SimpleGrantedAuthority(ROLE_EMPLOYEE));
        }
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

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