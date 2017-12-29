

package com.kabasakalis.springifyapi.controllers;


import com.kabasakalis.springifyapi.models.BaseEntity;
import com.sun.istack.internal.Nullable;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyAccessor;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.CollectionFactory;
import org.springframework.core.GenericTypeResolver;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mapping.PersistentEntity;
import org.springframework.data.mapping.PersistentProperty;
import org.springframework.data.mapping.PersistentPropertyAccessor;
import org.springframework.data.repository.core.support.DefaultRepositoryMetadata;
import org.springframework.data.repository.support.DefaultRepositoryInvokerFactory;
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
import java.lang.reflect.Method;
import java.net.URI;
import java.util.*;

import static org.springframework.data.rest.webmvc.RestMediaTypes.SPRING_DATA_COMPACT_JSON_VALUE;
import static org.springframework.data.rest.webmvc.RestMediaTypes.TEXT_URI_LIST_VALUE;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static org.springframework.web.bind.annotation.RequestMethod.*;

// import com.kabasakalis.springifyapi.services.SpringifyService;
//import org.springframework.data.rest.webmvc.RepositoryPropertyReferenceController;


public abstract class AbstractBaseRestController<T extends BaseEntity> implements ApplicationContextAware {

    // private Logger logger = LoggerFactory.getLogger(RESTController.class);

    protected JpaRepository<T, Long> repository;
    protected PagedResourcesAssembler<T> pagedAssembler;
    protected Class<T> resourceClass;
    protected String resourceClassName;
    protected Repositories repositories;
    protected RepositoryInvokerFactory repositoryInvokerFactory;
    protected ApplicationContext appContext;
    protected SimpleIdentifiableResourceAssembler<T> assembler;
    protected static final String BASE_MAPPING = "/{id}/{property}";
    protected static final Collection<HttpMethod> AUGMENTING_METHODS = Arrays.asList(HttpMethod.PATCH, HttpMethod.POST);


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


//       @RequestMapping(
//            method = RequestMethod.GET,
//            produces = MediaTypes.HAL_JSON_VALUE)
//    public ResponseEntity<Page<T>> getAll(Pageable pageRequest) {
//        PagedResources<Resource<T>> pagedResources = pagedAssembler.toResource(repository.findAll(pageRequest), assembler);
//        return new ResponseEntity(pagedResources, HttpStatus.OK);
//    }
//
//
//    @RequestMapping(
//            method = RequestMethod.GET,
//            path = "/{id}",
//            produces = MediaTypes.HAL_JSON_VALUE)
//    ResponseEntity<Resource<T>> getOne(@PathVariable Long id) {
//        return Optional.ofNullable(repository.findOne(id))
//                .map(o -> new ResponseEntity<>(assembler.toResource(o), HttpStatus.OK))
//                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
//    }
//
//
//
//          @RequestMapping(
//            method = RequestMethod.GET,
//            produces = MediaTypes.HAL_JSON_VALUE)
//    public ResponseEntity<?> getAssociatedResources(Pageable pageRequest) {
//        PagedResources<Resource<T>> pagedResources = pagedAssembler.toResource(repository.findAll(pageRequest), assembler);
//        return new ResponseEntity(pagedResources, HttpStatus.OK);
//    }


    protected ResponseEntity<?> getAssociatedResources(
            Long resourceId,
            JpaRepository<? extends BaseEntity, Long> associatedClassrepository,
            SimpleIdentifiableResourceAssembler<BaseEntity> associatedResourceAssembler,
            PagedResourcesAssembler<BaseEntity> associatedResourcePagedAssembler,
            Pageable pageRequest,
            ) {

        T resource = repository.findOne(id);
        Class<?> resourceClass = resource.getClass();
        String resourceClassName = resourceClass.getSimpleName();

        PropertyAccessor resourceAccessor = PropertyAccessorFactory.forBeanPropertyAccess(resource);
        List<BaseEntity> associatedResources = (List<BaseEntity>) resourceAccessor.getPropertyValue(associationfield);
        Page<BaseEntity> pagedAssociatedResources = new PageImpl<BaseEntity>(associatedResources, pageRequest, associatedResources.size());
        return new ResponseEntity<>(pagedAssociatedResources, HttpStatus.OK);

    }


        protected ResponseEntity<?> getAssociatedResource(
            Long resourceId,
            JpaRepository<? extends BaseEntity, Long> associatedClassrepository,
            SimpleIdentifiableResourceAssembler<BaseEntity> associatedResourceAssembler,
            Long associatedResourceId
            ) {

        T resource = repository.findOne(id);
        PropertyAccessor resourceAccessor = PropertyAccessorFactory.forBeanPropertyAccess(resource);
        Object associatedResource = resourceAccessor.getPropertyValue(subresourceClassName.toLowerCase());
        return new ResponseEntity<>(associatedResourceAssembler.toResource(associatedResource, HttpStatus.OK);

    }


//        } else {

//            T resource = repository.findOne(id);
//            PropertyAccessor resourceAccessor = PropertyAccessorFactory.forBeanPropertyAccess(resource);
//            BaseEntity associatedResource = (BaseEntity) resourceAccessor.getPropertyValue(associationfield);
//            return new ResponseEntity<>(associatedResourceAssembler.toResource(associatedResource), HttpStatus.OK);
//        }
//        return Optional.ofNullable(repository.findOne(id))
//                .map(resource -> {
//                    if (id == null) {
//                        PagedResources<ResourceSupport> pagedResources =
//                                associatedResourcePagedAssembler
//                                        .toResource(relationshipOwnerClassrepository.findAll(pageRequest), associatedResourceAssembler);
//                        return new ResponseEntity(pagedResources, HttpStatus.OK);
//                    } else {

//                        PropertyAccessor resourceAccessor = PropertyAccessorFactory.forBeanPropertyAccess(resource);
//                        Object associatedResource = resourceAccessor.getPropertyValue(subresourceClassName.toLowerCase());
//                        return new ResponseEntity<>(associatedResourceAssembler.toResource(associatedResource), HttpStatus.OK);
//                    }
//                })
//                .map(o - o > new ResponseEntity<>(associatedResourceAssembler.toResource(o), HttpStatus.OK))
//                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    enum Association {
        ONE_TO_MANY,
        MANY_TO_MANY,
        ONE_TO_ONE
    }

    private List<BaseEntity> getResourceCollection(PropertyAccessor accessor, String className) {
        return (List<BaseEntity>) accessor.getPropertyValue(className.toLowerCase().concat("s"));
    }


}
