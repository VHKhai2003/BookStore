package com.example.bookstore.service.impl;

import com.example.bookstore.model.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.bookstore.model.User;
import com.example.bookstore.repository.UserRepository;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    // this method run when user login
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username + " not found!"));
        if(!user.getStatus().equalsIgnoreCase("active")) {
            throw new DisabledException("User was blocked");
        }
        System.out.println(user.getUsername() + " ---  " + user.getRole().getName());
        return UserPrincipal.createFrom(user);
    }

}
