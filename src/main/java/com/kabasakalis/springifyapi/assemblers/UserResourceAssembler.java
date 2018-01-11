package com.kabasakalis.springifyapi.assemblers;

import com.kabasakalis.springifyapi.controllers.CoreController;
import com.kabasakalis.springifyapi.controllers.UserController;
import com.kabasakalis.springifyapi.domain.SpringifyUser;
import com.kabasakalis.springifyapi.serializers.BaseResourceSupport;
import com.kabasakalis.springifyapi.serializers.UserResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.SimpleIdentifiableResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;


@Component
public class UserResourceAssembler extends SimpleIdentifiableResourceAssembler<SpringifyUser> {

    UserResourceAssembler() {
        super(UserController.class, UserResource.class);
    }

    @Override
        public void addLinks(BaseResourceSupport resource) {
        super.addLinks(resource);
        // optionally add more custom links here, usually associatiated resources
        PageRequest pageRequest = getPageRequest(0, 20, null);
        resource.add(
                linkTo(methodOn(UserController.class).getRoles(pageRequest, resource.getEntity().getId())).withRel("roles"));
    }


    @Override
        public void addLinks(PagedResources<ResourceSupport> pagedResources) {
        super.addLinks(pagedResources);
        PageRequest pageRequest = getPageRequest(0, 20, null);
        pagedResources.add(
                linkTo(methodOn(CoreController.class).root(pageRequest)).withRel("home"));
    }

}
