
package com.kabasakalis.springifyapi.controllers;


import com.kabasakalis.springifyapi.hateoas.AlbumResourceAssembler;
import com.kabasakalis.springifyapi.hateoas.PlaylistResourceAssembler;
import com.kabasakalis.springifyapi.models.Album;
import com.kabasakalis.springifyapi.models.BaseEntity;
import com.kabasakalis.springifyapi.models.Playlist;
import com.kabasakalis.springifyapi.repositories.AlbumRepository;
import com.kabasakalis.springifyapi.repositories.PlaylistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.web.HateoasPageableHandlerMethodArgumentResolver;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RepositoryRestController
@RequestMapping("/playlists")
public class PlaylistController extends AbstractBaseRestController<Playlist> {

    private PlaylistResourceAssembler assembler;
    private AlbumRepository albumRepository;
    private PagedResourcesAssembler<Album> pagedAlbumAssembler;
    private AlbumResourceAssembler albumResourceAssembler;

    @Autowired
    public PlaylistController(PlaylistRepository repository,
                              AlbumRepository albumRepository,
                              AlbumResourceAssembler albumResourceAssembler,
                              ApplicationContext appContext,
                              PlaylistResourceAssembler assembler) {
        super(repository, appContext, assembler);
        this.albumRepository = albumRepository;
        this.albumResourceAssembler = albumResourceAssembler;
        HateoasPageableHandlerMethodArgumentResolver resolver = new HateoasPageableHandlerMethodArgumentResolver();
        this.pagedAlbumAssembler = new PagedResourcesAssembler<Album>(resolver, null);
    }


    @RequestMapping(
            method = RequestMethod.GET,
            path = "/{id}/albums",
            consumes = {"application/json"},
            produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity getPlaylistAlbums(
            Pageable pageRequest,
            @PathVariable Long id) {
        Page<Album> pagedPlaylistAlbums = albumRepository.findAllByPlaylists(repository.getOne(id), pageRequest);
        return getAssociatedResources(albumResourceAssembler, pagedAlbumAssembler, pagedPlaylistAlbums, pageRequest);
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
                Association.MANY_TO_MANY, albumRepository, id, albumId);
    }


}
