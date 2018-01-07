package com.kabasakalis.springifyapi.controllers;

import com.kabasakalis.springifyapi.hateoas.UserResourceAssembler;
import com.kabasakalis.springifyapi.models.User;
import com.kabasakalis.springifyapi.repositories.ArtistRepository;
import com.kabasakalis.springifyapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.web.HateoasPageableHandlerMethodArgumentResolver;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RepositoryRestController
@RequestMapping("/users")
public class UserController extends AbstractBaseRestController<User> {

    private UserRepository applicationUserRepository;
    private UserResourceAssembler assembler;
    private PagedResourcesAssembler<User> pagedUserAssembler;
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Autowired
    public UserController(UserRepository repository,
                          UserResourceAssembler assembler,
                          BCryptPasswordEncoder bCryptPasswordEncoder,
                          ApplicationContext appContext) {
        super(repository, appContext, null);
        HateoasPageableHandlerMethodArgumentResolver resolver = new HateoasPageableHandlerMethodArgumentResolver();
        this.pagedUserAssembler = new PagedResourcesAssembler<>(resolver, null);
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.assembler = assembler;
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
    public ResponseEntity<Void> signup(@RequestBody User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return addOne(user);
    }


}
