package com.boxedfolder.domain;

import com.boxedfolder.carrot.Application;
import com.boxedfolder.carrot.config.Profiles;
import com.boxedfolder.carrot.domain.App;
import com.boxedfolder.carrot.domain.Beacon;
import com.boxedfolder.carrot.domain.analytics.impl.AnalyticsAggregatorImpl;
import com.boxedfolder.carrot.domain.event.NotificationEvent;
import com.boxedfolder.carrot.domain.event.TextEvent;
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
import javax.xml.soap.Text;
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

    @Before
    public void setup() {
        // Setup test data
        App app = new App();
        app.setName("Testapp");
        app.setId(0L);
        app.setApplicationKey(UUID.fromString("550e8400-e29b-11d4-a716-446655440001"));
        entityManager.merge(app);

        App secondApp = new App();
        secondApp.setName("Testapp 2");
        secondApp.setId(22L);
        secondApp.setApplicationKey(UUID.fromString("550e8400-e29b-11d4-a716-446655440000"));
        entityManager.merge(secondApp);

        Beacon beacon = new Beacon();
        beacon.setName("Testbeacon");
        beacon.setId(22L);
        beacon.setUuid(UUID.fromString("550e8400-e29b-11d4-a716-446655440002"));
        beacon.setMajor(1);
        beacon.setMinor(2);
        entityManager.merge(beacon);

        NotificationEvent event = new NotificationEvent();
        event.setName("Testevent");
        event.setId(25L);
        event.setMessage("test");
        event.setTitle("testtitle");
        entityManager.merge(event);

        TextEvent secondEvent = new TextEvent();
        secondEvent.setName("Testevent 2");
        secondEvent.setId(25L);
        secondEvent.setText("test");
        entityManager.merge(secondEvent);

        analyticsAggregator = new AnalyticsAggregatorImpl();
        analyticsAggregator.setEntityManager(entityManager);
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

    @After
    public void tearDown() {
        entityManager.clear();
    }
}
