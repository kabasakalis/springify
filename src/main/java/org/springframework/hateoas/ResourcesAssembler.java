
package org.springframework.hateoas;

public interface ResourcesAssembler<T, D extends ResourceSupport> {

    Resources<D> toResources(Iterable<? extends T> entities);

}
