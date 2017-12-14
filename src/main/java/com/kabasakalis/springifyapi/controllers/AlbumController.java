

package  com.kabasakalis.springifyapi.controllers;

import com.kabasakalis.springifyapi.hateoas.AlbumResource;
import com.kabasakalis.springifyapi.models.Album;
// import net.vatri.ecommerce.models.AlbumImage;

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
@RequestMapping("/albums")
public class AlbumController extends CoreController{

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
    public List<AlbumResource> index() {
        List<Album> res = springifyService.getAlbums();
        List<AlbumResource> output = new ArrayList<AlbumResource>();
        res.forEach((a)->{
            AlbumResource ar = new AlbumResource(a);
            ar.add(createHateoasLink(a.getId()));

            output.add(ar);
        });
        return output;
    }

    @PostMapping
    public Album create(@RequestBody @Valid Album album){
        return springifyService.saveAlbum(album);
    }

    @GetMapping("/{id}")
    public ResourceSupport view(@PathVariable("id") long id){
        Album p = springifyService.getAlbum(id);

        AlbumResource pr = new AlbumResource(p);
        pr.add(createHateoasLink(p.getId()));

        return pr;
    }

    @PostMapping(value = "/{id}")
    public Album edit(@PathVariable("id") long id, @RequestBody @Valid Album album){

        Album updatedAlbum = springifyService.getAlbum(id);

        if(updatedAlbum == null){
            return null;
        }

        updatedAlbum.setTitle(album.getTitle());
        updatedAlbum.setYear(album.getYear());
        updatedAlbum.setCreatedDate(album.getCreatedDate());
        updatedAlbum.setUpdatedDate(album.getUpdatedDate());

        return springifyService.saveAlbum(updatedAlbum);
    }


    // Todo: add delete method

}
