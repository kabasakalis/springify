package com.kabasakalis.springifyapi.assemblers;

import com.kabasakalis.springifyapi.controllers.ArtistController;
import com.kabasakalis.springifyapi.controllers.CoreController;
import com.kabasakalis.springifyapi.controllers.GenreController;
import com.kabasakalis.springifyapi.controllers.PlaylistController;
import com.kabasakalis.springifyapi.domain.Genre;
import com.kabasakalis.springifyapi.serializers.BaseResourceSupport;
import com.kabasakalis.springifyapi.serializers.GenreResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.SimpleIdentifiableResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;


@Component
public class GenreResourceAssembler extends SimpleIdentifiableResourceAssembler<Genre> {

    GenreResourceAssembler() { super(GenreController.class, GenreResource.class);}

    @Override
    public void addLinks(BaseResourceSupport resource) {
        super.addLinks(resource);
        // optionally add more custom links here, usually associatiated resources
        PageRequest pageRequest = getPageRequest(0, 20, null);
        resource.add(
                linkTo(methodOn(GenreController.class).getArtists(pageRequest, resource.getEntity().getId())).withRel("artists"));
    }


    @Override
    public void addLinks(PagedResources<ResourceSupport> pagedResources) {
        super.addLinks(pagedResources);
        PageRequest pageRequest = getPageRequest(0, 20, null);
        pagedResources.add(
                linkTo(methodOn(ArtistController.class).getAll(pageRequest)).withRel("artists"),
                linkTo(methodOn(PlaylistController.class).getAll(pageRequest)).withRel("playlists"),
                linkTo(methodOn(CoreController.class).root(pageRequest)).withRel("home"));
    }

}
