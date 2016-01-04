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

package com.boxedfolder.service;

import com.boxedfolder.carrot.domain.*;
import com.boxedfolder.carrot.repository.AppRepository;
import com.boxedfolder.carrot.repository.BeaconRepository;
import com.boxedfolder.carrot.repository.EventRepository;
import com.boxedfolder.carrot.repository.TransactionLogRepository;
import com.boxedfolder.carrot.service.impl.SyncServiceImpl;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.*;

import static junit.framework.Assert.assertEquals;;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;
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
    private User user;

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

        user = new User();
        user.setId(100L);
        user.setDateCreated(new DateTime());
        user.setDateUpdated(new DateTime());
        user.setEmail("test@test.com");

        testData = new HashMap<>();
        app = new App();
        app.setName("Testapp");
        app.setId(5L);
        app.setDateCreated(new DateTime());
        app.setDateUpdated(new DateTime());
        app.setApplicationKey(UUID.randomUUID());
        app.setUser(user);

        event = new NotificationEvent();
        event.setName("Event 1");
        event.setId(1L);
        event.setMessage("Test");
        event.setTitle("test");
        event.setDateUpdated(new DateTime());
        event.setDateCreated(new DateTime());
        event.setUser(user);
        event.getApps().add(app);

        beacon = new Beacon();
        beacon.setName("Beacon");
        beacon.setId(1L);
        beacon.setMajor(2);
        beacon.setMinor(1);
        beacon.setUuid(UUID.randomUUID());
        beacon.setDateUpdated(new DateTime());
        beacon.setDateCreated(new DateTime());
        beacon.setUser(user);

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
        app.setUser(user);
        when(appRepository.findByApplicationKey((UUID)anyObject())).thenReturn(app);
        when(beaconRepository.findByDateUpdatedAndUser((DateTime)anyObject(), (User)anyObject()))
                .thenReturn(Arrays.asList(beacon));
        when(logRepository.findDeletedIDsByDateTimeAndClass((DateTime)anyObject(), (Class)anyObject(),
                anyLong()))
                .thenReturn(new ArrayList<Long>());

        List<Event> eList = new ArrayList<>();
        eList.add(event);
        when(eventRepository.findByDateUpdated((DateTime)anyObject(), (App)anyObject())).thenReturn(eList);

        Map<String, Object> result = syncService.sync(timestamp, app.getApplicationKey().toString());
        assertEquals(result, testData);
    }
}
