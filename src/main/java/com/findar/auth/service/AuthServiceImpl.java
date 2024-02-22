package com.findar.auth.service;


import com.findar.auth.dto.LoginRequest;
import com.findar.auth.dto.LoginResponse;
import com.findar.common.ApiResponse;
import com.findar.common.JsonUtil;
import com.findar.exception.BadRequestException;
import com.findar.security.JWTUtil;
import com.findar.user.model.User;
import com.findar.user.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserServiceImpl userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ApiResponse<LoginResponse> doLogin(LoginRequest request) {
        final User userDetails = userService.loadUser(request.getUsername());

        if (!passwordEncoder.matches(request.getPassword(), userDetails.getPassword()))
            throw new BadRequestException("Invalid username/password combination");

        final String userDetailsJson = JsonUtil.toJson(userDetails);
        final String token = JWTUtil.createToken(userDetailsJson);
        Collection<String> authorities = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.toSet());


        return ApiResponse.<LoginResponse>builder()
                .message("User successfully authenticated")
                .data(new LoginResponse(token, userDetails.getUsername(), authorities))
                .build();
    }
}
