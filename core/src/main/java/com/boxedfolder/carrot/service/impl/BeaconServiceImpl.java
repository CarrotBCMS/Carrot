/*
 * Carrot - beacon content management
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

package com.boxedfolder.carrot.service.impl;

import com.boxedfolder.carrot.domain.Beacon;
import com.boxedfolder.carrot.domain.Event;
import com.boxedfolder.carrot.exceptions.GeneralExceptions;
import com.boxedfolder.carrot.repository.BeaconRepository;
import com.boxedfolder.carrot.repository.EventRepository;
import com.boxedfolder.carrot.service.BeaconService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
@Service
public class BeaconServiceImpl extends CrudServiceImpl<Beacon, BeaconRepository> implements BeaconService {
    private EventRepository eventRepository;

    @Override
    public Beacon save(Beacon object) {
        // Check if there is already another uuid/major/minor combination
        Beacon oldObject = repository.findFirstByUuidAndMajorAndMinor(object.getUuid(),
                object.getMajor(),
                object.getMinor());

        if (oldObject != null && !object.equals(oldObject)) {
            throw new GeneralExceptions.AlreadyExistsException();
        }

        return super.save(object);
    }


    @Override
    public void delete(Long id) {
        // Override here to remove unwanted relationships
        Beacon object = repository.findOne(id);
        if (object == null) {
            throw new GeneralExceptions.NotFoundException();
        }

        // Iterate through all events to release relationship
        for (Event event : object.getEvents()) {
            event.getBeacons().remove(object);
            eventRepository.save(event);
        }
        repository.delete(object);
    }


    @Inject
    public void setEventRepository(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }
}
