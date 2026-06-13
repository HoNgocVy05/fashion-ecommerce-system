package com.fashion.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.fashion.ecommerce.dto.LoginDto;
import com.fashion.ecommerce.dto.RegisterDto;
import com.fashion.ecommerce.entity.UserEntity;
import com.fashion.ecommerce.exception.ApiException;
import com.fashion.ecommerce.repository.UserRepository;
import com.fashion.ecommerce.security.JwtUtil;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private JwtUtil jwtUtil;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // register
    @Override
    public String register(RegisterDto dto) {

        if (userRepository.findByEmail(dto.getEmail()) != null) {
            throw new ApiException("Email đã tồn tại", HttpStatus.BAD_REQUEST);
        }

        UserEntity user = new UserEntity();
        user.setFullname(dto.getFullname());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setGender(dto.getGender());
        user.setPhoneNumber(dto.getPhoneNumber());

        userRepository.save(user);

        return "Đăng ký thành công";
    }

    // login
    @Override
    public String login(LoginDto dto) {

        UserEntity user = userRepository.findByEmail(dto.getEmail());

        if (user == null) {
            throw new ApiException("Email không tồn tại", HttpStatus.NOT_FOUND);
        }

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new ApiException("Sai mật khẩu", HttpStatus.UNAUTHORIZED);
        }

        // Generate token
        return jwtUtil.generateToken(user.getEmail());
    }
}