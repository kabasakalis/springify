package com.kabasakalis.springifyapi.services;


import com.kabasakalis.springifyapi.models.Role;
import com.kabasakalis.springifyapi.models.SpringifyUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import com.kabasakalis.springifyapi.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private UserRepository repository;
    private SpringifyUser springifyUser;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.repository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SpringifyUser springifyUser = repository.findByUsername(username);
        if (springifyUser == null) {
            throw new UsernameNotFoundException(username);
        }
        this.springifyUser = springifyUser;
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (Role role : springifyUser.getRoles()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        }

        return new User(springifyUser.getUsername(), springifyUser.getPassword(), grantedAuthorities);

    }

    public SpringifyUser getSpringifyUser() {
        return springifyUser;
    }

    public void setSpringifyUser(SpringifyUser springifyUser) {
        this.springifyUser = springifyUser;
    }
}

