package com.boxedfolder.web.client;

import com.boxedfolder.carrot.domain.event.Event;
import com.boxedfolder.carrot.service.EventService;
import com.boxedfolder.carrot.web.client.EventResource;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.notNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
@RunWith(MockitoJUnitRunner.class)
public class EventResourceTest {
    @Mock
    private EventService service;
    private MockMvc restUserMockMvc;
    private List<Event> testData;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EventResource resource = new EventResource();
        resource.setService(service);
        restUserMockMvc = MockMvcBuilders.standaloneSetup(resource).build();

        // Create 3 events
        testData = new ArrayList<Event>();
        Event event = new Event();
        event.setName("Event 1");
        testData.add(event);

        event = new Event();
        event.setName("Event 2");
        testData.add(event);
        testData.add(event);

        event = new Event();
        event.setName("Event 3");
        testData.add(event);
        testData.add(event);
    }

    @Test
    public void testGetAllEvents() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String value = mapper.writeValueAsString(testData);

        when(service.findAll()).thenReturn(testData);
        restUserMockMvc.perform(get("/client/events")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(value));
    }

    @Test
    public void testAddEvent() throws Exception {
        Event event = testData.get(0);
        ObjectMapper mapper = new ObjectMapper();
        String value = mapper.writeValueAsString(event);
        given(service.save((Event)notNull())).willReturn(event);
        restUserMockMvc.perform(post("/client/events")
                .content(value)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().string(value));
    }

    @Test
    public void testDeleteEvent() throws Exception {
        restUserMockMvc.perform(delete("/client/events/0"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetSingleEvent() throws Exception {
        Event event = testData.get(0);
        given(service.find(1L)).willReturn(event);
        ObjectMapper mapper = new ObjectMapper();
        String value = mapper.writeValueAsString(event);
        restUserMockMvc.perform(get("/client/events/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(value));
    }
}
