package com.example.bookstore.service.impl;

import com.example.bookstore.model.Role;
import com.example.bookstore.model.User;
import com.example.bookstore.model.UserPrincipal;
import com.example.bookstore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomOauth2Service extends DefaultOAuth2UserService {
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // Get the user information from provider (google)
        OAuth2User oauth2User = super.loadUser(userRequest);
        Map<String, Object> attributes = oauth2User.getAttributes();
//        System.out.println("Attribute when login with google: " + attributes);

        String email = (String) attributes.get("email");
        String name = (String) attributes.get("name");
        String username = email.substring(0, email.indexOf("@"));
        String avatar = (String) attributes.get("picture");
//        String provider = userRequest.getClientRegistration().getRegistrationId();

        Optional<User> userOptional = userRepository.findByEmail(email);
        User user = null;
        if(userOptional.isPresent()){
            // existed user
            // update user information here if there are any changes
            user = userOptional.get();
        } else {
            user = User.builder()
                    .username(username)
                    .password(null)
                    .email(email)
                    .name(name)
                    .avatar(avatar)
                    .role(Role.builder().id(1).name("User").build()) // role User
                    .status("active")
                    .build();
            user = userRepository.save(user);
        }

        if(!user.getStatus().equalsIgnoreCase("active")) {
            throw new DisabledException("User was blocked");
        }

        return UserPrincipal.createFrom(user);
    }
}
