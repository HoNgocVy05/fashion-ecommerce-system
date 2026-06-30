package com.fashion.ecommerce.dto.user;

import lombok.Data;


@Data
public class CreateAdminRequestDto {

    private String email;

    private String fullname;

    private String phoneNumber;

    private String password;

}