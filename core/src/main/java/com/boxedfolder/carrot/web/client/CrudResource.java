/*
 * Carrot - beacon management
 * Copyright (C) 2016 Heiko Dreyer
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.boxedfolder.carrot.web.client;

import com.boxedfolder.carrot.domain.general.AbstractEntity;
import com.boxedfolder.carrot.domain.util.View;
import com.boxedfolder.carrot.service.CrudService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.util.List;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
public abstract class CrudResource<T extends CrudService<S>, S extends AbstractEntity> {
    protected T service;

    @JsonView(View.Client.class)
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<S> getAll() {
        return service.findAll();
    }

    @JsonView(View.Client.class)
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public S create(@RequestBody @Valid S object) {
        return service.save(object);
    }

    @JsonView(View.Client.class)
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Long id) {
        service.delete(id);
    }

    @JsonView(View.Client.class)
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public S get(@PathVariable("id") Long id) {
        return service.find(id);
    }

    @Inject
    public void setService(T service) {
        this.service = service;
    }
}