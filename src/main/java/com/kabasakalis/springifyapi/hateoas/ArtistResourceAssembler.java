package com.kabasakalis.springifyapi.hateoas;

import com.kabasakalis.springifyapi.models.Artist;
import com.kabasakalis.springifyapi.controllers.ArtistController;
import org.springframework.hateoas.SimpleIdentifiableResourceAssembler;
import org.springframework.stereotype.Component;

@Component
public class ArtistResourceAssembler extends SimpleIdentifiableResourceAssembler<Artist> {

	ArtistResourceAssembler() {
		super(ArtistController.class);
	}
}
