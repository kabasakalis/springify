package com.kabasakalis.springifyapi.assemblers;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.HateoasPageableHandlerMethodArgumentResolver;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.*;
import org.springframework.util.Assert;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.web.util.UriComponentsBuilder.fromUri;

public class PagedCustomResourcesAssembler<T extends Identifiable<?>> extends PagedResourcesAssembler<T> {


    private final HateoasPageableHandlerMethodArgumentResolver pageableResolver;

    private boolean forceFirstAndLastRels = false;
    private final UriComponents baseUri;

    public PagedCustomResourcesAssembler(HateoasPageableHandlerMethodArgumentResolver resolver, UriComponents baseUri) {
        super(resolver, baseUri);
        this.pageableResolver = resolver;
          this.baseUri = baseUri;
     }


    public  PagedResources<ResourceSupport> toResource(Page<T> page, SimpleIdentifiableResourceAssembler<T> assembler) {
        return createCustomResource(page, assembler, null);
    }


    private  PagedResources<ResourceSupport>
    createCustomResource(Page<T> page,
                         SimpleIdentifiableResourceAssembler<T> assembler, Link link) {

        Assert.notNull(page, "Page must not be null!");
        Assert.notNull(assembler, "ResourceAssembler must not be null!");

        List<ResourceSupport> resources = new ArrayList<>(page.getNumberOfElements());

        for (T element : page) {
            resources.add(assembler.toCustomResource(element));
        }

        PagedResources<ResourceSupport> resource = createPagedResource(resources, asPageMetadata(page), page);

        return addPaginationLinks(resource, page, link);
    }


    private  PagedResources<ResourceSupport> addPaginationLinks(PagedResources<ResourceSupport> resources, Page<?> page, Link link) {

        UriTemplate base = getUriTemplate(link);

        boolean isNavigable = page.hasPrevious() || page.hasNext();

        if (isNavigable || forceFirstAndLastRels) {
            resources.add(createLink(base, new PageRequest(0, page.getSize(), page.getSort()), Link.REL_FIRST));
        }

        if (page.hasPrevious()) {
            resources.add(createLink(base, page.previousPageable(), Link.REL_PREVIOUS));
        }

        Pageable current = new PageRequest(page.getNumber(), page.getSize(), page.getSort());

        resources.add(link == null ? createLink(base, current, Link.REL_SELF) : link.withSelfRel());

        if (page.hasNext()) {
            resources.add(createLink(base, page.nextPageable(), Link.REL_NEXT));
        }

        if (isNavigable || forceFirstAndLastRels) {

            int lastIndex = page.getTotalPages() == 0 ? 0 : page.getTotalPages() - 1;

            resources.add(createLink(base, new PageRequest(lastIndex, page.getSize(), page.getSort()), Link.REL_LAST));
        }

        return resources;
    }

    private UriTemplate getUriTemplate(Link baseLink) {

        String href = baseLink != null ? baseLink.getHref()
                : baseUri == null ? ServletUriComponentsBuilder.fromCurrentRequest().build().toString() : baseUri.toString();

        return new UriTemplate(href);
    }

    private Link createLink(UriTemplate base, Pageable pageable, String rel) {

        UriComponentsBuilder builder = fromUri(base.expand());
        pageableResolver.enhance(builder, getMethodParameter(), pageable);

        return new Link(new UriTemplate(builder.build().toString()), rel);
    }


    private static <T> PagedResources.PageMetadata asPageMetadata(Page<T> page) {

        Assert.notNull(page, "Page must not be null!");
        return new PagedResources.PageMetadata(page.getSize(), page.getNumber(), page.getTotalElements(), page.getTotalPages());
    }
}
