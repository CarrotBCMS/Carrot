package com.boxedfolder.service;

import com.boxedfolder.carrot.domain.event.Event;
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
public class EventServiceImplTest {
    @Mock
    private EventRepository repository;
    private EventServiceImpl service;
    private List<Event> testData;

    @Before
    public void setUp() {
        service = new EventServiceImpl();
        service.setRepository(repository);
        testData = new ArrayList<Event>();

        // Create 3 different events
        Event event = new Event();
        event.setName("Event 1");
        testData.add(event);

        event = new Event();
        event.setName("Event 2");
        testData.add(event);

        event = new Event();
        event.setName("Event 3");
        testData.add(event);
    }

    @Test
    public void testRetrieveAllEvents() {
        when(repository.findAll()).thenReturn(testData);
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