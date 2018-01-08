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
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Arrays;
import java.util.HashSet;

@RepositoryRestController
@RequestMapping("/users")
public class UserController extends AbstractBaseRestController<SpringifyUser> {

    private UserRepository repository;
    private RoleRepository roleRepository;
//    private UserResourceAssembler assembler;
    private RoleResourceAssembler roleResourceAssembler;
    private PagedResourcesAssembler<Role> pagedRoleAssembler;
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
        this.pagedRoleAssembler = new PagedResourcesAssembler<Role>(resolver, null);
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.roleResourceAssembler = roleResourceAssembler;
        this.roleRepository = roleRepository;
    }


//    @PostMapping("/sign-up")
//    public void signUp(@RequestBody ApplicationUser user) {
//        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
//        applicationUserRepository.save(user);
//    }


    @RequestMapping(
            method = RequestMethod.POST,
            path = "/sign-up",
            produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> signup(@RequestBody SpringifyUser springifyUser) {

        springifyUser.setPassword(bCryptPasswordEncoder.encode(springifyUser.getPassword()));
        springifyUser.setPassword(bCryptPasswordEncoder.encode(springifyUser.getPassword()));
         Role moderatorRole = roleRepository.findByName("ROLE_MODERATOR") ;
        springifyUser.setRoles(new HashSet<Role>(Arrays.asList(moderatorRole)));
        return addOne(springifyUser);
    }


}
