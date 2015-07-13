package com.boxedfolder.carrot.service;

import com.boxedfolder.carrot.domain.general.AbstractEntity;
import com.boxedfolder.carrot.exceptions.GeneralExceptions;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
public interface CrudService<T extends AbstractEntity> {
    T find(Long id) throws GeneralExceptions.NotFoundException;
    List<T> findAll();
    List<T> findAll(Pageable pageable);
    List<T> findAll(int page, int size);

    T save(T object);
    void delete(Long id) throws GeneralExceptions.NotFoundException;
    long count();
}
