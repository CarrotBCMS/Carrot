package com.boxedfolder.service;

import com.boxedfolder.carrot.domain.App;
import com.boxedfolder.carrot.domain.Beacon;
import com.boxedfolder.carrot.domain.analytics.AnalyticsLog;
import com.boxedfolder.carrot.domain.event.Event;
import com.boxedfolder.carrot.domain.event.NotificationEvent;
import com.boxedfolder.carrot.domain.event.TextEvent;
import com.boxedfolder.carrot.repository.AnalyticsLogRepository;
import com.boxedfolder.carrot.repository.AppRepository;
import com.boxedfolder.carrot.repository.BeaconRepository;
import com.boxedfolder.carrot.repository.EventRepository;
import com.boxedfolder.carrot.service.impl.AnalyticsServiceImpl;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
@RunWith(MockitoJUnitRunner.class)
public class AnalyticsServiceImplTest {
    private AnalyticsServiceImpl service;
    @Mock
    private AppRepository appRepository;
    @Mock
    private BeaconRepository beaconRepository;
    @Mock
    private EventRepository eventRepository;
    @Mock
    private AnalyticsLogRepository analyticsLogRepository;

    private List<App> testAppData;
    private List<Beacon> testBeaconData;
    private List<Event> testEventData;
    private List<AnalyticsLog> testAnalyticsLogData;

    @Before
    public void setUp() {
        service = new AnalyticsServiceImpl();
        service.setAnalyticsLogRepository(analyticsLogRepository);
        service.setAppRepository(appRepository);
        service.setBeaconRepository(beaconRepository);
        service.setEventRepository(eventRepository);

        testAppData = new ArrayList<App>();
        testBeaconData = new ArrayList<Beacon>();
        testEventData = new ArrayList<Event>();
        testAnalyticsLogData = new ArrayList<AnalyticsLog>();

        // Setup test data
        App app = new App();
        app.setDateCreated(new DateTime());
        app.setDateUpdated(new DateTime());
        app.setName("Testapp");
        app.setApplicationKey(UUID.fromString("550e8400-e29b-11d4-a716-446655440001"));
        testAppData.add(app);

        App secondApp = new App();
        secondApp.setDateCreated(new DateTime());
        secondApp.setName("Testapp 2");
        secondApp.setApplicationKey(UUID.fromString("550e8400-e29b-11d4-a716-446655440000"));
        testAppData.add(secondApp);

        Beacon beacon = new Beacon();
        beacon.setDateCreated(new DateTime());
        beacon.setDateUpdated(new DateTime());
        beacon.setName("Testbeacon");
        beacon.setUuid(UUID.fromString("550e8400-e29b-11d4-a716-446655440002"));
        beacon.setMajor(1);
        beacon.setMinor(2);
        testBeaconData.add(beacon);

        NotificationEvent event = new NotificationEvent();
        event.setDateCreated(new DateTime());
        event.setDateUpdated(new DateTime());
        event.setName("Testevent");
        event.setMessage("test");
        event.setTitle("testtitle");
        event.getApps().add(app);
        event.getBeacons().add(beacon);
        testEventData.add(event);

        TextEvent secondEvent = new TextEvent();
        secondEvent.setDateCreated(new DateTime());
        secondEvent.setDateUpdated(new DateTime());
        secondEvent.setName("Testevent 2");
        secondEvent.setText("test");
        secondEvent.getApps().add(app);
        secondEvent.getBeacons().add(beacon);
        testEventData.add(secondEvent);

        AnalyticsLog log = new AnalyticsLog();
        log.setDateCreated(new DateTime());
        log.setDateUpdated(new DateTime());
        log.setBeacon(beacon);
        log.setOccuredEvent(event);
        testAnalyticsLogData.add(log);

        AnalyticsLog secondLog = new AnalyticsLog();
        secondLog.setDateCreated(new DateTime());
        secondLog.setDateUpdated(new DateTime());
        secondLog.setBeacon(beacon);
        secondLog.setOccuredEvent(secondEvent);
        testAnalyticsLogData.add(secondLog);
    }

    @Test
    public void testCountBeacons() {
        when(beaconRepository.count()).thenReturn((long)testBeaconData.size());
        assertTrue(service.countBeacons() == 1);
    }

    @Test
    public void testCountApps() {
        when(appRepository.count()).thenReturn((long)testAppData.size());
        assertTrue(service.countApps() == 2);
    }

    @Test
    public void testCountEvents() {
        when(eventRepository.count()).thenReturn((long)testEventData.size());
        assertTrue(service.countEvents() == 2);
    }
}
