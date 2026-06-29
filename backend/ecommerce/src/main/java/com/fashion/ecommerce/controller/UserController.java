package com.fashion.ecommerce.controller;


import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import com.fashion.ecommerce.dto.UserUpdateDto;
import com.fashion.ecommerce.entity.UserEntity;
import com.fashion.ecommerce.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController{

    private final UserService userService;
    public UserController(UserService userService){this.userService=userService;}

    @GetMapping("/me")
    public UserEntity getMe(Authentication auth){
        return userService.getMyProfile(auth.getName());
    }

    @PutMapping("/me")
    public UserEntity updateMe(Authentication auth,@RequestBody UserUpdateDto dto){
        return userService.updateMyProfile(auth.getName(),dto);
    }

    @DeleteMapping("/me")
    public String deleteMe(Authentication auth){
        userService.deleteMyAccount(auth.getName());
        return "Deleted";
    }

    @GetMapping
    public List<UserEntity> getAll(){
        return userService.getAllUsers();
    }

    @PostMapping("/admin")
    @PreAuthorize("hasRole('SYSTEM_ADMIN')")
    public String createAdmin(@RequestBody UserUpdateDto dto){
        userService.createAdmin(dto);
        return "Admin created";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Integer id,Authentication auth){
        userService.deleteUser(id,auth.getName());
        return "Deleted";
    }

    @PutMapping("/lock/{id}")
    public String lockUser(@PathVariable Integer id,Authentication auth){
        userService.lockUser(id,auth.getName());
        return "Locked";
    }

    @PutMapping("/{id}/role/{role}")
    public String changeRole(@PathVariable Integer id,@PathVariable String role){
        userService.changeRole(id,role);
        return "Changed";
    }
}