package com.kabasakalis.springifyapi.hateoas;

import com.kabasakalis.springifyapi.controllers.*;
import com.kabasakalis.springifyapi.models.Role;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.SimpleIdentifiableResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;


@Component
public class RoleResourceAssembler extends SimpleIdentifiableResourceAssembler<Role> {

    RoleResourceAssembler() {
        super(RoleController.class);
    }

//    @Override
    protected void addLinks(Resource<Role> resource) {
        super.addLinks(resource);
        // optionally add more custom links here.
        PageRequest pageRequest = getPageRequest(0, 20, null);
        resource.add(
//                linkTo(methodOn(RoleController.class).getRoles(pageRequest, resource.getContent().getId())).withRel("roles"),
                linkTo(methodOn(CoreController.class).root(pageRequest)).withRel("home"));
    }

    /**
     * Define links to add to {@link Resources} collection.
     *
     * @param resources
     */
    @Override
    protected void addLinks(Resources<Resource<Role>> resources) {
        super.addLinks(resources);
    }


    /**
     * Add links to {@link PagedResources} collection.
     *
     * @param pagedResources
     */

    @Override
    public void addLinks(PagedResources<Resource<Role>> pagedResources) {
        super.addLinks(pagedResources);
        PageRequest pageRequest = getPageRequest(0, 20, null);
        pagedResources.add(
                linkTo(methodOn(CoreController.class).root(pageRequest)).withRel("home"));
    }

}
