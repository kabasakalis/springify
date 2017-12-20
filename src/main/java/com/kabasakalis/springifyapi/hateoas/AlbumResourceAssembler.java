package com.kabasakalis.springifyapi.hateoas;

import com.kabasakalis.springifyapi.models.Album;
import com.kabasakalis.springifyapi.controllers.AlbumController;
import com.kabasakalis.springifyapi.controllers.ArtistController;
import org.springframework.hateoas.SimpleIdentifiableResourceAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.stereotype.Component;

import org.springframework.hateoas.Resources;
import org.springframework.hateoas.Resource;


@Component
public class AlbumResourceAssembler extends SimpleIdentifiableResourceAssembler<Album> {

	AlbumResourceAssembler() {
		super(AlbumController.class);
	}


	/**
	 * Define links to add to every {@link Resource}.
	 *
	 * @param resource
	 */
	@Override
	protected void addLinks(Resource<Album> resource) {

		// resource.add(getCollectionLinkBuilder().slash(resource.getContent()).withSelfRel());
		// resource.add(getCollectionLinkBuilder().slash(resource.getContent()).withSelfRel());
		// resource.add(getCollectionLinkBuilder().withRel(this.relProvider.getCollectionResourceRelFor(this.resourceType)));
	}

	/**
	 * Define links to add to {@link Resources} collection.
	 *
	 * @param resources
	 */
	@Override
	protected void addLinks(Resources<Resource<Album>> resources) {
		resources.add(getCollectionLinkBuilder().withSelfRel());
	}




}
