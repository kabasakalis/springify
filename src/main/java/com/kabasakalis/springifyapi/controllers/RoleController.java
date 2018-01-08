package com.kabasakalis.springifyapi.controllers;

import com.kabasakalis.springifyapi.hateoas.RoleResourceAssembler;
import com.kabasakalis.springifyapi.hateoas.UserResourceAssembler;
import com.kabasakalis.springifyapi.models.Role;
import com.kabasakalis.springifyapi.models.SpringifyUser;
import com.kabasakalis.springifyapi.repositories.RoleRepository;
import com.kabasakalis.springifyapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.web.HateoasPageableHandlerMethodArgumentResolver;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;

@RepositoryRestController
@RequestMapping("/roles")
public class RoleController extends AbstractBaseRestController<Role> {

    private RoleRepository repository;
    private UserRepository userRepository;
//    private UserResourceAssembler assembler;
    private UserResourceAssembler userResourceAssembler;
    private PagedResourcesAssembler<SpringifyUser> pagedUserAssembler;
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Autowired
    public RoleController(RoleRepository repository,
                          RoleResourceAssembler assembler,
                          UserResourceAssembler userResourceAssembler,
                          UserRepository userRepository,
                          BCryptPasswordEncoder bCryptPasswordEncoder,
                          ApplicationContext appContext) {
        super(repository, appContext, assembler);
        HateoasPageableHandlerMethodArgumentResolver resolver = new HateoasPageableHandlerMethodArgumentResolver();
        this.pagedUserAssembler = new PagedResourcesAssembler<SpringifyUser>(resolver, null);
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userResourceAssembler = userResourceAssembler;
        this.userRepository = userRepository;
    }


}
