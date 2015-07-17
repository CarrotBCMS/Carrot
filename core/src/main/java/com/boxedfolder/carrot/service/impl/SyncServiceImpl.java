package com.boxedfolder.carrot.service.impl;

import com.boxedfolder.carrot.domain.*;
import com.boxedfolder.carrot.exceptions.GeneralExceptions;
import com.boxedfolder.carrot.repository.AppRepository;
import com.boxedfolder.carrot.repository.BeaconRepository;
import com.boxedfolder.carrot.repository.EntityDeletionLogRepository;
import com.boxedfolder.carrot.repository.EventRepository;
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
    private EntityDeletionLogRepository logRepository;

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
        result.put("timestamp", System.currentTimeMillis() / 1000L);
        result.put("beacons", beaconMap(timestamp, app));
        result.put("events", eventMap(timestamp, app));

        return result;
    }

    private Map<String, Object> beaconMap(Long timestamp, App app) {
        DateTime dateTime = new DateTime(timestamp * 1000L);
        Map<String, Object> result = new HashMap<>();
        result.put("createdOrUpdated", beaconRepository.findByDateUpdated(dateTime, app));

        // First sync, add empty list
        result.put("deleted", timestamp > 0 ?
                logRepository.findDeletedIDsByDateTimeAndClass(dateTime, Beacon.class) : new ArrayList<>());

        return result;
    }

    private Map<String, Object> eventMap(Long timestamp, App app) {
        DateTime dateTime = new DateTime(timestamp * 1000L);
        Map<String, Object> result = new HashMap<>();
        result.put("createdOrUpdated", eventRepository.findByDateUpdated(dateTime, app));

        // Add all possible event classes
        List<Long> deletedEvents = logRepository.findDeletedIDsByDateTimeAndClass(dateTime, Event.class);
        deletedEvents.addAll(logRepository.findDeletedIDsByDateTimeAndClass(dateTime, TextEvent.class));
        deletedEvents.addAll(logRepository.findDeletedIDsByDateTimeAndClass(dateTime, NotificationEvent.class));

        // First sync?! Add empty list...
        result.put("deleted", timestamp > 0 ? deletedEvents : new ArrayList<>());

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
    public void setLogRepository(EntityDeletionLogRepository logRepository) {
        this.logRepository = logRepository;
    }
}
