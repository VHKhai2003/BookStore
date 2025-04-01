package com.example.bookstore.model;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
@Slf4j
public class UserPrincipal implements UserDetails, OAuth2User {
    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;
    private Map<String, Object> attributes;

    @Override
    public String getName() {
        return username;
    }

    public static UserPrincipal createFrom(User user){
        List<SimpleGrantedAuthority> userAuthorities
                = List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().getName()));
        log.info("Login information: username={}, authorities={}",
                user.getUsername(), userAuthorities);

        return UserPrincipal.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(userAuthorities)
                .build();
    }
}
