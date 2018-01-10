package com.kabasakalis.springifyapi.hateoas;

import com.kabasakalis.springifyapi.controllers.CoreController;
import com.kabasakalis.springifyapi.controllers.UserController;
import com.kabasakalis.springifyapi.models.SpringifyUser;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.*;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;


@Component
public class UserResourceAssembler extends SimpleIdentifiableResourceAssembler<SpringifyUser> {

    UserResourceAssembler() {
        super(UserController.class, UserResource.class);
    }

    @Override
//    protected void addLinks(Resource<SpringifyUser> resource) {
        protected void addLinks(BaseResourceSupport resource) {
        super.addLinks(resource);
        // optionally add more custom links here.
        PageRequest pageRequest = getPageRequest(0, 20, null);
        resource.add(
                linkTo(methodOn(UserController.class).getRoles(pageRequest, resource.getEntity().getId())).withRel("roles"),
                linkTo(methodOn(CoreController.class).root(pageRequest)).withRel("home"));
    }


    @Override
    protected void addLinks(Resources<Resource<SpringifyUser>> resources) {
        super.addLinks(resources);
    }

    @Override
    public void addLinks(PagedResources<Resource<SpringifyUser>> pagedResources) {
        super.addLinks(pagedResources);
        PageRequest pageRequest = getPageRequest(0, 20, null);
        pagedResources.add(
                linkTo(methodOn(CoreController.class).root(pageRequest)).withRel("home"));
    }

}
