package com.example.bookstore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@SuppressWarnings({ "deprecation", "removal" })
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests(authz -> authz
                .requestMatchers("/bootstrap.min.css", "/bootstrap.bundle.min.js", "/favicon.png",
                        "/fontawesome/**", "/jquery-min.js", "/images/**", "/toastr/**", "/",
                        "/auth/login", "/auth/register")
                .permitAll()
                .requestMatchers("/admin/**").hasRole("Admin")
                .anyRequest().authenticated())
                .formLogin(form -> form.loginPage("/auth/login")
                        .defaultSuccessUrl("/", false).permitAll())
                .logout(logout -> logout.logoutUrl("/auth/logout").logoutSuccessUrl("/").permitAll());
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http,
            PasswordEncoder passwordEncoder, UserDetailsService userDetailsService) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder)
                .and()
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // test without encode
        // return NoOpPasswordEncoder.getInstance();
        return new BCryptPasswordEncoder();
    }
}
