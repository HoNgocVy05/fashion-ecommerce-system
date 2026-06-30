package com.fashion.ecommerce.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
public class RegisterDto {

    @NotBlank(message = "Tên không được để trống")
    private String fullname;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không hợp lệ")
    private String email;

    @NotBlank(message = "Password không được để trống")
    @Size(min = 8, message = "Password tối thiểu 8 ký tự")
    private String password;

    @Pattern(regexp = "male|female|other", message = "Giới tính không hợp lệ")
    private String gender;

    @Pattern(regexp = "^[0-9]{10}$", message = "SĐT không đúng")
    private String phoneNumber;

}