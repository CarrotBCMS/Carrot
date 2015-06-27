package com.boxedfolder.carrot.service.impl;

import com.boxedfolder.carrot.repository.AnalyticsLogRepository;
import com.boxedfolder.carrot.repository.AppRepository;
import com.boxedfolder.carrot.repository.BeaconRepository;
import com.boxedfolder.carrot.repository.EventRepository;
import com.boxedfolder.carrot.service.AnalyticsService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
@Service
public class AnalyticsServiceImpl implements AnalyticsService {
    private AnalyticsLogRepository analyticsLogRepository;
    private AppRepository appRepository;
    private BeaconRepository beaconRepository;
    private EventRepository eventRepository;

    @Override
    public long countBeacons() {
        return beaconRepository.count();
    }

    @Override
    public long countApps() {
        return appRepository.count();
    }

    @Override
    public long countEvents() {
        return eventRepository.count();
    }

    @Inject
    public void setAnalyticsLogRepository(AnalyticsLogRepository analyticsLogRepository) {
        this.analyticsLogRepository = analyticsLogRepository;
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
}
