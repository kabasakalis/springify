package com.kabasakalis.springifyapi.hateoas;

import com.kabasakalis.springifyapi.controllers.*;
import com.kabasakalis.springifyapi.models.Album;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.*;
import org.springframework.stereotype.Component;

import org.springframework.util.Assert;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;


@Component
public class AlbumResourceAssembler extends SimpleIdentifiableResourceAssembler<Album> {

    AlbumResourceAssembler() {
        super(AlbumController.class);
    }


    /**
     * Define links to add to every {@link Resource}.
     *
     * @param resource
     */
//    @Override
    protected void addLinks(Resource<Album> resource) {
        super.addLinks(resource);
        // optionally add more custom links here.
        PageRequest pageRequest = getPageRequest(0, 20, null);
        resource.add(
                linkTo(methodOn(AlbumController.class).getPlaylists(pageRequest, resource.getContent().getId())).withRel("playlists"),
                linkTo(methodOn(AlbumController.class).getArtist(resource.getContent().getId())).withRel("artist"),
                linkTo(methodOn(CoreController.class).root(pageRequest)).withRel("home"));
    }

    /**
     * Define links to add to {@link Resources} collection.
     *
     * @param resources
     */
    @Override
    protected void addLinks(Resources<Resource<Album>> resources) {
        super.addLinks(resources);
    }


    /**
     * Add links to {@link PagedResources} collection.
     *
     * @param pagedResources
     */

    @Override
    public void addLinks(PagedResources<Resource<Album>> pagedResources) {
        super.addLinks(pagedResources);
        PageRequest pageRequest = getPageRequest(0, 20, null);
        pagedResources.add(
                linkTo(methodOn(ArtistController.class).getAll(pageRequest)).withRel("artists"),
                linkTo(methodOn(PlaylistController.class).getAll(pageRequest)).withRel("playlists"),
                linkTo(methodOn(CoreController.class).root(pageRequest)).withRel("home"));
    }


}
