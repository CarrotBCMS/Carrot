/*
 * Carrot - beacon management
 * Copyright (C) 2016 Heiko Dreyer
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

import com.boxedfolder.carrot.Application;
import com.boxedfolder.carrot.config.Profiles;
import com.boxedfolder.carrot.domain.App;
import com.boxedfolder.carrot.domain.Beacon;
import com.boxedfolder.carrot.domain.NotificationEvent;
import com.boxedfolder.carrot.domain.general.logs.EntityDeletionLog;
import com.boxedfolder.carrot.domain.general.logs.RemovedRelationshipLog;
import com.boxedfolder.carrot.repository.impl.TransactionLogRepositoryImpl;
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
public class TransactionLogRepositoryTest {
    @PersistenceContext
    private EntityManager entityManager;
    private TransactionLogRepositoryImpl logRepository;

    private RemovedRelationshipLog rLog;

    private DateTime prevDate;
    private App app;
    private Beacon beacon;
    private NotificationEvent event;

    @Before
    public void setup() {
        logRepository = new TransactionLogRepositoryImpl();
        logRepository.setEntityManager(entityManager);

        prevDate = DateTime.now().minus(1000);

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

        EntityDeletionLog deletionLog = new EntityDeletionLog();
        deletionLog.setType(event.getClass());
        deletionLog.setDateTime(new DateTime());
        deletionLog.setEntityId(event.getId());
        entityManager.persist(deletionLog);

        deletionLog = new EntityDeletionLog();
        deletionLog.setType(event.getClass());
        deletionLog.setDateTime(new DateTime());
        deletionLog.setEntityId(3L);
        entityManager.persist(deletionLog);

        RemovedRelationshipLog arLog = new RemovedRelationshipLog();
        arLog.setAppId(app.getId());
        arLog.setEventId(2L);
        arLog.setDateTime(new DateTime());
        entityManager.persist(arLog);

        arLog = new RemovedRelationshipLog();
        arLog.setAppId(app.getId());
        arLog.setEventId(3L);
        arLog.setDateTime(new DateTime());
        entityManager.persist(arLog);
        entityManager.flush();
    }

    @After
    public void teardown() {
        entityManager.clear();
    }

    @Test
    public void testSaveTransactionLog() {
        List<RemovedRelationshipLog> logs = logRepository.findAll(prevDate, app.getId());
        assertTrue(logs.size() == 2);
        RemovedRelationshipLog nLog = new RemovedRelationshipLog();
        nLog.setAppId(app.getId());
        nLog.setDateTime(new DateTime());
        nLog.setEventId(1L);
        logRepository.save(nLog);
        logs = logRepository.findAll(prevDate, app.getId());
        assertTrue(logs.size() == 3);
    }

    @Test
    public void testFindDeletedIDsByDateTimeAndClassTest() {
        List<Long> data = logRepository.findDeletedIDsByDateTimeAndClass(prevDate, event.getClass());
        assertTrue(data.size() == 2);
    }

    @Test
    public void testDeleteTransactionLog() {
        List<RemovedRelationshipLog> logs = logRepository.findAll(prevDate, app.getId());
        assertTrue(logs.size() == 2);
        logRepository.delete(logs.get(0));
        logs = logRepository.findAll(prevDate, app.getId());
        assertTrue(logs.size() == 1);
    }

    @Test
    public void testFindOneById() {
        List<RemovedRelationshipLog> logs = logRepository.findAll(prevDate, app.getId());
        RemovedRelationshipLog log = logRepository.findOne(logs.get(0).getId());
        assertTrue(log != null);
    }

    @Test
    public void testFineOneEventApp() {
        RemovedRelationshipLog log = logRepository.findOne(2L, app.getId());
        assertTrue(log != null);
    }

    @Test
    public void testFindAll() {
        List<RemovedRelationshipLog> all = logRepository.findAll(prevDate, app.getId());
        assertTrue(all.size() == 2);
    }
}
