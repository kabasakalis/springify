
package  com.kabasakalis.springifyapi.controllers;

import com.kabasakalis.springifyapi.hateoas.ArtistResource;
import com.kabasakalis.springifyapi.models.Artist;
// import net.vatri.ecommerce.models.ArtistImage;

import com.kabasakalis.springifyapi.services.SpringifyService;
// import net.vatri.ecommerce.storage.StorageService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
// import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/artists")
public class ArtistController extends CoreController{

    @Autowired
    private SpringifyService springifyService;
    //
    // @Autowired
    // private StorageService storageService;

    @Autowired
    private SessionFactory sessionFactory;

    // @Autowired Validator artistValidator;

    // @InitBinder
    // protected void initBinder(WebDataBinder binder){
    //     binder.addValidators(artistValidator);
    // }

    @GetMapping
    public List<ArtistResource> index() {
        List<Artist> res = springifyService.getArtists();
        List<ArtistResource> output = new ArrayList<ArtistResource>();
        res.forEach((a)->{
            ArtistResource ar = new ArtistResource(a);
            ar.add(createHateoasLink(a.getId()));

            output.add(ar);
        });
        return output;
    }

    @PostMapping
    public Artist create(@RequestBody @Valid Artist artist){
        return springifyService.saveArtist(artist);
    }

    @GetMapping("/{id}")
    public ResourceSupport view(@PathVariable("id") long id){
        Artist p = springifyService.getArtist(id);

        ArtistResource pr = new ArtistResource(p);
        pr.add(createHateoasLink(p.getId()));

        return pr;
    }

    @PostMapping(value = "/{id}")
    public Artist edit(@PathVariable("id") long id, @RequestBody @Valid Artist artist){

        Artist updatedArtist = springifyService.getArtist(id);

        if(updatedArtist == null){
            return null;
        }

        updatedArtist.setName(artist.getName());
        updatedArtist.setCountry(artist.getCountry());
        updatedArtist.setCreatedDate(artist.getCreatedDate());

        return springifyService.saveArtist(updatedArtist);
    }


    // Todo: add delete method

}
