
package com.kabasakalis.springifyapi.controllers;

import com.kabasakalis.springifyapi.hateoas.PlaylistResource;
import com.kabasakalis.springifyapi.hateoas.PlaylistResourceAssembler;
import com.kabasakalis.springifyapi.models.BaseEntity;
import com.kabasakalis.springifyapi.models.Playlist;
import com.kabasakalis.springifyapi.repositories.AlbumRepository;
import com.kabasakalis.springifyapi.repositories.PlaylistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.web.HateoasPageableHandlerMethodArgumentResolver;


@RepositoryRestController
@RequestMapping("/playlists")
public class PlaylistController extends AbstractBaseRestController<Playlist> {


    @Autowired
    private PlaylistResourceAssembler assembler;
    private AlbumRepository albumRepository;


    @Autowired
    public PlaylistController(PlaylistRepository repository,
                              AlbumRepository albumRepository,
                              ApplicationContext appContext,
                              PlaylistResourceAssembler assembler) {
        super(repository, appContext, assembler);
        this.albumRepository = albumRepository;
    }


    @RequestMapping(
            method = {RequestMethod.PATCH, RequestMethod.PUT, RequestMethod.POST},
            path = "/{id}/albums",
            consumes = {"application/json", "text/uri-list"},
            produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<? extends ResourceSupport> addPlaylistAlbums(
            @PathVariable long id,
            @RequestBody(required = false) Resources<? extends BaseEntity> albumLinks) {
        return associateResources(Association.MANY_TO_MANY, albumRepository, id, albumLinks);

    }


    @RequestMapping(
            method = RequestMethod.DELETE,
            path = "/{id}/albums/{albumId}",
            consumes = {"application/json"},
            produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity deleteAlbum(
            @PathVariable Long id, @PathVariable Long albumId) {
        return deleteAssociation(
                Association.MANY_TO_MANY,
                albumRepository,
                id,
                albumId
        );
    }


}
