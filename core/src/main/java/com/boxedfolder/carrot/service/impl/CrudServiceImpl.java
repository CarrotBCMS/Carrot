package com.boxedfolder.carrot.service.impl;

import com.boxedfolder.carrot.domain.AbstractEntity;
import com.boxedfolder.carrot.exceptions.GeneralExceptions;
import com.boxedfolder.carrot.service.CrudService;
import org.joda.time.LocalDateTime;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.inject.Inject;
import java.util.List;

public abstract class CrudServiceImpl<T extends AbstractEntity, S extends PagingAndSortingRepository<T, Long>> implements CrudService<T> {
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
        return (List<T>)repository.findAll();
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
        if (object.getId() == null) {
            // Assuming our entity is a brand new one
            object.setDateCreated(new LocalDateTime());
        } else {
            // If it is already persisted - fetch it and update creation date
            T oldObject = find(object.getId());
            object.setDateCreated(oldObject.getDateCreated());
        }

        object.setDateUpdated(new LocalDateTime()); // Mark as updated

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