package com.fashion.ecommerce.service;


import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.fashion.ecommerce.dto.user.UserUpdateRequestDto;
import com.fashion.ecommerce.dto.user.CreateAdminRequestDto;
import com.fashion.ecommerce.entity.RoleEntity;
import com.fashion.ecommerce.entity.UserEntity;
import com.fashion.ecommerce.exception.ApiException;
import com.fashion.ecommerce.repository.RoleRepository;
import com.fashion.ecommerce.repository.UserRepository;


@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    public UserServiceImpl(UserRepository userRepository,RoleRepository roleRepository){
        this.userRepository=userRepository;
        this.roleRepository=roleRepository;
        this.passwordEncoder=new BCryptPasswordEncoder();
    }

    @Override
    public UserEntity getMyProfile(String email){
        UserEntity user=userRepository.findByEmail(email);
        if(user==null){
            throw new ApiException("User not found",HttpStatus.NOT_FOUND);
        }
        return user;
    }

    @Override
    public UserEntity updateMyProfile(String email,UserUpdateRequestDto dto){
        UserEntity user=userRepository.findByEmail(email);
        if(user==null){
            throw new ApiException("User not found",HttpStatus.NOT_FOUND);
        }
        if(dto.getFullname()!=null){
            user.setFullname(dto.getFullname());
        }
        if(dto.getGender()!=null){
            user.setGender(dto.getGender());
        }
        if(dto.getPhoneNumber()!=null){
            user.setPhoneNumber(dto.getPhoneNumber());
        }
        return userRepository.save(user);
    }

    @Override
    public void deleteMyAccount(String email){
        UserEntity user=userRepository.findByEmail(email);
        if(user==null){
            throw new ApiException("User not found",HttpStatus.NOT_FOUND);
        }
        userRepository.delete(user);
    }

    @Override
    public List<UserEntity> getAllUsers(){
        return userRepository.findAll();
    }

    @Override
    public void deleteUser(Integer id,String currentEmail){
        UserEntity target=userRepository.findById(id).orElseThrow();
        UserEntity current=userRepository.findByEmail(currentEmail);
        if(current==null){
            throw new ApiException("User not found",HttpStatus.NOT_FOUND);
        }
        String targetRole = target.getRole() != null
                ? target.getRole().getName()
                : "";

        String currentRole = current.getRole() != null
                ? current.getRole().getName()
                : "";

        if(targetRole.equals("SYSTEM_ADMIN")){
            throw new ApiException(
                    "ADMIN cannot delete SYSTEM_ADMIN",
                    HttpStatus.FORBIDDEN
            );
        }

        if(targetRole.equals("ADMIN")
                && currentRole.equals("ADMIN")
                && !target.getEmail().equals(currentEmail)){
            throw new ApiException(
                    "ADMIN cannot delete ADMIN",
                    HttpStatus.FORBIDDEN
            );
        }
        userRepository.delete(target);
    }
    @Override
    public void lockUser(Integer id,String currentEmail){
        UserEntity target=userRepository.findById(id).orElseThrow();
        UserEntity current=userRepository.findByEmail(currentEmail);
        if(current==null){
            throw new ApiException("User not found",HttpStatus.NOT_FOUND);
        }

        if(target.getRole()!=null
                && current.getRole()!=null
                && target.getRole().getName().equals("ADMIN")
                && current.getRole().getName().equals("ADMIN")
                && !target.getEmail().equals(currentEmail)){
            throw new ApiException("ADMIN cannot lock ADMIN", HttpStatus.FORBIDDEN
            );
        }
        target.setIsActive(false);
        userRepository.save(target);
    }

    @Override
    public void createAdmin(CreateAdminRequestDto dto){

        RoleEntity role = roleRepository.findByName("ADMIN");
        if(role==null){
            throw new ApiException(
                    "ADMIN role not found",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
        UserEntity admin = new UserEntity();
        admin.setEmail(dto.getEmail());
        admin.setFullname(dto.getFullname());
        admin.setPhoneNumber(dto.getPhoneNumber());
        admin.setPassword(passwordEncoder.encode(dto.getPassword()));
        admin.setRole(role);
        admin.setIsActive(true);
        userRepository.save(admin);
    }

    @Override
    public void changeRole(Integer id,String roleName){
        UserEntity user=userRepository.findById(id).orElseThrow();
        RoleEntity role=roleRepository.findByName(roleName);
        user.setRole(role);
        userRepository.save(user);
    }

}