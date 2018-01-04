package com.kabasakalis.springifyapi.hateoas;

import com.kabasakalis.springifyapi.controllers.*;
import com.kabasakalis.springifyapi.models.Playlist;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.SimpleIdentifiableResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;


@Component
public class PlaylistResourceAssembler extends SimpleIdentifiableResourceAssembler<Playlist> {

    PlaylistResourceAssembler() {
        super(PlaylistController.class);
    }

    @Override
    protected void addLinks(Resource<Playlist> resource) {
        super.addLinks(resource);
        // optionally add more custom links here.
        PageRequest pageRequest = getPageRequest(0, 20, null);
        resource.add(
                linkTo(methodOn(PlaylistController.class).getAlbums(pageRequest, resource.getContent().getId())).withRel("albums"),
                linkTo(methodOn(CoreController.class).root(pageRequest)).withRel("home"));
    }


    /**
     * Define links to add to {@link Resources} collection.
     *
     * @param resources
     */
    @Override
    protected void addLinks(Resources<Resource<Playlist>> resources) {
        super.addLinks(resources);
    }


    /**
     * Add links to {@link PagedResources} collection.
     *
     * @param pagedResources
     */

    @Override
    public void addLinks(PagedResources<Resource<Playlist>> pagedResources) {
        super.addLinks(pagedResources);
        PageRequest pageRequest = getPageRequest(0, 20, null);
        pagedResources.add(
                linkTo(methodOn(ArtistController.class).getAll(pageRequest)).withRel("artists"),
                linkTo(methodOn(GenreController.class).getAll(pageRequest)).withRel("genres"),
                linkTo(methodOn(AlbumController.class).getAll(pageRequest)).withRel("albums"),
                linkTo(methodOn(CoreController.class).root(pageRequest)).withRel("home"));
    }


}
