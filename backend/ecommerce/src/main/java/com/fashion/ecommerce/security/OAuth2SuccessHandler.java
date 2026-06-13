package com.fashion.ecommerce.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fashion.ecommerce.entity.UserEntity;
import com.fashion.ecommerce.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Value("${FRONTEND_URL}")
    private String frontendUrl;

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    // constructor injection
    public OAuth2SuccessHandler(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException {

        OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();

        String provider = request.getRequestURI().contains("google") ? "google" : "facebook";

        String providerId = provider.equals("google")
                ? oauthUser.getAttribute("sub")
                : oauthUser.getAttribute("id");

        UserEntity user = userRepository.findByProviderAndProviderId(provider, providerId);

        if (user == null) {
            throw new RuntimeException("User không tồn tại sau OAuth");
        }

        // dùng email để generate token
        String token = jwtUtil.generateToken(user.getEmail());

        String redirectUrl = frontendUrl + "/oauth-success?token=" + token;

        getRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }
}