package com.fashion.ecommerce.service;

import com.fashion.ecommerce.dto.LoginDto;
import com.fashion.ecommerce.dto.RegisterDto;

public interface AuthService {

    String register(RegisterDto dto);

    String login(LoginDto dto);
}