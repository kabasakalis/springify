

package com.kabasakalis.springifyapi.controllers;


import com.kabasakalis.springifyapi.models.BaseEntity;
import org.springframework.beans.PropertyAccessor;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.CollectionFactory;
import org.springframework.core.GenericTypeResolver;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mapping.PersistentEntity;
import org.springframework.data.mapping.PersistentProperty;
import org.springframework.data.mapping.PersistentPropertyAccessor;
import org.springframework.data.repository.core.support.DefaultRepositoryMetadata;
import org.springframework.data.repository.support.Repositories;
import org.springframework.data.repository.support.RepositoryInvoker;
import org.springframework.data.repository.support.RepositoryInvokerFactory;
import org.springframework.data.rest.core.event.AfterLinkSaveEvent;
import org.springframework.data.rest.core.event.BeforeLinkSaveEvent;
import org.springframework.data.rest.core.mapping.PropertyAwareResourceMapping;
import org.springframework.data.rest.core.mapping.ResourceMetadata;
import org.springframework.data.rest.core.util.Function;
import org.springframework.data.rest.webmvc.ControllerUtils;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.data.rest.webmvc.RootResourceInformation;
import org.springframework.data.rest.webmvc.support.BackendId;
import org.springframework.data.web.HateoasPageableHandlerMethodArgumentResolver;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.*;
import org.springframework.http.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.Serializable;
import java.net.URI;
import java.util.*;

import static org.springframework.data.rest.webmvc.RestMediaTypes.SPRING_DATA_COMPACT_JSON_VALUE;
import static org.springframework.data.rest.webmvc.RestMediaTypes.TEXT_URI_LIST_VALUE;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static org.springframework.web.bind.annotation.RequestMethod.*;

// import com.kabasakalis.springifyapi.services.SpringifyService;
//import org.springframework.data.rest.webmvc.RepositoryPropertyReferenceController;




public abstract class AbstractBaseRestController<T extends BaseEntity> {

    // private Logger logger = LoggerFactory.getLogger(RESTController.class);

    protected JpaRepository<T, Long> repository;
    protected PagedResourcesAssembler<T> pagedAssembler;
    protected Class<T> resourceClass;
    protected String resourceClassName;

    @Autowired
    protected  Repositories repositories;
    @Autowired
    protected  RepositoryInvokerFactory repositoryInvokerFactory;
    // @Autowired
    protected SimpleIdentifiableResourceAssembler<T> assembler;

//    protected static final String BASE_MAPPING = "/{repository}/{id}/{property}";
    protected static final String BASE_MAPPING = "/{id}/{property}";
    protected static final Collection<HttpMethod> AUGMENTING_METHODS = Arrays.asList(HttpMethod.PATCH, HttpMethod.POST);


    @Autowired
    public AbstractBaseRestController(JpaRepository<T, Long> repository,
//                                      Repositories repositories,
//                                      RepositoryInvokerFactory repositoryInvokerFactory,
                                      SimpleIdentifiableResourceAssembler<T> assembler) {
        this.repository = repository;
        HateoasPageableHandlerMethodArgumentResolver resolver = new HateoasPageableHandlerMethodArgumentResolver();
        this.pagedAssembler = new PagedResourcesAssembler<T>(resolver, null);
        this.assembler = assembler;
        this.resourceClass = (Class<T>) GenericTypeResolver.resolveTypeArgument(getClass(), AbstractBaseRestController.class);
        this.resourceClassName = resourceClass.getSimpleName();

    }


    @RequestMapping(
            method = RequestMethod.GET,
            produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Page<T>> getAll(Pageable pageRequest) {
        PagedResources<Resource<T>> pagedResources = pagedAssembler.toResource(repository.findAll(pageRequest), assembler);
        return new ResponseEntity(pagedResources, HttpStatus.OK);
    }


    @RequestMapping(
            method = RequestMethod.GET,
            path = "/{id}",
            produces = MediaTypes.HAL_JSON_VALUE)
    ResponseEntity<Resource<T>> getOne(@PathVariable Long id) {
        return Optional.ofNullable(repository.findOne(id))
                .map(o -> new ResponseEntity<>(assembler.toResource(o), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(
            method = POST,
            produces = MediaTypes.HAL_JSON_VALUE)
    ResponseEntity<Void> addOne(@RequestBody T entityBody) {
        T entity = repository.save(entityBody);
        HttpHeaders httpHeaders = new HttpHeaders();
        URI location_link = linkTo(methodOn(ArtistController.class).getOne(entity.getId())).toUri();
        httpHeaders.setLocation(location_link);
        return new ResponseEntity(assembler.toResource(entity), httpHeaders, HttpStatus.CREATED);
    }


    @RequestMapping(
            method = PATCH,
            path = "/{id}",
            produces = MediaTypes.HAL_JSON_VALUE)
    ResponseEntity<Resource<T>> updateOne(@RequestBody T entityPatch, @PathVariable long id) {
        return Optional.ofNullable(repository.findOne(id))
                .map(entity -> {
                    entityPatch.setId(id);
                    repository.save(entityPatch);
                    return new ResponseEntity<>(assembler.toResource(entity), HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Transactional
    @RequestMapping(
            method = RequestMethod.DELETE,
            path = "/{id}",
            produces = MediaTypes.HAL_JSON_VALUE)
    ResponseEntity<Resource<T>> deleteOne(@PathVariable Long id) {
        return Optional.ofNullable(repository.findOne(id))
                .map(entity -> {
                    repository.delete(entity);
                    return new ResponseEntity<>(assembler.toResource(entity), HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    protected BaseEntity loadEntity(JpaRepository<? extends BaseEntity, Long> repository, Link link) {
        String href = link.expand().getHref();
        Long id = Long.parseLong(href.substring(href.lastIndexOf('/') + 1));
        return repository.findOne(id);
    }


    protected ResponseEntity<? extends ResourceSupport> addOneToManyResources(
            JpaRepository<? extends BaseEntity, Long> relationshipOwnerClassrepository,
            Long id,
            Resources<? extends BaseEntity> links) {
        DefaultRepositoryMetadata drm = new DefaultRepositoryMetadata(
                relationshipOwnerClassrepository.getClass().getInterfaces()[0]);
        Class<?> subresourceClass = drm.getDomainType();
        String subresourceClassName = subresourceClass.getSimpleName();
        return Optional.ofNullable(repository.findOne(id))
                .map(resource -> {
                    for (Link link : links.getLinks()) {
                        Optional<? extends BaseEntity> subresource = Optional.ofNullable(
                                loadEntity(relationshipOwnerClassrepository, link));
                        if (subresource.isPresent()) {
                            PropertyAccessor subresourceAccessor = PropertyAccessorFactory.forBeanPropertyAccess(subresource.get());
                            PropertyAccessor resourceAccessor = PropertyAccessorFactory.forBeanPropertyAccess(resource);
                            subresourceAccessor.setPropertyValue(resourceClassName.toLowerCase(), resource);
                            List<BaseEntity> subresourceCollection =
                                    (List<BaseEntity>) resourceAccessor.getPropertyValue(
                                            subresourceClassName.toLowerCase().concat("s"));
                            subresourceCollection.add(subresource.get());
                        } else {
                            return ControllerUtils.toEmptyResponse(HttpStatus.NOT_FOUND);
                        }
                    }
                    repository.save(resource);
                    return ControllerUtils.toEmptyResponse(HttpStatus.NO_CONTENT);
                })
                .orElse(ControllerUtils.toEmptyResponse(HttpStatus.NOT_FOUND));
    }




      @RequestMapping(value = BASE_MAPPING, method = { PATCH, PUT, POST }, //
            consumes = { MediaType.APPLICATION_JSON_VALUE, SPRING_DATA_COMPACT_JSON_VALUE, TEXT_URI_LIST_VALUE })
    public ResponseEntity<? extends ResourceSupport> createPropertyReference(
              final RootResourceInformation resourceInformation, final HttpMethod requestMethod,
              final @RequestBody(required = false) Resources<Object> incoming, @BackendId Serializable id,
              @PathVariable String property) throws Exception {

        final Resources<Object> source = incoming == null ? new Resources<Object>(Collections.emptyList()) : incoming;
        final RepositoryInvoker invoker = resourceInformation.getInvoker();

        Function<ReferencedProperty, ResourceSupport> handler = new Function<ReferencedProperty, ResourceSupport>() {

            @Override
            public ResourceSupport apply(ReferencedProperty prop) throws HttpRequestMethodNotSupportedException {

                Class<?> propertyType = prop.property.getType();

                if (prop.property.isCollectionLike()) {

                    Collection<Object> collection = AUGMENTING_METHODS.contains(requestMethod)
                            ? (Collection<Object>) prop.propertyValue : CollectionFactory.createCollection(propertyType, 0);

                    // Add to the existing collection
                    for (Link l : source.getLinks()) {
                        collection.add(loadPropertyValue(prop.propertyType, l));
                    }

                    prop.accessor.setProperty(prop.property, collection);

                } else if (prop.property.isMap()) {

                    Map<String, Object> map = AUGMENTING_METHODS.contains(requestMethod)
                            ? (Map<String, Object>) prop.propertyValue
                            : CollectionFactory.<String, Object> createMap(propertyType, 0);

                    // Add to the existing collection
                    for (Link l : source.getLinks()) {
                        map.put(l.getRel(), loadPropertyValue(prop.propertyType, l));
                    }

                    prop.accessor.setProperty(prop.property, map);

                } else {

                    if (HttpMethod.PATCH.equals(requestMethod)) {
                        throw new HttpRequestMethodNotSupportedException(HttpMethod.PATCH.name(), new String[] { "PATCH" },
                                "Cannot PATCH a reference to this singular property since the property type is not a List or a Map.");
                    }

                    if (source.getLinks().size() != 1) {
                        throw new IllegalArgumentException(
                                "Must send only 1 link to update a property reference that isn't a List or a Map.");
                    }

                    Object propVal = loadPropertyValue(prop.propertyType, source.getLinks().get(0));
                    prop.accessor.setProperty(prop.property, propVal);
                }

//                publisher.publishEvent(new BeforeLinkSaveEvent(prop.accessor.getBean(), prop.propertyValue));
                Object result = invoker.invokeSave(prop.accessor.getBean());
//                publisher.publishEvent(new AfterLinkSaveEvent(result, prop.propertyValue));

                return null;
            }
        };

        doWithReferencedProperty(resourceInformation, id, property, handler, requestMethod);

        return ControllerUtils.toEmptyResponse(HttpStatus.NO_CONTENT);
    }






        private ResourceSupport doWithReferencedProperty(RootResourceInformation resourceInformation, Serializable id,
                                                     String propertyPath, Function<ReferencedProperty, ResourceSupport> handler, HttpMethod method) throws Exception {

        ResourceMetadata metadata = resourceInformation.getResourceMetadata();
        PropertyAwareResourceMapping mapping = metadata.getProperty(propertyPath);

        if (mapping == null || !mapping.isExported()) {
            throw new ResourceNotFoundException();
        }

        PersistentProperty<?> property = mapping.getProperty();
        resourceInformation.verifySupportedMethod(method, property);

        RepositoryInvoker invoker = resourceInformation.getInvoker();
        Object domainObj = invoker.invokeFindOne(id);

        if (null == domainObj) {
            throw new ResourceNotFoundException();
        }

        PersistentPropertyAccessor accessor = property.getOwner().getPropertyAccessor(domainObj);
        return handler.apply(new ReferencedProperty(property, accessor.getProperty(property), accessor));
    }




    private Object loadPropertyValue(Class<?> type, Link link) {

        String href = link.expand().getHref();
        String id = href.substring(href.lastIndexOf('/') + 1);

        RepositoryInvoker invoker = repositoryInvokerFactory.getInvokerFor(type);

        return invoker.invokeFindOne(id);
    }


private class ReferencedProperty {

        final PersistentEntity<?, ?> entity;
        final PersistentProperty<?> property;
        final Class<?> propertyType;
        final Object propertyValue;
        final PersistentPropertyAccessor accessor;

        private ReferencedProperty(PersistentProperty<?> property, Object propertyValue,
                                   PersistentPropertyAccessor wrapper) {

            this.property = property;
            this.propertyValue = propertyValue;
            this.accessor = wrapper;
            this.propertyType = property.getActualType();
            this.entity = repositories.getPersistentEntity(propertyType);
        }
    }

}
