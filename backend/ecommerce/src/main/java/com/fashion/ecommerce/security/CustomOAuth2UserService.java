package com.fashion.ecommerce.security;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.fashion.ecommerce.entity.UserEntity;
import com.fashion.ecommerce.repository.UserRepository;


@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    public CustomOAuth2UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) {

        OAuth2User oauthUser = super.loadUser(request);

        String provider = request.getClientRegistration().getRegistrationId();

        String providerId;
        String email = null;
        String name;

        if (provider.equals("google")) {
            providerId = oauthUser.getAttribute("sub");
            email = oauthUser.getAttribute("email");
            name = oauthUser.getAttribute("name");
        } else {
            providerId = oauthUser.getAttribute("id");
            email = oauthUser.getAttribute("email");
            name = oauthUser.getAttribute("name");
        }

        UserEntity user = userRepository.findByProviderAndProviderId(provider, providerId);

        if (user == null) {
            user = new UserEntity();
            user.setProvider(provider);
            user.setProviderId(providerId);
            user.setFullname(name);
            user.setEmail(email);
            user.setIsActive(true);

            userRepository.save(user);
        }

        return oauthUser;
    }
}