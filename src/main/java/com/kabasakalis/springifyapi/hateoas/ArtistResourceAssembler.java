package com.kabasakalis.springifyapi.hateoas;

import com.kabasakalis.springifyapi.models.Artist;
import com.kabasakalis.springifyapi.controllers.ArtistController;
import org.springframework.hateoas.SimpleIdentifiableResourceAssembler;
import org.springframework.stereotype.Component;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.Resource;

@Component
public class ArtistResourceAssembler extends SimpleIdentifiableResourceAssembler<Artist> {

	ArtistResourceAssembler() {
		super(ArtistController.class);
	}
	@Override
	protected void addLinks(Resource<Artist> resource) {

		resource.add(getCollectionLinkBuilder().slash(resource.getContent()).withSelfRel());
		resource.add(getCollectionLinkBuilder().slash(resource.getContent()).withSelfRel());
resource.add(getCollectionLinkBuilder().slash(resource.getContent()).withSelfRel());
		resource.add(getCollectionLinkBuilder().slash(resource.getContent()).withSelfRel());

		// resource.add(getCollectionLinkBuilder().withRel(this.relProvider.getCollectionResourceRelFor(this.resourceType)));
	}

}
