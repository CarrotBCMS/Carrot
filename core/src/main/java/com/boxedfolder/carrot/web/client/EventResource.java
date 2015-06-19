package com.boxedfolder.carrot.web.client;

import com.boxedfolder.carrot.domain.event.Event;
import com.boxedfolder.carrot.service.EventService;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
@RequestMapping("/client/events")
public class EventResource extends CrudResource<EventService, Event> {
}
