package com.boxedfolder.carrot.service.impl;

import com.boxedfolder.carrot.domain.Event;
import com.boxedfolder.carrot.repository.EventRepository;
import com.boxedfolder.carrot.service.EventService;
import org.springframework.stereotype.Service;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
@Service
public class EventServiceImpl extends CrudServiceImpl<Event, EventRepository> implements EventService {

    @Override
    public Event save(Event object) {
        Event oldObject = repository.findOne(object.getId());
        if (oldObject != null) {
            // Check if there are event-beacon relationship changes
        }
        return super.save(object);
    }
}
