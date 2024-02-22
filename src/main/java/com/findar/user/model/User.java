package com.findar.user.model;

import com.findar.common.BaseEntity;
import com.findar.role.model.Permission;
import com.findar.role.model.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@Table(name = "findar_users")
@SQLRestriction("del_flag = 'N'")
public class User extends BaseEntity implements UserDetails {

    private String username;
    private String password;
    private String firstName;
    private String lastName;


    @ManyToOne
    private Role role;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Objects.isNull(role) ? Collections.emptySet()
        : role.getPermissions().stream().map(Permission::getName)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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
