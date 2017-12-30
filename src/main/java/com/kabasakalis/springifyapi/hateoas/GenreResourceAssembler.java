package com.kabasakalis.springifyapi.hateoas;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.kabasakalis.springifyapi.models.BaseEntity;
import com.kabasakalis.springifyapi.models.Genre;
import com.kabasakalis.springifyapi.controllers.GenreController;
import org.springframework.hateoas.SimpleIdentifiableResourceAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.stereotype.Component;

import org.springframework.hateoas.Resources;
import org.springframework.hateoas.Resource;


@Component
public class GenreResourceAssembler extends SimpleIdentifiableResourceAssembler<Genre> {

    GenreResourceAssembler() {
        super(GenreController.class);
    }

    @Override
    protected void addLinks(Resource<Genre> resource) {

        resource.add(getCollectionLinkBuilder().slash(resource.getContent()).withSelfRel());
        resource.add(getCollectionLinkBuilder().slash(resource.getContent()).withSelfRel());
        resource.add(getCollectionLinkBuilder().slash(resource.getContent()).withSelfRel());
        // resource.add(getCollectionLinkBuilder().withRel(this.relProvider.getCollectionResourceRelFor(this.resourceType)));
    }

}
