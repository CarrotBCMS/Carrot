package com.boxedfolder.carrot.web.client;

import com.boxedfolder.carrot.domain.event.Event;
import com.boxedfolder.carrot.domain.util.EventList;
import com.boxedfolder.carrot.domain.util.View;
import com.boxedfolder.carrot.service.EventService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
@RestController
@RequestMapping("/client/events")
public class EventResource extends CrudResource<EventService, Event> {
    @JsonView(View.Client.class)
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Event> getAll() {
        EventList list = new EventList();
        list.addAll(service.findAll());
        return list;
    }
}
