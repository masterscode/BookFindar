package com.findar.user.service;

import com.findar.auth.dto.CreateUserRequest;
import com.findar.user.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService  extends UserDetailsService {
    com.findar.user.model.User loadUser(String username) throws UsernameNotFoundException;

    User createUser (CreateUserRequest request);
}
