package com.findar.auth.controller;

import com.findar.auth.dto.LoginRequest;
import com.findar.auth.dto.LoginResponse;
import com.findar.auth.service.AuthService;
import com.findar.common.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> doLogin(@Valid @RequestBody LoginRequest request) {

        return ResponseEntity.ok(authService.doLogin(request));

    }
}
