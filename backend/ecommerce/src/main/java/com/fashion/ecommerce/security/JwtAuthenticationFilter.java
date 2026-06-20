package com.fashion.ecommerce.security;


import java.io.IOException;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import org.springframework.stereotype.Component;

import org.springframework.web.filter.OncePerRequestFilter;


import com.fashion.ecommerce.entity.UserEntity;
import com.fashion.ecommerce.repository.UserRepository;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;



@Component
public class JwtAuthenticationFilter
        extends OncePerRequestFilter {



    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    )
            throws ServletException, IOException {

        String authHeader =
                request.getHeader("Authorization");

        String token = null;
        String email = null;

        // lấy token
        if(authHeader != null
                && authHeader.startsWith("Bearer ")) {
            token =
                    authHeader.substring(7);

            try {
                if(jwtUtil.validateToken(token)) {
                    email =
                            jwtUtil.extractUsername(token);
                }

            } catch(Exception e){
                System.out.println(
                        "JWT lỗi: "
                                + e.getMessage()
                );
            }
        }

        if(email != null
                && SecurityContextHolder
                .getContext()
                .getAuthentication() == null) {

            UserEntity user =
                    userRepository
                            .findByEmail(email);


            if(user != null
                    && user.getRole() != null) {

                String role =
                        user.getRole()
                                .getName();

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                email,
                                null,
                                List.of(
                                        new SimpleGrantedAuthority(
                                                "ROLE_" + role
                                        )
                                )
                        );

                authentication.setDetails(

                        new WebAuthenticationDetailsSource()
                                .buildDetails(request)

                );
                SecurityContextHolder
                        .getContext()
                        .setAuthentication(authentication);

            }
        }

        filterChain.doFilter(
                request,
                response
        );
    }

}