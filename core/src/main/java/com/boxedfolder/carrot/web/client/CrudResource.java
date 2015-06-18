package com.boxedfolder.carrot.web.client;

import com.boxedfolder.carrot.domain.AbstractEntity;
import com.boxedfolder.carrot.service.CrudService;
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

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<S> getAll() {
        return service.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public S create(@RequestBody @Valid final S object) {
        return service.save(object);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Long id) {
        service.delete(id);
    }

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