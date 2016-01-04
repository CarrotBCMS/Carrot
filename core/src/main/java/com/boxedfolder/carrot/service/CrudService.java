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
