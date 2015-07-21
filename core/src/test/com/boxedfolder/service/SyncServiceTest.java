package com.boxedfolder.service;

import com.boxedfolder.carrot.domain.App;
import com.boxedfolder.carrot.domain.Beacon;
import com.boxedfolder.carrot.domain.Event;
import com.boxedfolder.carrot.domain.NotificationEvent;
import com.boxedfolder.carrot.repository.AppRepository;
import com.boxedfolder.carrot.repository.BeaconRepository;
import com.boxedfolder.carrot.repository.EventRepository;
import com.boxedfolder.carrot.repository.TransactionLogRepository;
import com.boxedfolder.carrot.service.impl.SyncServiceImpl;
import com.boxedfolder.carrot.web.sync.SyncResource;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.*;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
@RunWith(MockitoJUnitRunner.class)
public class SyncServiceTest {
    private Map<String, Object> testData;
    private Long timestamp;
    private App app;
    private Beacon beacon;
    private NotificationEvent event;
    private SyncServiceImpl syncService;

    @Mock
    private BeaconRepository beaconRepository;
    @Mock
    private AppRepository appRepository;
    @Mock
    private EventRepository eventRepository;
    @Mock
    private TransactionLogRepository logRepository;

    @Before
    public void setup() {
        syncService = new SyncServiceImpl();
        syncService.setAppRepository(appRepository);
        syncService.setBeaconRepository(beaconRepository);
        syncService.setEventRepository(eventRepository);
        syncService.setLogRepository(logRepository);

        testData = new HashMap<>();
        app = new App();
        app.setName("Testapp");
        app.setId(5L);
        app.setDateCreated(new DateTime());
        app.setDateUpdated(new DateTime());
        app.setApplicationKey(UUID.randomUUID());

        event = new NotificationEvent();
        event.setName("Event 1");
        event.setId(1L);
        event.setMessage("Test");
        event.setTitle("test");
        event.setDateUpdated(new DateTime());
        event.setDateCreated(new DateTime());
        event.getApps().add(app);

        beacon = new Beacon();
        beacon.setName("Beacon");
        beacon.setId(1L);
        beacon.setMajor(2);
        beacon.setMinor(1);
        beacon.setUuid(UUID.randomUUID());
        beacon.setDateUpdated(new DateTime());
        beacon.setDateCreated(new DateTime());

        timestamp = 0L;
        testData.put("timestamp", System.currentTimeMillis() / 1000L);

        Map<String, Object> beaconResult = new HashMap<>();
        beaconResult.put("createdOrUpdated", Arrays.asList(beacon));

        // First sync, add empty list
        beaconResult.put("deleted", new ArrayList<>());

        testData.put("beacons", beaconResult);

        Map<String, Object> eventResult = new HashMap<>();
        eventResult.put("createdOrUpdated", Arrays.asList(event));
        eventResult.put("deleted", new ArrayList<>());
        testData.put("events", eventResult);
    }

    @Test
    public void testSync() {
        when(appRepository.findByApplicationKey((UUID)anyObject())).thenReturn(app);
        when(beaconRepository.findByDateUpdated((DateTime)anyObject())).thenReturn(Arrays.asList(beacon));
        when(logRepository.findDeletedIDsByDateTimeAndClass((DateTime)anyObject(), (Class)anyObject())).thenReturn(new ArrayList<Long>());

        List<Event> eList = new ArrayList<>();
        eList.add(event);
        when(eventRepository.findByDateUpdated((DateTime)anyObject(), (App)anyObject())).thenReturn(eList);
        Map<String, Object> result = syncService.sync(timestamp, app.getApplicationKey().toString());
        assertEquals(result, testData);
    }
}
