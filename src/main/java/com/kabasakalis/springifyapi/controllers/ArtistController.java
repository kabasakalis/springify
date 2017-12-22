
package  com.kabasakalis.springifyapi.controllers;

import com.kabasakalis.springifyapi.hateoas.ArtistResource;
import com.kabasakalis.springifyapi.hateoas.AlbumResource;
import com.kabasakalis.springifyapi.hateoas.ArtistResourceAssembler;
import com.kabasakalis.springifyapi.hateoas.AlbumResourceAssembler;
import com.kabasakalis.springifyapi.models.Artist;
import com.kabasakalis.springifyapi.models.Album;
// import com.kabasakalis.springifyapi.controllers.ArtistController;
import com.kabasakalis.springifyapi.repositories.ArtistRepository;
import com.kabasakalis.springifyapi.repositories.AlbumRepository;
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
@RequestMapping("/artists")
public class ArtistController extends AbstractBaseRestController<Artist> {


@Autowired
private ArtistResourceAssembler assembler;

    @Autowired
    public ArtistController(ArtistRepository repository, ArtistResourceAssembler assembler ) {
        super(repository, assembler);
    }
}
