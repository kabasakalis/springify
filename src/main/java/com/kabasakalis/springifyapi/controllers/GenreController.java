

package  com.kabasakalis.springifyapi.controllers;

import com.kabasakalis.springifyapi.hateoas.GenreResource;
import com.kabasakalis.springifyapi.hateoas.GenreResourceAssembler;
import com.kabasakalis.springifyapi.models.Genre;
import com.kabasakalis.springifyapi.repositories.GenreRepository;
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
@RequestMapping("/genres")
public class GenreController extends AbstractBaseRestController<Genre> {


@Autowired
private GenreResourceAssembler assembler;

    @Autowired
    public GenreController(GenreRepository repository, GenreResourceAssembler assembler ) {
        super(repository, assembler);
    }
}
