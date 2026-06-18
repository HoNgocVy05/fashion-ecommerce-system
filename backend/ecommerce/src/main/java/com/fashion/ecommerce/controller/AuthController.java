package com.fashion.ecommerce.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fashion.ecommerce.dto.ApiResponse;
import com.fashion.ecommerce.dto.LoginDto;
import com.fashion.ecommerce.dto.RegisterDto;
import com.fashion.ecommerce.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // register 
    @PostMapping("/register")
    public Object register(@Valid @RequestBody RegisterDto request) {
        return authService.register(request);
    }

    // login
    @PostMapping("/login")
    public ApiResponse<String> login(@RequestBody LoginDto request) {
        return authService.login(request);
    }
}