package com.gravityer.authservice.controllers;

import com.gravityer.authservice.Dtos.Request.UserLoginRequestDto;
import com.gravityer.authservice.Dtos.Response.UserCreateResponseDto;
import com.gravityer.authservice.Dtos.Response.UserLoginResponseDto;
import com.gravityer.authservice.Dtos.UserRegisterDto;
import com.gravityer.authservice.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class authController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<BaseResponse<UserCreateResponseDto>> register(@Valid @RequestBody UserRegisterDto userRegisterDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.createUser(userRegisterDto));
    }

    @PostMapping("/login")
    public ResponseEntity<BaseResponse<UserLoginResponseDto>> postUserLoginResponseDto(@Valid @RequestBody UserLoginRequestDto userLoginRequestDto) {
        return ResponseEntity.ok(authService.loginUser(userLoginRequestDto));
    }
}
