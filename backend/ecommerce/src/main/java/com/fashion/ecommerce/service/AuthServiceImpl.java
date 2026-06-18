package com.fashion.ecommerce.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.fashion.ecommerce.dto.ApiResponse;
import com.fashion.ecommerce.dto.LoginDto;
import com.fashion.ecommerce.dto.RegisterDto;
import com.fashion.ecommerce.entity.RoleEntity;
import com.fashion.ecommerce.entity.UserEntity;
import com.fashion.ecommerce.exception.ApiException;
import com.fashion.ecommerce.repository.RoleRepository;
import com.fashion.ecommerce.repository.UserRepository;
import com.fashion.ecommerce.security.JwtUtil;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder;

    // constructor injection, instead of field injection with @Autowired
    public AuthServiceImpl(
            UserRepository userRepository,
            RoleRepository roleRepository,
            JwtUtil jwtUtil
    ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    // register
    @Override
    public String register(RegisterDto dto) {

        if (userRepository.findByEmail(dto.getEmail()) != null) {
            throw new ApiException("Email đã tồn tại", HttpStatus.BAD_REQUEST);
        }

        RoleEntity roleUser = roleRepository.findByName("USER");

        if (roleUser == null) {
            throw new ApiException("Role không hợp lệ", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        UserEntity user = new UserEntity();
        user.setFullname(dto.getFullname());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setGender(dto.getGender());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setRole(roleUser);

        userRepository.save(user);

        return "Đăng ký thành công";
    }

    // login
    @Override
    public ApiResponse<String> login(LoginDto dto) {

        UserEntity user = userRepository.findByEmail(dto.getEmail());

        if (user == null) {
            throw new ApiException("Email không tồn tại", HttpStatus.NOT_FOUND);
        }

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new ApiException("Sai mật khẩu", HttpStatus.UNAUTHORIZED);
        }

        String token = jwtUtil.generateToken(user.getEmail());

        return new ApiResponse<>(
                "Đăng nhập thành công",
                token
        );
    }
}