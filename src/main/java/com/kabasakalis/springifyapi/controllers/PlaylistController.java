
package  com.kabasakalis.springifyapi.controllers;

import com.kabasakalis.springifyapi.hateoas.PlaylistResource;
import com.kabasakalis.springifyapi.hateoas.PlaylistResourceAssembler;
import com.kabasakalis.springifyapi.models.Playlist;
import com.kabasakalis.springifyapi.repositories.PlaylistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.*;
import org.springframework.web.bind.annotation.*;
import  org.springframework.data.web.HateoasPageableHandlerMethodArgumentResolver;


@RepositoryRestController
@RequestMapping("/playlists")
public class PlaylistController extends AbstractBaseRestController<Playlist> {


@Autowired
private PlaylistResourceAssembler assembler;

    @Autowired
    public PlaylistController(PlaylistRepository repository, PlaylistResourceAssembler assembler ) {
        super(repository, assembler);
    }
}
