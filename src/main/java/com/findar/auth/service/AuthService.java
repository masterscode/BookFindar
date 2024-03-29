package com.findar.auth.service;

import com.findar.auth.dto.CreateUserRequest;
import com.findar.auth.dto.LoginRequest;
import com.findar.auth.dto.LoginResponse;
import com.findar.auth.dto.UserDto;
import com.findar.common.ApiResponse;

public interface AuthService {
    ApiResponse<LoginResponse> doLogin(LoginRequest request);

    ApiResponse<UserDto> register(CreateUserRequest request);
}
