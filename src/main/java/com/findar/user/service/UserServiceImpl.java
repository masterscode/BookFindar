package com.findar.user.service;

import com.findar.role.model.Permission;
import com.findar.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(user -> User.builder()
                        .username(user.getUsername())
                        .accountExpired(user.isAccountNonExpired())
                        .password(user.getPassword())
                        .disabled(user.isEnabled())
                        .accountLocked(user.isAccountNonLocked())
                        .accountExpired(user.isAccountNonExpired())
                        .authorities(
                                user.getRole().getPermissions().stream().map(Permission::getName)
                                        .map(SimpleGrantedAuthority::new)
                                        .collect(Collectors.toSet())
                        )
                        .build()
                )
                .orElseThrow(() -> new UsernameNotFoundException("Unknown user"));
    }
}
