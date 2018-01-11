
package com.kabasakalis.springifyapi.controllers;

import com.kabasakalis.springifyapi.assemblers.UserResourceAssembler;
import com.kabasakalis.springifyapi.domain.Role;
import com.kabasakalis.springifyapi.domain.SpringifyUser;
import com.kabasakalis.springifyapi.exceptions.BaseException;
import com.kabasakalis.springifyapi.repositories.RoleRepository;
import com.kabasakalis.springifyapi.repositories.UserRepository;
import com.kabasakalis.springifyapi.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Collections;
import java.util.HashSet;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(path = "/sign-up")
public class RegisterController {

    private JwtService jwtService;
    private UserResourceAssembler assembler;
    private UserRepository repository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public RegisterController(JwtService jwtService,
                            UserRepository repository,
                            RoleRepository roleRepository,
                            UserResourceAssembler assembler,
                            BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.jwtService = jwtService;
        this.roleRepository = roleRepository;
        this.repository = repository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.assembler = assembler;
    }


    @RequestMapping(
            method = RequestMethod.POST,
            produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> signup(@RequestBody SpringifyUser springifyUser) {
        if (!springifyUser.getPassword().equals(springifyUser.getPasswordConfirm()))
            throw new BaseException(HttpStatus.UNAUTHORIZED, "Password is not confirmed", null);
        springifyUser.setPassword(bCryptPasswordEncoder.encode(springifyUser.getPassword()));
        Role userRole = roleRepository.findByName("USER");
        springifyUser.setRoles(new HashSet<Role>(Collections.singletonList(userRole)));
        return saveUser(springifyUser);
    }

    ResponseEntity<Void> saveUser(SpringifyUser springifyUser) {
        SpringifyUser newUser = repository.save(springifyUser);
        HttpHeaders httpHeaders = new HttpHeaders();
        URI location_link = linkTo(methodOn(UserController.class).getOne(newUser.getId())).toUri();
        httpHeaders.setLocation(location_link);
        return new ResponseEntity(assembler.toResource(newUser), httpHeaders, HttpStatus.CREATED);
    }

}

