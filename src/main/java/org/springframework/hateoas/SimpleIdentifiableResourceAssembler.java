package org.springframework.hateoas;

import com.kabasakalis.springifyapi.serializers.BaseResourceSupport;
import org.springframework.core.GenericTypeResolver;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.core.EvoInflectorRelProvider;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.util.Assert;

import java.lang.reflect.Constructor;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class SimpleIdentifiableResourceAssembler<T extends Identifiable<?>> extends SimpleResourceAssembler<T> {

    protected final Class<?> controllerClass;
    private final RelProvider relProvider;
    private Class<?> resourceType;
    private Class<? extends ResourceSupport> resourceSupportClass;
    private String basePath = "";

    public SimpleIdentifiableResourceAssembler(Class<?> controllerClass, RelProvider relProvider, Class<? extends ResourceSupport> resourceSupportClass) {

        this.controllerClass = controllerClass;
        this.relProvider = relProvider;
        this.resourceSupportClass = resourceSupportClass;
        // Find the "T" type contained in "T extends Identifiable<?>", e.g. SimpleIdentifiableResourceAssembler<SpringifyUser> -> SpringifyUser
        this.resourceType = GenericTypeResolver.resolveTypeArgument(this.getClass(), SimpleIdentifiableResourceAssembler.class);
    }

    public SimpleIdentifiableResourceAssembler(Class<?> controllerClass, Class<? extends ResourceSupport> resourceSupportClass) {
        this(controllerClass, new EvoInflectorRelProvider(), resourceSupportClass);
    }

    public SimpleIdentifiableResourceAssembler(Class<?> controllerClass) {
        this(controllerClass, new EvoInflectorRelProvider(), null);
    }


    public ResourceSupport toCustomResource(T entity) {
        try {
            Constructor<?> ctor = Class.forName(resourceSupportClass.getName()).getConstructor(resourceType);
            BaseResourceSupport resource = (BaseResourceSupport) ctor.newInstance(new Object[]{entity});
            addLinks(resource);
            return resource;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void addLinks(BaseResourceSupport resource) {
        Assert.notNull(resource, "Resource must not be null!");
        resource.add(getCollectionLinkBuilder().slash(resource.getEntity()).withSelfRel());
        resource.add(getCollectionLinkBuilder().withRel(this.relProvider.getCollectionResourceRelFor(this.resourceType)));
    }

    public void addLinks(PagedResources<ResourceSupport> pagedResources) {
        Assert.notNull(pagedResources, "PagedResources must not be null!");
    }


    protected LinkBuilder getCollectionLinkBuilder() {

        ControllerLinkBuilder linkBuilder = linkTo(this.controllerClass);
        for (String pathComponent : (getPrefix()).split("/")) {
            if (!pathComponent.isEmpty()) {
                linkBuilder = linkBuilder.slash(pathComponent);
            }
        }
        return linkBuilder;
    }


    private String getPrefix() {
        return getBasePath().isEmpty() ? "" : getBasePath() + "/";
    }

    public String getBasePath() {
        return this.basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public PageRequest getPageRequest(int page, int size, Sort sort) {
        return new PageRequest(page, size, sort);
    }
}
