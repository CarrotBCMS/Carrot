/*
 * Carrot - beacon management
 * Copyright (C) 2015 Heiko Dreyer
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

import com.boxedfolder.carrot.domain.App;
import com.boxedfolder.carrot.domain.Event;
import com.boxedfolder.carrot.exceptions.GeneralExceptions;
import com.boxedfolder.carrot.repository.AppRepository;
import com.boxedfolder.carrot.repository.EventRepository;
import com.boxedfolder.carrot.service.AppService;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.UUID;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
@Service
public class AppServiceImpl extends CrudServiceImpl<App, AppRepository> implements AppService {
    private EventRepository eventRepository;

    // Override this to generate a random UUID for our new application
    @Override
    public App save(App object) {
        if (object.getId() == null) {
            object.setApplicationKey(UUID.randomUUID()); // Add a random UUID
        } else {
            // If it is already persisted - fetch it and update creation date
            App oldObject = find(object.getId());
            object.setApplicationKey(oldObject.getApplicationKey());
        }

        return repository.save(object);
    }

    @Override
    public void delete(Long id) {
        // Override here to remove unwanted relationships
        App object = repository.findOne(id);
        if (object == null) {
            throw new GeneralExceptions.NotFoundException();
        }

        // Iterate through all events to release relationship
        for (Event event : object.getEvents()) {
            event.getApps().remove(object);
            eventRepository.save(event);
        }
        repository.delete(object);
    }


    @Inject
    public void setEventRepository(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }
}
