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

package com.boxedfolder.carrot.service.impl;

import com.boxedfolder.carrot.domain.*;
import com.boxedfolder.carrot.domain.general.logs.RemovedRelationshipLog;
import com.boxedfolder.carrot.domain.util.EventList;
import com.boxedfolder.carrot.exceptions.GeneralExceptions;
import com.boxedfolder.carrot.repository.AppRepository;
import com.boxedfolder.carrot.repository.BeaconRepository;
import com.boxedfolder.carrot.repository.EventRepository;
import com.boxedfolder.carrot.repository.TransactionLogRepository;
import com.boxedfolder.carrot.service.SyncService;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.*;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
@Service
public class SyncServiceImpl implements SyncService {
    private AppRepository appRepository;
    private BeaconRepository beaconRepository;
    private EventRepository eventRepository;
    private TransactionLogRepository logRepository;

    @Override
    public Map<String, Object> sync(Long timestamp, String appKey) {
        if (timestamp == null) {
            timestamp = 0L;
        }

        // First look for the concrete application
        App app = appRepository.findByApplicationKey(UUID.fromString(appKey));
        if (app == null) {
            throw new GeneralExceptions.InvalidAppKey();
        }

        // Build result
        Map<String, Object> result = new HashMap<>();
        result.put(SyncService.Keys.TIMESTAMP_KEY, System.currentTimeMillis() / 1000L);
        result.put(SyncService.Keys.BEACONS_KEY, beaconMap(timestamp, app));
        result.put(SyncService.Keys.EVENTS_KEY, eventMap(timestamp, app));

        return result;
    }

    private Map<String, Object> beaconMap(Long timestamp, App app) {
        DateTime dateTime = new DateTime(timestamp * 1000L);
        Map<String, Object> result = new HashMap<>();
        result.put(SyncService.Keys.CREATE_UPDATE_KEY, beaconRepository.findByDateUpdatedAndUser(dateTime,
                app.getUser()));

        // First sync, add empty list
        result.put(SyncService.Keys.DELETED_KEY, timestamp > 0 ?
                logRepository.findDeletedIDsByDateTimeAndClass(dateTime, Beacon.class,
                        app.getUser().getId()) : new ArrayList<>());

        return result;
    }

    private Map<String, Object> eventMap(Long timestamp, App app) {
        DateTime dateTime = new DateTime(timestamp * 1000L);
        Map<String, Object> result = new HashMap<>();
        EventList eventList = new EventList();
        eventList.addAll(eventRepository.findByDateUpdated(dateTime, app));
        result.put(SyncService.Keys.CREATE_UPDATE_KEY, eventList);

        // First sync? Add empty list...
        List<Long> deletedEvents = new ArrayList<>();
        if (timestamp > 0L) {
            // Add all possible event classes
            Long userId = app.getUser().getId();
            deletedEvents.addAll(logRepository.findDeletedIDsByDateTimeAndClass(dateTime, Event.class, userId));
            deletedEvents.addAll(logRepository.findDeletedIDsByDateTimeAndClass(dateTime, TextEvent.class, userId));
            deletedEvents.addAll(logRepository.findDeletedIDsByDateTimeAndClass(dateTime, NotificationEvent.class, userId));

            // Check if there is an event with dangling connections
            List<RemovedRelationshipLog> logs = logRepository.findAll(dateTime, app.getId());
            deletedEvents.addAll(RemovedRelationshipLog.asEventList(logs));
        }

        result.put(SyncService.Keys.DELETED_KEY, deletedEvents);

        return result;
    }

    @Inject
    public void setAppRepository(AppRepository appRepository) {
        this.appRepository = appRepository;
    }

    @Inject
    public void setBeaconRepository(BeaconRepository beaconRepository) {
        this.beaconRepository = beaconRepository;
    }

    @Inject
    public void setEventRepository(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Inject
    public void setLogRepository(TransactionLogRepository logRepository) {
        this.logRepository = logRepository;
    }
}
