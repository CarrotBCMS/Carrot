package com.boxedfolder.carrot.service.impl;

import com.boxedfolder.carrot.domain.App;
import com.boxedfolder.carrot.domain.Beacon;
import com.boxedfolder.carrot.domain.event.Event;
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
