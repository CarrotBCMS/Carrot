package com.boxedfolder.domain;

import com.boxedfolder.carrot.domain.Event;
import com.boxedfolder.carrot.domain.NotificationEvent;
import com.boxedfolder.carrot.domain.TextEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
@RunWith(MockitoJUnitRunner.class)
public class EventTest {
    private ObjectMapper mapper;

    @Before
    public void setup() {
        mapper = new ObjectMapper();
    }

    @Test
    public void testEventTypeSerialization() throws Exception {
        Event event = new NotificationEvent();
        String value = mapper.writeValueAsString(event);
        assertTrue(value.contains("\"objectType\":\"notification\""));

        event = new TextEvent();
        value = mapper.writeValueAsString(event);
        assertTrue(value.contains("\"objectType\":\"text\""));
    }

    @Test
    public void testEventTypeDeserialization() throws Exception {
        String eventString = "{\"objectType\":\"text\",\"name\":\"test\",\"threshold\":0.0,\"eventType\":0,\"text\":\"Testtext\"}";
        Event event = mapper.readValue(eventString, Event.class);
        assertTrue(event.getClass() == TextEvent.class);
        assertFalse(event.getClass() == NotificationEvent.class);
    }
}
