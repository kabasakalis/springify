package com.kabasakalis.springifyapi.controllers;

import org.springframework.hateoas.Link;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;


import org.springframework.web.bind.annotation.RestController;

// @RestController("/api")
public class CoreController {
    protected Link createHateoasLink(long id){
        Link link = linkTo(getClass()).slash(id).withSelfRel();
        return link;
    }
}
