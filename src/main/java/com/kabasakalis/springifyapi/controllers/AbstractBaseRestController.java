

package com.kabasakalis.springifyapi.controllers;


import com.kabasakalis.springifyapi.models.BaseEntity;
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
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.*;
import org.springframework.hateoas.SimpleIdentifiableResourceAssembler;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static org.springframework.web.bind.annotation.RequestMethod.PATCH;
import static org.springframework.web.bind.annotation.RequestMethod.POST;


public abstract class AbstractBaseRestController<T extends BaseEntity> implements ApplicationContextAware {


    protected JpaRepository<T, Long> repository;
    protected SimpleIdentifiableResourceAssembler<T> assembler;
    protected PagedResourcesAssembler<T> pagedAssembler;
    protected Class<T> resourceClass;
    protected String resourceClassName;
    protected Repositories repositories;
    protected RepositoryInvokerFactory repositoryInvokerFactory;
    protected ApplicationContext appContext;


    @Autowired
    public AbstractBaseRestController(JpaRepository<T, Long> repository,
                                      ApplicationContext appContext,
                                      SimpleIdentifiableResourceAssembler<T> assembler) {
        this.repository = repository;
        HateoasPageableHandlerMethodArgumentResolver resolver = new HateoasPageableHandlerMethodArgumentResolver();
        this.pagedAssembler = new PagedResourcesAssembler<T>(resolver, null);
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




    @GetMapping(value = "/home", produces = MediaTypes.HAL_JSON_VALUE)
    public ResourceSupport root() {

        ResourceSupport rootResource = new ResourceSupport();

//        rootResource.add(
//                linkTo(methodOn(EmployeeController.class).root()).withSelfRel(),
//                linkTo(methodOn(EmployeeController.class).findAll()).withRel("employees"));

        return rootResource;
    }




    @RequestMapping(
            method = RequestMethod.GET,
            produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Page<T>> getAll(Pageable pageRequest) {
        PagedResources<Resource<T>> pagedResources = pagedAssembler.toResource(repository.findAll(pageRequest), assembler);
        assembler.addLinks(pagedResources, pageRequest);
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


    protected <R extends BaseEntity> ResponseEntity<Page<R>> getAssociatedResources(
            SimpleIdentifiableResourceAssembler<R> associatedResourceAssembler,
            PagedResourcesAssembler<R> associatedResourcePagedAssembler,
            Page<R> pagedAssociatedResources,
            Pageable pageRequest) {
        PagedResources<Resource<R>> pagedResponseBody = associatedResourcePagedAssembler
                .toResource(pagedAssociatedResources, associatedResourceAssembler);
        return new ResponseEntity(pagedResponseBody, HttpStatus.OK);
    }


    protected <R extends BaseEntity> ResponseEntity<Resource<R>> getAssociatedResource(
            Long resourceId,
            Class<R> associatedResourceClass,
            SimpleIdentifiableResourceAssembler<R> associatedResourceAssembler) {
        return Optional.ofNullable(repository.findOne(resourceId))
                .map(PropertyAccessorFactory::forBeanPropertyAccess)
                .map(resourceAccessor -> {
                    return (R) resourceAccessor.getPropertyValue(associatedResourceClass.getSimpleName().toLowerCase());
                })
                .map(associatedResource -> new ResponseEntity<Resource<R>>(associatedResourceAssembler.toResource(
                        (R) associatedResource),
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    protected ResponseEntity<? extends ResourceSupport> associateResources(
            final Association association,
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

                            if (association == Association.ONE_TO_MANY) {

                                List<BaseEntity> subresourceCollection = getResourceCollection(resourceAccessor, subresourceClassName);
                                subresourceAccessor.setPropertyValue(resourceClassName.toLowerCase(), resource);
                                subresourceCollection.add(subresource.get());

                            } else if (association == Association.MANY_TO_MANY) {

                                List<BaseEntity> subresourceCollection = getResourceCollection(resourceAccessor, subresourceClassName);
                                List<BaseEntity> resourceCollection = getResourceCollection(subresourceAccessor, resourceClassName);
                                subresourceCollection.add(subresource.get());
                                resourceCollection.add(resource);

                            } else if (association == Association.ONE_TO_ONE) {
                                resourceAccessor.setPropertyValue(subresourceClassName.toLowerCase(), subresource.get());
                                subresourceAccessor.setPropertyValue(resourceClassName.toLowerCase(), resource);

                            } else {
                                return ControllerUtils.toEmptyResponse(HttpStatus.NOT_FOUND);
                            }

                        } else {
                            return ControllerUtils.toEmptyResponse(HttpStatus.NOT_FOUND);
                        }
                    }
                    repository.save(resource);
                    return ControllerUtils.toEmptyResponse(HttpStatus.NO_CONTENT);
                })
                .orElse(ControllerUtils.toEmptyResponse(HttpStatus.NOT_FOUND));
    }


    protected ResponseEntity<? extends ResourceSupport> deleteAssociation(
            final Association association,
            JpaRepository<? extends BaseEntity, Long> relationshipOwnerClassrepository,
            Long resourceId,
            Long subresourceId) {

        T resource = repository.findOne(resourceId);
        BaseEntity subresource = loadEntity(relationshipOwnerClassrepository, new Link("/".concat(subresourceId.toString())));
        if (!resourcesExistAndAreAssociated(association, relationshipOwnerClassrepository, resource, subresource))
            return ControllerUtils.toEmptyResponse(HttpStatus.NOT_FOUND);
        PropertyAccessor subresourceAccessor = PropertyAccessorFactory.forBeanPropertyAccess(subresource);
        PropertyAccessor resourceAccessor = PropertyAccessorFactory.forBeanPropertyAccess(resource);
        String subresourceClassName = getAssociatedClassFromRepository(relationshipOwnerClassrepository).getSimpleName();

        if (association == Association.ONE_TO_MANY) {
            subresourceAccessor.setPropertyValue(resourceClassName.toLowerCase(), null);
        } else if (association == Association.MANY_TO_MANY) {
            List<BaseEntity> subresourceCollection = getResourceCollection(resourceAccessor, subresourceClassName);
            List<BaseEntity> resourceCollection = getResourceCollection(subresourceAccessor, resourceClassName);
            subresourceCollection.remove(subresource);
            resourceCollection.remove(resource);
        } else if (association == Association.ONE_TO_ONE) {
            resourceAccessor.setPropertyValue(subresourceClassName.toLowerCase(), null);
        }
        repository.save(resource);
        return ControllerUtils.toEmptyResponse(HttpStatus.NO_CONTENT);
    }


    private List<BaseEntity> getResourceCollection(PropertyAccessor accessor, String className) {
        return (List<BaseEntity>) accessor.getPropertyValue(className.toLowerCase().concat("s"));
    }

    private Class<?> getAssociatedClassFromRepository(JpaRepository<?, Long> repository) {
        DefaultRepositoryMetadata drm = new DefaultRepositoryMetadata(
                repository.getClass().getInterfaces()[0]);
        return drm.getDomainType();
    }

    protected BaseEntity loadEntity(JpaRepository<? extends BaseEntity, Long> repository, Link link) {
        String href = link.expand().getHref();
        Long id = Long.parseLong(href.substring(href.lastIndexOf('/') + 1));
        return repository.findOne(id);
    }


    private boolean resourcesExistAndAreAssociated(Association association,
                                                   JpaRepository<? extends BaseEntity, Long> relationshipOwnerClassrepository,
                                                   BaseEntity resource,
                                                   BaseEntity subresource) {
        if ((resource == null) || (subresource == null)) return false;
        PropertyAccessor subresourceAccessor = PropertyAccessorFactory.forBeanPropertyAccess(subresource);
        PropertyAccessor resourceAccessor = PropertyAccessorFactory.forBeanPropertyAccess(resource);
        if (association == Association.ONE_TO_MANY) {
            BaseEntity subresourceOwner = (BaseEntity) subresourceAccessor.getPropertyValue(resourceClassName.toLowerCase());
            return subresourceOwner != null  &&  subresourceOwner.getId().equals(resource.getId());
        } else if (association == Association.MANY_TO_MANY) {
            List<BaseEntity> subresourceCollection = getResourceCollection(resourceAccessor, subresource.getClass().getSimpleName());
            List<BaseEntity> resourceCollection = getResourceCollection(subresourceAccessor, resourceClassName);
            return (subresourceCollection.contains(subresource) && resourceCollection.contains(resource));
        } else if (association == Association.ONE_TO_ONE) {
            BaseEntity subresourceOwner = (BaseEntity) subresourceAccessor.getPropertyValue(resourceClassName.toLowerCase());
            BaseEntity resourceOwner = (BaseEntity) resourceAccessor.getPropertyValue(subresource.getClass().getSimpleName().toLowerCase());
           return  (subresourceOwner != null) && (resourceOwner != null) &&
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
