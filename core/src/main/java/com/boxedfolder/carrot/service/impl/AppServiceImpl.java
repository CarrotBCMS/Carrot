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
            object.setDateCreated(new DateTime());
            object.setApplicationKey(UUID.randomUUID()); // Add a random UUID
        } else {
            // If it is already persisted - fetch it and update creation date
            App oldObject = find(object.getId());
            object.setDateCreated(oldObject.getDateCreated());
            object.setApplicationKey(oldObject.getApplicationKey());
        }

        object.setDateUpdated(new DateTime()); // Mark as updated
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
