package com.findar.user.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService  extends UserDetailsService {
    com.findar.user.model.User loadUser(String username) throws UsernameNotFoundException;
}
