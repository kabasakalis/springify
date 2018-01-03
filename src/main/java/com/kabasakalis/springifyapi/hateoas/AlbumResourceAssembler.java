package com.kabasakalis.springifyapi.hateoas;

import com.kabasakalis.springifyapi.controllers.GenreController;
import com.kabasakalis.springifyapi.controllers.PlaylistController;
import com.kabasakalis.springifyapi.models.Album;
import com.kabasakalis.springifyapi.controllers.AlbumController;
import org.springframework.data.domain.PageImpl;
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
    @Override
    protected void addLinks(Resource<Album> resource) {
        super.addLinks(resource);
        // optionally add more custom links here.
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
    public void addLinks(PagedResources<Resource<Album>> pagedResources, Pageable pageRequest) {


	  Link link = linkTo(methodOn(PlaylistController.class).getAll(pageRequest)).withRel("playlists");
        Link link2 = linkTo(methodOn(GenreController.class).getAll(pageRequest)).withRel("genres");

		pagedResources.add(link);
        pagedResources.add(link2);

		super.addLinks(pagedResources, pageRequest);
    }


}
