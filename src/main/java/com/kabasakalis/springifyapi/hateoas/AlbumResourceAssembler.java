package com.kabasakalis.springifyapi.hateoas;

import com.kabasakalis.springifyapi.models.Album;
import com.kabasakalis.springifyapi.controllers.AlbumController;
import org.springframework.hateoas.SimpleIdentifiableResourceAssembler;
import org.springframework.stereotype.Component;

@Component
public class AlbumResourceAssembler extends SimpleIdentifiableResourceAssembler<Album> {

	AlbumResourceAssembler() {
		super(AlbumController.class);
	}
}
