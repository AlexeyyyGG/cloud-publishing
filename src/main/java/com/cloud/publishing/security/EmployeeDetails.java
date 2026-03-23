package com.cloud.publishing.security;

import com.cloud.publishing.model.Employee;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class EmployeeDetails implements UserDetails {
    private final Employee employee;

    public EmployeeDetails(Employee employee) {
        this.employee = employee;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (employee.chiefEditor()) {
            return List.of(new SimpleGrantedAuthority("ROLE_CHIEF_EDITOR"));
        } else {
            return List.of(new SimpleGrantedAuthority("ROLE_EMPLOYEE"));
        }
    }

    @Override
    public String getPassword() {
        return this.employee.password();
    }

    @Override
    public String getUsername() {
        return this.employee.email();
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