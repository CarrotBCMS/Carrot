package com.boxedfolder.domain;

import com.boxedfolder.carrot.Application;
import com.boxedfolder.carrot.config.Profiles;
import com.boxedfolder.carrot.domain.App;
import com.boxedfolder.carrot.domain.Beacon;
import com.boxedfolder.carrot.domain.analytics.AnalyticsLog;
import com.boxedfolder.carrot.domain.event.NotificationEvent;
import com.boxedfolder.carrot.domain.event.TextEvent;
import com.boxedfolder.carrot.repository.impl.AnalyticsLogRepositoryImpl;
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

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(Profiles.TESTING)
@SpringApplicationConfiguration(classes = {Application.class})
@Transactional
public class AnalyticsRepositoryImplTest {
    @PersistenceContext
    private EntityManager entityManager;
    private AnalyticsLogRepositoryImpl analyticsRepository;

    private App app;
    private Beacon beacon;
    private NotificationEvent event;

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

        event = new NotificationEvent();
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
        entityManager.persist(log);

        AnalyticsLog secondLog = new AnalyticsLog();
        secondLog.setDateCreated(new DateTime());
        secondLog.setDateUpdated(new DateTime());
        secondLog.setBeacon(beacon);
        secondLog.setOccuredEvent(secondEvent);
        entityManager.persist(secondLog);
        entityManager.flush();

        analyticsRepository = new AnalyticsLogRepositoryImpl();
        analyticsRepository.setEntityManager(entityManager);
    }

    @After
    public void tearDown() {
        entityManager.clear();
    }

    @Test
    public void testSaveLog() {
        AnalyticsLog log = new AnalyticsLog();
        log.setDateUpdated(new DateTime());
        log.setDateCreated(new DateTime());
        log.setOccuredEvent(event);
        log.setBeacon(beacon);
        assertTrue(analyticsRepository.findAll().size() == 2);
        log = analyticsRepository.save(log);
        assertTrue(analyticsRepository.findAll().size() == 3);
        assertNotNull(log);
    }

    @Test
    public void testFindAll() {
        List<AnalyticsLog> logs = analyticsRepository.findAll();
        assertTrue(logs.size() == 2);
    }

    @Test
    public void testFindAllFromBeacon() {
        List<AnalyticsLog> logs = analyticsRepository.findAll(beacon);
        assertTrue(logs.size() == 2);
    }

    @Test
    public void testFindAllFromApp() {
        List<AnalyticsLog> logs = analyticsRepository.findAll(app);
        assertTrue(logs.size() == 2);
    }

    @Test
    public void testFindAllFromEvent() {
        List<AnalyticsLog> logs = analyticsRepository.findAll(event);
        assertTrue(logs.size() == 1);
    }

    @Test
    public void testCountLogsForBeacon() {
        Long aLong = analyticsRepository.count(beacon);
        assertTrue(aLong == 2);
    }

    @Test
    public void testCountLogsForApp() {
        Long aLong = analyticsRepository.count(app);
        assertTrue(aLong == 2);
    }

    @Test
    public void testFindAllFromTo() {
        DateTime to = new DateTime();
        DateTime from = new DateTime().minusDays(4);
        List<AnalyticsLog> logs = analyticsRepository.findAll(from, to);
        assertTrue(logs.size() == 2);
    }
}
