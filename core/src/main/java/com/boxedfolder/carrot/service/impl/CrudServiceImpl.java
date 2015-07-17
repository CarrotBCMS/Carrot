package com.boxedfolder.carrot.service.impl;

import com.boxedfolder.carrot.domain.general.AbstractEntity;
import com.boxedfolder.carrot.exceptions.GeneralExceptions;
import com.boxedfolder.carrot.repository.OrderedRepository;
import com.boxedfolder.carrot.service.CrudService;
import org.joda.time.DateTime;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.inject.Inject;
import java.util.List;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
public abstract class CrudServiceImpl<T extends AbstractEntity, S extends OrderedRepository<T>>
        implements CrudService<T>
{
    protected S repository;

    @Override
    public T find(Long id) {
        T object = repository.findOne(id);
        if (object == null) {
            throw new GeneralExceptions.NotFoundException();
        }

        return object;
    }

    @Override
    public List<T> findAll() {
        return (List<T>)repository.findAllByOrderByDateCreatedDesc();
    }

    @Override
    public List<T> findAll(int page, int size) {
        return findAll(new PageRequest(page, size));
    }

    @Override
    public List<T> findAll(Pageable page) {
        return repository.findAll(page).getContent();
    }

    @Override
    public T save(T object) {
        return repository.save(object);
    }

    @Override
    public void delete(Long id) {
        T object = repository.findOne(id);
        if (object == null) {
            throw new GeneralExceptions.NotFoundException();
        }

        repository.delete(object);
    }

    @Override
    public long count() {
        return repository.count();
    }

    @Inject
    public void setRepository(S repository) {
        this.repository = repository;
    }
}