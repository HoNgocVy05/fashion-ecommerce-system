package com.fashion.ecommerce.service;

import com.fashion.ecommerce.dto.UserUpdateDto;
import com.fashion.ecommerce.entity.UserEntity;

import java.util.List;


public interface UserService{

    UserEntity getMyProfile(String email);

    UserEntity updateMyProfile(String email,UserUpdateDto dto);

    void deleteMyAccount(String email);

    List<UserEntity> getAllUsers();

    UserEntity updateUser(Integer id,String currentEmail,UserUpdateDto dto);

    void deleteUser(Integer id,String currentEmail);

    void lockUser(Integer id,String currentEmail);

    void createAdmin(UserUpdateDto dto);

    void changeRole(Integer id,String role);

}