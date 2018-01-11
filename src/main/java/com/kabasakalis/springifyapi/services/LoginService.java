package com.kabasakalis.springifyapi.services;


import com.kabasakalis.springifyapi.domain.SpringifyUser;
import com.kabasakalis.springifyapi.repositories.UserRepository;
import com.kabasakalis.springifyapi.security.LoginCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class LoginService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @SuppressWarnings("unused")
    public LoginService() {
        this(null, null);
    }

    @Autowired
    public LoginService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    public Optional<SpringifyUser> login(LoginCredentials credentials) {
        return Optional.ofNullable(userRepository.findByUsername(credentials.getUsername())).
                filter((springifyUser) ->
                        bCryptPasswordEncoder.matches(credentials.getPassword(), springifyUser.getPassword()));
    }
}


