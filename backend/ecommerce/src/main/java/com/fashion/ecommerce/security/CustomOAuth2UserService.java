package com.fashion.ecommerce.security;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.fashion.ecommerce.entity.AuthProvider;
import com.fashion.ecommerce.entity.UserEntity;
import com.fashion.ecommerce.repository.UserRepository;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    // constructor injection, instead of field injection with @Autowired
    public CustomOAuth2UserService(
            UserRepository userRepository
    ) {
        this.userRepository = userRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) {

        OAuth2User oauthUser = super.loadUser(request);

        String registrationId = request.getClientRegistration().getRegistrationId();

        AuthProvider provider = registrationId.equals("google")
                ? AuthProvider.GOOGLE
                : AuthProvider.FACEBOOK;

        String providerId;
        String email;
        String name;

        if (provider == AuthProvider.GOOGLE) {
            providerId = oauthUser.getAttribute("sub");
            email = oauthUser.getAttribute("email");
            name = oauthUser.getAttribute("name");
        } else {
            providerId = oauthUser.getAttribute("id");
            email = oauthUser.getAttribute("email");
            name = oauthUser.getAttribute("name");
        }

        if (email == null) {
            email = registrationId + "_" + providerId + "@noemail.com";
        }

        UserEntity existing = userRepository.findByEmail(email);

        if (existing == null) {
            UserEntity newUser = new UserEntity();

            newUser.setEmail(email);
            newUser.setFullname(name);
            newUser.setProvider(provider);
            newUser.setIsActive(true);

            // tránh lỗi null password
            newUser.setPassword("OAUTH_USER");

            userRepository.save(newUser);
        }

        return oauthUser;
    }
}