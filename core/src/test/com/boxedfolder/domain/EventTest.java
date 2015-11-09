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
