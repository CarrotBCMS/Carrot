package com.boxedfolder.domain;

import com.boxedfolder.carrot.Application;
import com.boxedfolder.carrot.config.Profiles;
import com.boxedfolder.carrot.domain.App;
import com.boxedfolder.carrot.domain.Beacon;
import com.boxedfolder.carrot.domain.analytics.AnalyticsLog;
import com.boxedfolder.carrot.domain.analytics.impl.AnalyticsAggregatorImpl;
import com.boxedfolder.carrot.domain.event.NotificationEvent;
import com.boxedfolder.carrot.domain.event.TextEvent;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

import static junit.framework.Assert.assertTrue;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(Profiles.TESTING)
@SpringApplicationConfiguration(classes = {Application.class})
@Transactional
public class AnalyticsAggregatorImplTest {
    @PersistenceContext
    private EntityManager entityManager;
    private AnalyticsAggregatorImpl analyticsAggregator;

    private App app;
    private Beacon beacon;

    @Before
    public void setup() {
        // Setup test data
        app = new App();
        app.setDateCreated(new DateTime());
        app.setDateUpdated(new DateTime());
        app.setName("Testapp");
        app.setApplicationKey(UUID.fromString("550e8400-e29b-11d4-a716-446655440001"));
        entityManager.persist(app);

        App secondApp = new App();
        secondApp.setDateCreated(new DateTime());
        secondApp.setName("Testapp 2");
        secondApp.setApplicationKey(UUID.fromString("550e8400-e29b-11d4-a716-446655440000"));
        entityManager.persist(secondApp);

        beacon = new Beacon();
        beacon.setDateCreated(new DateTime());
        beacon.setDateUpdated(new DateTime());
        beacon.setName("Testbeacon");
        beacon.setUuid(UUID.fromString("550e8400-e29b-11d4-a716-446655440002"));
        beacon.setMajor(1);
        beacon.setMinor(2);
        entityManager.persist(beacon);

        NotificationEvent event = new NotificationEvent();
        event.setDateCreated(new DateTime());
        event.setDateUpdated(new DateTime());
        event.setName("Testevent");
        event.setMessage("test");
        event.setTitle("testtitle");
        event.getApps().add(app);
        event.getBeacons().add(beacon);
        entityManager.persist(event);

        TextEvent secondEvent = new TextEvent();
        secondEvent.setDateCreated(new DateTime());
        secondEvent.setDateUpdated(new DateTime());
        secondEvent.setName("Testevent 2");
        secondEvent.setText("test");
        secondEvent.getApps().add(app);
        secondEvent.getBeacons().add(beacon);
        entityManager.persist(secondEvent);

        AnalyticsLog log = new AnalyticsLog();
        log.setDateCreated(new DateTime());
        log.setDateUpdated(new DateTime());
        log.setBeacon(beacon);
        log.setOccuredEvent(event);
        log.setDateTime(new DateTime());
        entityManager.persist(log);

        AnalyticsLog logTwo = new AnalyticsLog();
        logTwo.setDateCreated(new DateTime());
        logTwo.setDateUpdated(new DateTime());
        logTwo.setBeacon(beacon);
        logTwo.setOccuredEvent(secondEvent);
        logTwo.setDateTime(new DateTime());
        entityManager.persist(logTwo);
        entityManager.flush();

        analyticsAggregator = new AnalyticsAggregatorImpl();
        analyticsAggregator.setEntityManager(entityManager);
    }

    @After
    public void tearDown() {
        entityManager.clear();
    }

    @Test
    public void testCountApps() {
        Long aLong = analyticsAggregator.countApps();
        assertTrue(aLong == 2);
    }

    @Test
    public void testCountEvents() {
        Long aLong = analyticsAggregator.countEvents();
        assertTrue(aLong == 2);
    }

    @Test
    public void testCountBeacons() {
        Long aLong = analyticsAggregator.countBeacons();
        assertTrue(aLong == 1);
    }

    @Test
    public void testFindAll() {
        List<AnalyticsLog> logs = analyticsAggregator.findAll();
        assertTrue(logs.size() == 2);
    }

    @Test
    public void testFindAllFromBeacon() {
        List<AnalyticsLog> logs = analyticsAggregator.findAll(beacon);
        assertTrue(logs.size() == 2);
    }

    @Test
    public void testFindAllFromApp() {
        List<AnalyticsLog> logs = analyticsAggregator.findAll(app);
        assertTrue(logs.size() == 2);
    }
}
