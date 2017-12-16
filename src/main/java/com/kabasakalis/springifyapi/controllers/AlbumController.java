
package  com.kabasakalis.springifyapi.controllers;

import com.kabasakalis.springifyapi.hateoas.AlbumResource;
import com.kabasakalis.springifyapi.hateoas.AlbumResourceAssembler;
import com.kabasakalis.springifyapi.models.Album;

import com.kabasakalis.springifyapi.services.SpringifyService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;




@RestController
@RequestMapping("/api/albums")
public class AlbumController extends CoreController{

  @Autowired
  private SpringifyService springifyService;

  @Autowired
  private  AlbumResourceAssembler assembler;

  @Autowired
  private SessionFactory sessionFactory;

  // @Autowired Validator artistValidator;

  // @InitBinder
  // protected void initBinder(WebDataBinder binder){
  //     binder.addValidators(artistValidator);
  // }

  // @GetMapping
  // public List<AlbumResource> index() {
  //     List<Album> res = springifyService.getAlbums();
  //     List<AlbumResource> output = new ArrayList<AlbumResource>();
  //     res.forEach((a)->{
  //         AlbumResource ar = new AlbumResource(a);
  //         ar.add(createHateoasLink(a.getId()));
  //
  //         output.add(ar);
  //     });
  //     return output;
  // }


  @GetMapping( produces = MediaTypes.HAL_JSON_VALUE)
    public Resources<Resource<Album>> index() {
      return assembler.toResources(springifyService.getAlbums());
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
