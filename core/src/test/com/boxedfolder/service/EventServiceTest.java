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

package com.boxedfolder.service;

import com.boxedfolder.carrot.domain.Event;
import com.boxedfolder.carrot.domain.NotificationEvent;
import com.boxedfolder.carrot.domain.TextEvent;
import com.boxedfolder.carrot.repository.EventRepository;
import com.boxedfolder.carrot.service.impl.EventServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
@RunWith(MockitoJUnitRunner.class)
public class EventServiceTest {
    @Mock
    private EventRepository repository;
    private EventServiceImpl service;
    private List<Event> testData;

    @Before
    public void setUp() {
        service = new EventServiceImpl();
        service.setRepository(repository);
        testData = new ArrayList<>();

        // Create 3 different events
        Event event = new TextEvent();
        event.setName("Event 1");
        testData.add(event);

        event = new NotificationEvent();
        event.setName("Event 2");
        testData.add(event);

        event = new TextEvent();
        event.setName("Event 3");
        testData.add(event);
    }

    @Test
    public void testRetrieveAllEvents() {
        when(repository.findAllByOrderByDateCreatedDesc()).thenReturn(testData);
        List<Event> events = service.findAll();
        assertEquals(events, testData);
    }

    @Test
    public void testRetrieveSingleEvent() {
        when(repository.findOne(1L)).thenReturn(testData.get(0));
        Event event = service.find(1L);
        assertEquals(event, testData.get(0));
    }

    @Test
    public void testSaveEvent() {
        when(repository.save(testData.get(0))).thenReturn(testData.get(0));
        Event event = service.save(testData.get(0));
        assertEquals(event, testData.get(0));
    }
}
