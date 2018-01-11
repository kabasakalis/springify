package com.kabasakalis.springifyapi.assemblers;

import com.kabasakalis.springifyapi.controllers.AlbumController;
import com.kabasakalis.springifyapi.controllers.ArtistController;
import com.kabasakalis.springifyapi.controllers.CoreController;
import com.kabasakalis.springifyapi.controllers.PlaylistController;
import com.kabasakalis.springifyapi.domain.Album;
import com.kabasakalis.springifyapi.serializers.AlbumResource;
import com.kabasakalis.springifyapi.serializers.BaseResourceSupport;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.SimpleIdentifiableResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;


@Component
public class AlbumResourceAssembler extends SimpleIdentifiableResourceAssembler<Album> {

    AlbumResourceAssembler() { super(AlbumController.class, AlbumResource.class); }

    @Override
    public void addLinks(BaseResourceSupport resource) {
        super.addLinks(resource);
        // optionally add more custom links here, usually associatiated resources
        PageRequest pageRequest = getPageRequest(0, 20, null);
        resource.add(
                linkTo(methodOn(AlbumController.class).getPlaylists(pageRequest, resource.getEntity().getId())).withRel("playlists"),
                linkTo(methodOn(AlbumController.class).getArtist(resource.getEntity().getId())).withRel("artist"));
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
