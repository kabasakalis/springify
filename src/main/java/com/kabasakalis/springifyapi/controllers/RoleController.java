package com.kabasakalis.springifyapi.controllers;

import com.kabasakalis.springifyapi.assemblers.PagedCustomResourcesAssembler;
import com.kabasakalis.springifyapi.assemblers.RoleResourceAssembler;
import com.kabasakalis.springifyapi.assemblers.UserResourceAssembler;
import com.kabasakalis.springifyapi.domain.Role;
import com.kabasakalis.springifyapi.domain.SpringifyUser;
import com.kabasakalis.springifyapi.repositories.RoleRepository;
import com.kabasakalis.springifyapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.web.HateoasPageableHandlerMethodArgumentResolver;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RepositoryRestController
@RequestMapping("/roles")
public class RoleController extends AbstractBaseRestController<Role> {

    private RoleRepository repository;
    private UserRepository userRepository;
   private UserResourceAssembler userResourceAssembler;
    private PagedCustomResourcesAssembler<SpringifyUser> pagedUserAssembler;
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
        this.pagedUserAssembler = new PagedCustomResourcesAssembler<SpringifyUser>(resolver, null);
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userResourceAssembler = userResourceAssembler;
        this.userRepository = userRepository;
    }


    @RequestMapping(
            method = RequestMethod.GET,
            path = "/{id}/users",
            produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity getRoleUsers(
            Pageable pageRequest,
            @PathVariable Long id) {
        Page<SpringifyUser> pagedRoleUsers = userRepository.findAllByRoles(repository.getOne(id), pageRequest);
        return getAssociatedResources(userResourceAssembler, pagedUserAssembler, pagedRoleUsers, pageRequest);
    }


}
