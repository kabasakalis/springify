
package com.kabasakalis.springifyapi.controllers;

import com.kabasakalis.springifyapi.hateoas.AlbumResourceAssembler;
import com.kabasakalis.springifyapi.hateoas.ArtistResourceAssembler;
import com.kabasakalis.springifyapi.models.Album;
import com.kabasakalis.springifyapi.models.Artist;
import com.kabasakalis.springifyapi.repositories.AlbumRepository;
import com.kabasakalis.springifyapi.repositories.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

// import com.kabasakalis.springifyapi.controllers.ArtistController;

@RepositoryRestController
@RequestMapping("/artists")
public class ArtistController extends AbstractBaseRestController<Artist> {

//    @Autowired
    private AlbumRepository albumRepository;

//    @Autowired
//    private ArtistResourceAssembler assembler;

//    @Autowired
    private AlbumResourceAssembler albumAssembler;

//    @Autowired
    private PagedResourcesAssembler<Album> pagedAlbumAssembler;

    @Autowired
    public ArtistController(ArtistRepository repository, ArtistResourceAssembler assembler) {
        super(repository, assembler);
    }

//    @Autowired
    @RequestMapping(
            method = RequestMethod.GET,
            path = "/{id}/albums",
            produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Page<Album>> getArtistAlbums(
            Pageable pageRequest,
            @PathVariable Long id) {
        PagedResources<Resource<Album>>
                pagedArtistAlbums = pagedAlbumAssembler.toResource(
                albumRepository.findAllByArtistId(id, pageRequest),
                albumAssembler);
        return new ResponseEntity(pagedArtistAlbums, HttpStatus.OK);
    }


}
