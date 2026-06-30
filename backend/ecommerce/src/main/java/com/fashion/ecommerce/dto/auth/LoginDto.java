package com.fashion.ecommerce.dto.auth;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class LoginDto {

    @NotBlank(message = "Email không được để trống")
    private String email;

    @NotBlank(message = "Password không được để trống")
    private String password;
}