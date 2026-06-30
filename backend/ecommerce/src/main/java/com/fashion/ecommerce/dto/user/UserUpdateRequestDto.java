package com.fashion.ecommerce.dto.user;

import lombok.Data;


@Data
public class UserUpdateRequestDto {

    private String fullname;

    private String gender;

    private String phoneNumber;

}