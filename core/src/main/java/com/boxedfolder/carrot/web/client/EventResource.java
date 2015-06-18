package com.boxedfolder.carrot.web.client;

import com.boxedfolder.carrot.domain.event.Event;
import com.boxedfolder.carrot.service.EventService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
@RestController
@RequestMapping("/client/events")
public class EventResource extends CrudResource<EventService, Event> {
}
