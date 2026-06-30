package com.fashion.ecommerce.service;

import com.fashion.ecommerce.dto.ApiResponse;
import com.fashion.ecommerce.dto.auth.LoginDto;
import com.fashion.ecommerce.dto.auth.RegisterDto;

public interface AuthService {

    String register(RegisterDto dto);

    public ApiResponse<String> login(LoginDto dto);
}