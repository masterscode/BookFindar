package com.findar.user.service;

import com.findar.role.model.Permission;
import com.findar.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.fetchUserAndRole(username)
                .map(this::buildDetails)
                .orElseThrow(() -> new UsernameNotFoundException("Unknown user"));
    }


    private UserDetails buildDetails(com.findar.user.model.User user){

        Collection<GrantedAuthority> authorities = Objects.isNull(user.getRole()) ?
                Collections.emptyList()
                : user.getRole().getPermissions().stream().map(Permission::getName)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());

        return User.builder()
                .username(user.getUsername())
                .accountExpired(user.isAccountNonExpired())
                .password(user.getPassword())
                .disabled(user.isEnabled())
                .accountLocked(user.isAccountNonLocked())
                .accountExpired(user.isAccountNonExpired())
                .authorities(authorities)
                .build();
    }
}
