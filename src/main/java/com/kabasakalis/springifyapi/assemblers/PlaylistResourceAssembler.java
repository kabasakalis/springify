package com.kabasakalis.springifyapi.assemblers;

import com.kabasakalis.springifyapi.controllers.*;
import com.kabasakalis.springifyapi.domain.Playlist;
import com.kabasakalis.springifyapi.serializers.BaseResourceSupport;
import com.kabasakalis.springifyapi.serializers.PlaylistResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.SimpleIdentifiableResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;


@Component
public class PlaylistResourceAssembler extends SimpleIdentifiableResourceAssembler<Playlist> {

    PlaylistResourceAssembler() { super(PlaylistController.class, PlaylistResource.class); }

    @Override
    public void addLinks(BaseResourceSupport resource) {
        super.addLinks(resource);
        // optionally add more custom links here, usually associatiated resources
        PageRequest pageRequest = getPageRequest(0, 20, null);
        resource.add(
                linkTo(methodOn(PlaylistController.class).getAlbums(pageRequest, resource.getEntity().getId())).withRel("albums"));
    }

    @Override
    public void addLinks(PagedResources<ResourceSupport> pagedResources) {
        super.addLinks(pagedResources);
        PageRequest pageRequest = getPageRequest(0, 20, null);
        pagedResources.add(
                linkTo(methodOn(ArtistController.class).getAll(pageRequest)).withRel("artists"),
                linkTo(methodOn(GenreController.class).getAll(pageRequest)).withRel("genres"),
                linkTo(methodOn(AlbumController.class).getAll(pageRequest)).withRel("albums"),
                linkTo(methodOn(CoreController.class).root(pageRequest)).withRel("home"));
    }

}
