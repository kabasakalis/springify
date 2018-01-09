package com.kabasakalis.springifyapi.controllers;

import com.kabasakalis.springifyapi.exceptions.FailedLoginException;
import com.kabasakalis.springifyapi.hateoas.UserResourceAssembler;
import com.kabasakalis.springifyapi.models.SpringifyUser;
import com.kabasakalis.springifyapi.security.LoginCredentials;
import com.kabasakalis.springifyapi.services.JwtService;
import com.kabasakalis.springifyapi.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping(path = "/login")
public class LoginController {

    private LoginService loginService;
    private JwtService jwtService;
    private UserResourceAssembler userResourceAssembler;

    @Autowired
    public LoginController(LoginService loginService,
                           JwtService jwtService,
                           UserResourceAssembler userResourceAssembler) {
        this.loginService = loginService;
        this.jwtService = jwtService;
        this.userResourceAssembler = userResourceAssembler;
    }

    @RequestMapping(
            method = POST,
            produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<SpringifyUser> login(@RequestBody LoginCredentials credentials) {
        return loginService.login(credentials)
                .map(springifyUser -> {
                    try {
                        String  jwtToken  = jwtService.tokenFor(springifyUser);
                        HttpHeaders httpHeaders = new HttpHeaders();
                        httpHeaders.add("Token", jwtToken );
                        springifyUser.setJwtToken(jwtToken);
                        return new ResponseEntity(userResourceAssembler.toResource(springifyUser),
                                httpHeaders,
                                HttpStatus.OK);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .orElseThrow(() -> new FailedLoginException(credentials));
    }
}

