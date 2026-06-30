package com.fashion.ecommerce.service;

import com.fashion.ecommerce.dto.user.UserUpdateRequestDto;
import com.fashion.ecommerce.dto.user.CreateAdminRequestDto;
import com.fashion.ecommerce.entity.UserEntity;

import java.util.List;


public interface UserService{

    UserEntity getMyProfile(String email);

    UserEntity updateMyProfile(String email, UserUpdateRequestDto dto);

    void deleteMyAccount(String email);

    List<UserEntity> getAllUsers();

    void deleteUser(Integer id,String currentEmail);

    void lockUser(Integer id,String currentEmail);

    void createAdmin(CreateAdminRequestDto dto);

    void changeRole(Integer id,String role);

}