package com.kabasakalis.springifyapi.controllers;

import com.kabasakalis.springifyapi.assemblers.PagedCustomResourcesAssembler;
import com.kabasakalis.springifyapi.assemblers.RoleResourceAssembler;
import com.kabasakalis.springifyapi.assemblers.UserResourceAssembler;
import com.kabasakalis.springifyapi.domain.Role;
import com.kabasakalis.springifyapi.domain.SpringifyUser;
import com.kabasakalis.springifyapi.exceptions.BaseException;
import com.kabasakalis.springifyapi.repositories.RoleRepository;
import com.kabasakalis.springifyapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.web.HateoasPageableHandlerMethodArgumentResolver;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Collections;
import java.util.HashSet;

@RepositoryRestController
@RequestMapping("/users")
public class UserController extends AbstractBaseRestController<SpringifyUser> {

    private UserRepository repository;
    private RoleRepository roleRepository;
    private RoleResourceAssembler roleResourceAssembler;
    private PagedCustomResourcesAssembler<Role> pagedRoleAssembler;
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Autowired
    public UserController(UserRepository repository,
                          UserResourceAssembler assembler,
                          RoleResourceAssembler roleResourceAssembler,
                          RoleRepository roleRepository,
                          BCryptPasswordEncoder bCryptPasswordEncoder,
                          ApplicationContext appContext) {
        super(repository, appContext, assembler);
        HateoasPageableHandlerMethodArgumentResolver resolver = new HateoasPageableHandlerMethodArgumentResolver();
        this.pagedRoleAssembler = new PagedCustomResourcesAssembler<Role>(resolver, null);
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.roleResourceAssembler = roleResourceAssembler;
        this.roleRepository = roleRepository;
    }

    @RequestMapping(
            method = RequestMethod.POST,
            path = "/sign-up",
            produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> signup(@RequestBody SpringifyUser springifyUser) {
        if (!springifyUser.getPassword().equals(springifyUser.getPasswordConfirm()))
            throw new BaseException(HttpStatus.UNAUTHORIZED, "Password is not confirmed", null);
        springifyUser.setPassword(bCryptPasswordEncoder.encode(springifyUser.getPassword()));
        Role userRole = roleRepository.findByName("ROLE_USER");
        springifyUser.setRoles(new HashSet<Role>(Collections.singletonList(userRole)));
        return addOne(springifyUser);
    }

        @RequestMapping(
            method = RequestMethod.GET,
            path = "/{id}/roles",
            produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity getRoles(
            Pageable pageRequest,
            @PathVariable Long id) {
        Page<Role> pagedUserRoles = roleRepository.findAllBySpringifyUsers(repository.getOne(id), pageRequest);
        return getAssociatedResources(roleResourceAssembler, pagedRoleAssembler, pagedUserRoles, pageRequest);
    }



}
