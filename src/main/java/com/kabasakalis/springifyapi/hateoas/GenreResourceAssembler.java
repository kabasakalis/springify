package com.kabasakalis.springifyapi.hateoas;

import com.kabasakalis.springifyapi.controllers.ArtistController;
import com.kabasakalis.springifyapi.controllers.GenreController;
import com.kabasakalis.springifyapi.controllers.PlaylistController;
import com.kabasakalis.springifyapi.models.Genre;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.SimpleIdentifiableResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;


@Component
public class GenreResourceAssembler extends SimpleIdentifiableResourceAssembler<Genre> {

    GenreResourceAssembler() {
        super(GenreController.class);
    }

    @Override
    protected void addLinks(Resource<Genre> resource) {
        super.addLinks(resource);
        // optionally add more custom links here.
        PageRequest pageRequest = getPageRequest(0, 20, null);
        resource.add(
                linkTo(methodOn(GenreController.class).getArtists(pageRequest, resource.getContent().getId())).withRel("artists") );
    }

    /**
     * Define links to add to {@link Resources} collection.
     *
     * @param resources
     */
    @Override
    protected void addLinks(Resources<Resource<Genre>> resources) {
        super.addLinks(resources);
    }


    /**
     * Add links to {@link PagedResources} collection.
     *
     * @param pagedResources
     */

    @Override
    public void addLinks(PagedResources<Resource<Genre>> pagedResources) {
        super.addLinks(pagedResources);
        PageRequest pageRequest = getPageRequest(0, 20, null);
        pagedResources.add(
                linkTo(methodOn(ArtistController.class).getAll(pageRequest)).withRel("artists"),
                linkTo(methodOn(PlaylistController.class).getAll(pageRequest)).withRel("playlists"));
    }

}
