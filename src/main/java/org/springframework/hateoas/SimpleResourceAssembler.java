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

import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

public class SimpleResourceAssembler<T> implements ResourceAssembler<T, Resource<T>>, ResourcesAssembler<T, Resource<T>> {

    @Override
    public Resource<T> toResource(T entity) {
        Resource<T> resource = new Resource<T>(entity);
        addLinks(resource);
        return resource;
    }

    @Override
    public Resources<Resource<T>> toResources(Iterable<? extends T> entities) {
        Assert.notNull(entities, "Entities must not be null!");
        List<Resource<T>> result = new ArrayList<Resource<T>>();
        for (T entity : entities) {
            result.add(toResource(entity));
        }

        Resources<Resource<T>> resources = new Resources<>(result);
        addLinks(resources);
        return resources;
    }


    protected void addLinks(Resource<T> resource) {
        // Default adds no links
    }

    protected void addLinks(Resources<Resource<T>> resources) {
        // Default adds no links.
    }


}
