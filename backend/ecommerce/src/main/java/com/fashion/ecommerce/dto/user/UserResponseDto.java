package com.fashion.ecommerce.dto.user;


import lombok.Data;


@Data
public class UserResponseDto {

    private Integer id;

    private String email;

    private String fullname;

    private String gender;

    private String phoneNumber;

    private String role;

}