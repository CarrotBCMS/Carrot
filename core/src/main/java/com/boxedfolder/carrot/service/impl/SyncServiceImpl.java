package com.boxedfolder.carrot.service.impl;

import com.boxedfolder.carrot.domain.App;
import com.boxedfolder.carrot.exceptions.GeneralExceptions;
import com.boxedfolder.carrot.repository.AppRepository;
import com.boxedfolder.carrot.service.SyncService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
@Service
public class SyncServiceImpl implements SyncService {
    private AppRepository appRepository;

    @Override
    public Map<String, Object> sync(Long timestamp, String appKey) {
        // First look for Application
        App app = appRepository.findByApplicationKey(UUID.fromString(appKey));
        if (app == null) {
            throw new GeneralExceptions.InvalidAppKey();
        }

        // Build result
        Map<String, Object> result = new HashMap<>();
        result.put("timestamp", System.currentTimeMillis() / 1000L);
        result.put("beacons", beaconMap());
        result.put("events", eventMap());

        return result;
    }

    private Map<String, Object> beaconMap() {
        return null;
    }

    private Map<String, Object> eventMap() {
        return null;
    }

    @Inject
    public void setAppRepository(AppRepository appRepository) {
        this.appRepository = appRepository;
    }
}
