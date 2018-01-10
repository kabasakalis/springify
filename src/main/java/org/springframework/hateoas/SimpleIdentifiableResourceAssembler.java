/*
 * Copyright 2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.hateoas;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.kabasakalis.springifyapi.hateoas.BaseResourceSupport;
import com.kabasakalis.springifyapi.models.BaseEntity;
import org.springframework.core.GenericTypeResolver;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.*;
import org.springframework.hateoas.core.EvoInflectorRelProvider;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.util.Assert;
import org.springframework.web.util.UriComponentsBuilder;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Greg Turnquist
 */
public class SimpleIdentifiableResourceAssembler<T extends Identifiable<?>> extends SimpleResourceAssembler<T> {

    /**
     * The Spring MVC class for the {@link Identifiable} from which links will be built.
     */
    protected final Class<?> controllerClass;

    /**
     * A {@link RelProvider} to look up names of links as options for resource paths.
     */
    private final RelProvider relProvider;

    /**
     * A {@link Class} depicting the {@link Identifiable}'s type.
     */
    private Class<?> resourceType;


    private Class<? extends ResourceSupport> resourceSupportClass;


    /**
     * Default base path as empty.
     */
    private String basePath = "";

    /**
     * Default a assembler based on Spring MVC controller, resource type, and {@link RelProvider}. With this combination
     * of information, resources can be defined.
     *
     * @param controllerClass - Spring MVC controller to base links off of
     * @param relProvider
     * @see #setBasePath(String) to adjust base path to something like "/api"/
     */
    public SimpleIdentifiableResourceAssembler(Class<?> controllerClass, RelProvider relProvider, Class<? extends ResourceSupport> resourceSupportClass) {

        this.controllerClass = controllerClass;
        this.relProvider = relProvider;
        this.resourceSupportClass = resourceSupportClass;

        // Find the "T" type contained in "T extends Identifiable<?>", e.g. SimpleIdentifiableResourceAssembler<SpringifyUser> -> SpringifyUser
        this.resourceType = GenericTypeResolver.resolveTypeArgument(this.getClass(), SimpleIdentifiableResourceAssembler.class);
    }

    /**
     * Alternate constructor that falls back to {@link EvoInflectorRelProvider}.
     *
     * @param controllerClass
     */
    public SimpleIdentifiableResourceAssembler(Class<?> controllerClass, Class<? extends ResourceSupport> resourceSupportClass) {
        this(controllerClass, new EvoInflectorRelProvider(), resourceSupportClass);
    }

    public SimpleIdentifiableResourceAssembler(Class<?> controllerClass) {
        this(controllerClass, new EvoInflectorRelProvider(), null);
    }


    /**
     * Define links to add to every {@link Resource}.
     *
     * @param resource
     */
    @Override
    protected void addLinks(BaseResourceSupport resource) {

        resource.add(getCollectionLinkBuilder().slash(resource.getEntity()).withSelfRel());
        resource.add(getCollectionLinkBuilder().withRel(this.relProvider.getCollectionResourceRelFor(this.resourceType)));
    }

    /**
     * Define links to add to {@link Resources} collection.
     *
     * @param resources
     */
    @Override
    protected void addLinks(Resources<Resource<T>> resources) {
        resources.add(getCollectionLinkBuilder().withSelfRel());
    }

    /**
     * Build up a URI for the collection using the Spring MVC controller followed by the resource type transformed
     * by the {@link RelProvider}.
     * <p>
     * Assumption is that an {@link org.springframework.hateoas.examples.ArtistController} serving up {@link org.springframework.hateoas.examples.Employee}
     * objects will be serving resources at {@code /employees} and {@code /employees/1}.
     * <p>
     * If this is not the case, simply override this method in your concrete instance, or simply resort to
     * overriding {@link #addLinks(Resource)} and {@link #addLinks(Resources)} where you have full control over exactly
     * what links are put in the individual and collection resources.
     *
     * @return
     */
    protected LinkBuilder getCollectionLinkBuilder() {

        ControllerLinkBuilder linkBuilder = linkTo(this.controllerClass);

        for (String pathComponent : (getPrefix()).split("/")) {
            if (!pathComponent.isEmpty()) {
                linkBuilder = linkBuilder.slash(pathComponent);
            }
        }

        return linkBuilder;
    }


    //    public ResourceSupport toCustomResource(T entity) {
    public ResourceSupport toCustomResource(T entity) {

//        Class<?> claz = Class.forName(className
        try {
//            Constructor<?> ctor = Class.forName(resourceSupportClass.getSimpleName()).getConstructor(resourceType.getClass());
            Constructor<?> ctor = Class.forName(resourceSupportClass.getName()).getConstructor(resourceType);
            BaseResourceSupport resource = (BaseResourceSupport) ctor.newInstance(new Object[]{entity});
            addLinks( resource);
            return resource;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }



//        return new ResourceSupport();

    }



     public ResourceSupport toCustomResources(Iterable<? extends T> entities) {

        Assert.notNull(entities, "Entities must not be null!");
        List<Resource<T>> result = new ArrayList<Resource<T>>();

        for (T entity : entities) {
            result.add(toResource(entity));
        }

        Resources<Resource<T>> resources = new Resources<>(result);

        addLinks(resources);

        return resources;
    }



//    	private <S, R extends ResourceSupport> PagedResources<R> createResource(Page<S> page,
//			ResourceAssembler<S, R> assembler, Link link) {
//
//		Assert.notNull(page, "Page must not be null!");
//		Assert.notNull(assembler, "ResourceAssembler must not be null!");
//
//		List<R> resources = new ArrayList<R>(page.getNumberOfElements());
//
//		for (S element : page) {
//			resources.add(assembler.toResource(element));
//		}
//
//		PagedResources<R> resource = createPagedResource(resources, asPageMetadata(page), page);
//
//		return addPaginationLinks(resource, page, link);
//	}




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
