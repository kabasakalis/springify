

package com.kabasakalis.springifyapi.controllers;


import com.kabasakalis.springifyapi.assemblers.PagedCustomResourcesAssembler;
import com.kabasakalis.springifyapi.domain.BaseEntity;
import com.kabasakalis.springifyapi.exceptions.AssociationNotFoundException;
import com.kabasakalis.springifyapi.exceptions.EntityNotFoundException;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyAccessor;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.GenericTypeResolver;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.core.support.DefaultRepositoryMetadata;
import org.springframework.data.repository.support.DefaultRepositoryInvokerFactory;
import org.springframework.data.repository.support.Repositories;
import org.springframework.data.repository.support.RepositoryInvokerFactory;
import org.springframework.data.rest.webmvc.ControllerUtils;
import org.springframework.data.web.HateoasPageableHandlerMethodArgumentResolver;
import org.springframework.hateoas.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static org.springframework.web.bind.annotation.RequestMethod.PATCH;
import static org.springframework.web.bind.annotation.RequestMethod.POST;


public abstract class AbstractBaseRestController<T extends BaseEntity>
        extends CoreController
        implements ApplicationContextAware {


    protected JpaRepository<T, Long> repository;
    protected SimpleIdentifiableResourceAssembler<T> assembler;
    protected PagedCustomResourcesAssembler<T> pagedAssembler;
    protected Class<T> resourceClass;
    protected String resourceClassName;
    protected Repositories repositories;
    protected RepositoryInvokerFactory repositoryInvokerFactory;
    protected ApplicationContext appContext;


    public AbstractBaseRestController() { };

    @Autowired
    public AbstractBaseRestController(JpaRepository<T, Long> repository,
                                      ApplicationContext appContext,
                                      SimpleIdentifiableResourceAssembler<T> assembler) {
        this.repository = repository;
        HateoasPageableHandlerMethodArgumentResolver resolver = new HateoasPageableHandlerMethodArgumentResolver();
        this.pagedAssembler = new PagedCustomResourcesAssembler<T>(resolver, null);
        this.assembler = assembler;
        this.resourceClass = (Class<T>) GenericTypeResolver.resolveTypeArgument(getClass(), AbstractBaseRestController.class);
        this.resourceClassName = resourceClass.getSimpleName();
        this.repositories = new Repositories(appContext);
        this.repositoryInvokerFactory = new DefaultRepositoryInvokerFactory(repositories, new DefaultConversionService());
        this.appContext = appContext;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.appContext = applicationContext;
    }

    public ApplicationContext getContext() {
        return appContext;
    }

    @RequestMapping(
            method = RequestMethod.GET,
            produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Page<T>> getAll(Pageable pageRequest) {
        PagedResources<ResourceSupport> pagedResources = pagedAssembler.toResource(repository.findAll(pageRequest), assembler);
        assembler.addLinks(pagedResources);
        return new ResponseEntity(pagedResources, HttpStatus.OK);
    }


    @RequestMapping(
            method = RequestMethod.GET,
            path = "/{id}",
            produces = MediaTypes.HAL_JSON_VALUE)
    ResponseEntity<ResourceSupport> getOne(@PathVariable Long id) throws EntityNotFoundException {
        return Optional.ofNullable(repository.findOne(id))
                .map(o -> new ResponseEntity<>(assembler.toCustomResource(o), HttpStatus.OK))
                .orElseThrow(() -> new EntityNotFoundException(resourceClass, id));

    }

    @RequestMapping(
            method = POST,
            produces = MediaTypes.HAL_JSON_VALUE)
    ResponseEntity<Void> addOne(@RequestBody T entityBody) {
        T entity = repository.save(entityBody);
        HttpHeaders httpHeaders = new HttpHeaders();
        URI location_link = linkTo(methodOn(this.getClass()).getOne(entity.getId())).toUri();
        httpHeaders.setLocation(location_link);
        return new ResponseEntity(assembler.toCustomResource(entity), httpHeaders, HttpStatus.CREATED);
    }


    @RequestMapping(
            method = PATCH,
            path = "/{id}",
            produces = MediaTypes.HAL_JSON_VALUE)
    ResponseEntity updateOne(@RequestBody T entityPatch, @PathVariable long id) throws EntityNotFoundException {
        return Optional.ofNullable(repository.findOne(id))
                .map(entity -> {
                    entityPatch.setId(id);
                    repository.save(entityPatch);
                    return new ResponseEntity<>(assembler.toCustomResource(entity), HttpStatus.OK);
                })
                .orElseThrow(() -> new EntityNotFoundException(resourceClass, id));

    }

    @Transactional
    @RequestMapping(
            method = RequestMethod.DELETE,
            path = "/{id}",
            produces = MediaTypes.HAL_JSON_VALUE)
    ResponseEntity deleteOne(@PathVariable Long id) throws EntityNotFoundException {
        return Optional.ofNullable(repository.findOne(id))
                .map(entity -> {
                    repository.delete(entity);
                    return new ResponseEntity<>(assembler.toCustomResource(entity), HttpStatus.OK);
                })
                .orElseThrow(() -> new EntityNotFoundException(resourceClass, id));
    }


    protected <R extends BaseEntity> ResponseEntity<Page<R>> getAssociatedResources(
            SimpleIdentifiableResourceAssembler<R> associatedResourceAssembler,
            PagedCustomResourcesAssembler<R> associatedResourcePagedAssembler,
            Page<R> pagedAssociatedResources,
            Pageable pageRequest) {
        PagedResources<ResourceSupport> pagedResponseBody = associatedResourcePagedAssembler
                .toResource(pagedAssociatedResources, associatedResourceAssembler);
        associatedResourceAssembler.addLinks(pagedResponseBody);
        return new ResponseEntity(pagedResponseBody, HttpStatus.OK);
    }


    protected <R extends BaseEntity> ResponseEntity<ResourceSupport> getAssociatedResource(
            Long resourceId,
            Class<R> associatedResourceClass,
            SimpleIdentifiableResourceAssembler<R> associatedResourceAssembler) throws EntityNotFoundException {
        return Optional.ofNullable(repository.findOne(resourceId))
                .map(PropertyAccessorFactory::forBeanPropertyAccess)
                .map(resourceAccessor -> {
                    return Optional.ofNullable(resourceAccessor.getPropertyValue(associatedResourceClass.getSimpleName().toLowerCase()));
                })
                .map((associatedResource) -> {
                    if (associatedResource.isPresent()) {
                        return new ResponseEntity<>(associatedResourceAssembler.toCustomResource((R) associatedResource.get()), HttpStatus.OK);
                    } else {
                        throw new EntityNotFoundException(associatedResourceClass);
                    }
                })
                .orElseThrow(() -> new EntityNotFoundException(resourceClass, resourceId));
    }


    protected <R extends BaseEntity> ResponseEntity<? extends ResourceSupport> associateResources(
            final Association association,
            Class<R> associatedResourceClass,
            JpaRepository<? extends BaseEntity, Long> relationshipOwnerClassrepository,
            Long id,
            Resources<? extends BaseEntity> links) throws EntityNotFoundException, AssociationNotFoundException {
        String associatedResourceClassName = associatedResourceClass.getSimpleName();
        return Optional.ofNullable(repository.findOne(id))
                .map(resource -> {
                    for (Link link : links.getLinks()) {
                        Long subresourceId = parseIdFromLink(link);
                        Optional<? extends BaseEntity> subresource = Optional.ofNullable(
                                loadEntity(relationshipOwnerClassrepository, link));
                        if (subresource.isPresent()) {
                            PropertyAccessor subresourceAccessor = PropertyAccessorFactory.forBeanPropertyAccess(subresource.get());
                            PropertyAccessor resourceAccessor = PropertyAccessorFactory.forBeanPropertyAccess(resource);

                            if (association == Association.ONE_TO_MANY) {

                                List<BaseEntity> subresourceCollection = getResourceCollection(resourceAccessor,
                                        associatedResourceClassName);
                                subresourceAccessor.setPropertyValue(resourceClassName.toLowerCase(), resource);
                                subresourceCollection.add(subresource.get());

                            } else if (association == Association.MANY_TO_MANY) {

                                Set<BaseEntity> subresourceCollection = getSetResourceCollection(resourceAccessor, associatedResourceClassName);
                                Set<BaseEntity> resourceCollection = getSetResourceCollection(subresourceAccessor, resourceClassName);
                                subresourceCollection.add(subresource.get());
                                resourceCollection.add(resource);

                            } else if (association == Association.ONE_TO_ONE) {
                                resourceAccessor.setPropertyValue(associatedResourceClassName.toLowerCase(), subresource.get());
                                subresourceAccessor.setPropertyValue(resourceClassName.toLowerCase(), resource);

                            } else {
                                throw new AssociationNotFoundException(resourceClass, associatedResourceClass, id, subresource.get().getId());
                            }

                        } else {
                            throw new EntityNotFoundException(associatedResourceClass, subresourceId);
                        }
                    }
                    repository.save(resource);
                    return ControllerUtils.toEmptyResponse(HttpStatus.NO_CONTENT);
                })
                .orElseThrow(() -> new EntityNotFoundException(resourceClass, id));
    }


    protected <R extends BaseEntity> ResponseEntity<? extends ResourceSupport> deleteAssociation(
            final Association association,
            Class<R> associatedClass,
            JpaRepository<? extends BaseEntity, Long> relationshipOwnerClassrepository,
            Long resourceId,
            Long associatedResourceId) throws EntityNotFoundException, AssociationNotFoundException {

        T resource = repository.findOne(resourceId);
        BaseEntity associatedResource = loadEntity(relationshipOwnerClassrepository, new Link("/".concat(associatedResourceId.toString())));
        if (!resourcesExistAndAreAssociated(association, resource, associatedResource, associatedClass, resourceId,
                associatedResourceId))
            throw new AssociationNotFoundException(resourceClass, associatedClass, resourceId, associatedResourceId);
        String associatedClassName = associatedClass.getSimpleName();
        PropertyAccessor subresourceAccessor = PropertyAccessorFactory.forBeanPropertyAccess(associatedResource);
        PropertyAccessor resourceAccessor = PropertyAccessorFactory.forBeanPropertyAccess(resource);

        if (association == Association.ONE_TO_MANY) {
            subresourceAccessor.setPropertyValue(resourceClassName.toLowerCase(), null);
        } else if (association == Association.MANY_TO_MANY) {
            Set<BaseEntity> associatedResourceCollection = getSetResourceCollection(resourceAccessor, associatedClassName);
            Set<BaseEntity> resourceCollection = getSetResourceCollection(subresourceAccessor, resourceClassName);
            associatedResourceCollection.remove(associatedResource);
            resourceCollection.remove(resource);
        } else if (association == Association.ONE_TO_ONE) {
            resourceAccessor.setPropertyValue(associatedClassName.toLowerCase(), null);
        }
        repository.save(resource);
        return ControllerUtils.toEmptyResponse(HttpStatus.NO_CONTENT);
    }


    private List<BaseEntity> getResourceCollection(PropertyAccessor accessor, String className) {
        return (List<BaseEntity>) accessor.getPropertyValue(className.toLowerCase().concat("s"));
    }

    private Set<BaseEntity> getSetResourceCollection(PropertyAccessor accessor, String className) {
        return (Set<BaseEntity>) accessor.getPropertyValue(className.toLowerCase().concat("s"));
    }

    private Class<?> getAssociatedClassFromRepository(JpaRepository<?, Long> repository) {
        DefaultRepositoryMetadata drm = new DefaultRepositoryMetadata(
                repository.getClass().getInterfaces()[0]);
        return drm.getDomainType();
    }

    protected BaseEntity loadEntity(JpaRepository<? extends BaseEntity, Long> repository, Link link) {
        return repository.findOne(parseIdFromLink(link));
    }

    private Long parseIdFromLink(Link link) {
        String href = link.expand().getHref();
        return Long.parseLong(href.substring(href.lastIndexOf('/') + 1));
    }

    private <R extends BaseEntity> boolean resourcesExistAndAreAssociated(Association association,
                                                                          BaseEntity resource,
                                                                          BaseEntity subresource,
                                                                          Class<R> associationClass,
                                                                          Long resourceId,
                                                                          Long associatedResourceId
    ) {
        if (resource == null) throw new EntityNotFoundException(resourceClass, resourceId);
        if (subresource == null) throw new EntityNotFoundException(associationClass, associatedResourceId);
        PropertyAccessor subresourceAccessor = PropertyAccessorFactory.forBeanPropertyAccess(subresource);
        PropertyAccessor resourceAccessor = PropertyAccessorFactory.forBeanPropertyAccess(resource);
        if (association == Association.ONE_TO_MANY) {
            BaseEntity subresourceOwner = (BaseEntity) subresourceAccessor.getPropertyValue(resourceClassName.toLowerCase());
            return subresourceOwner != null && subresourceOwner.getId().equals(resource.getId());
        } else if (association == Association.MANY_TO_MANY) {
            Set<BaseEntity> subresourceCollection = getSetResourceCollection(resourceAccessor, subresource.getClass().getSimpleName());
            Set<BaseEntity> resourceCollection = getSetResourceCollection(subresourceAccessor, resourceClassName);
            return (subresourceCollection.contains(subresource) && resourceCollection.contains(resource));
        } else if (association == Association.ONE_TO_ONE) {
            BaseEntity subresourceOwner = (BaseEntity) subresourceAccessor.getPropertyValue(resourceClassName.toLowerCase());
            BaseEntity resourceOwner = (BaseEntity) resourceAccessor.getPropertyValue(subresource.getClass().getSimpleName().toLowerCase());
            return (subresourceOwner != null) && (resourceOwner != null) &&
                    (subresourceOwner.getId().equals(resource.getId())) &&
                    (resourceOwner.getId().equals(subresource.getId()));
        } else {
            return false;
        }

    }

    enum Association {
        ONE_TO_MANY,
        MANY_TO_MANY,
        ONE_TO_ONE
    }
}
