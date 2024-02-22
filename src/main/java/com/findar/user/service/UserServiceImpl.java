package com.findar.user.service;

import com.findar.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return loadUser(username);
    }

    @Override
    public com.findar.user.model.User loadUser(String username) throws UsernameNotFoundException {
        return userRepository.fetchUserAndRole(username)
                .orElseThrow(() -> new UsernameNotFoundException("Unknown user"));
    }

}
