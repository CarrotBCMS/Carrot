package com.boxedfolder.carrot.service.impl;

import com.boxedfolder.carrot.domain.App;
import com.boxedfolder.carrot.domain.Beacon;
import com.boxedfolder.carrot.domain.analytics.AnalyticsLog;
import com.boxedfolder.carrot.domain.event.Event;
import com.boxedfolder.carrot.repository.AnalyticsLogRepository;
import com.boxedfolder.carrot.repository.AppRepository;
import com.boxedfolder.carrot.repository.BeaconRepository;
import com.boxedfolder.carrot.repository.EventRepository;
import com.boxedfolder.carrot.service.AnalyticsService;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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

    @Override
    public List<AnalyticsLog> findAll() {
        return analyticsLogRepository.findAll();
    }

    @Override
    public List<AnalyticsLog> findAll(DateTime from, DateTime to) {
        // Defaults to weekly
        if (from == null || to == null) {
            from = new DateTime();
            from = from.withDayOfWeek(1)
                       .withHourOfDay(0)
                       .withMinuteOfHour(0)
                       .withSecondOfMinute(0);

            to = new DateTime();
            to = to.withDayOfWeek(1)
                   .plusWeeks(1)
                   .withHourOfDay(0)
                   .withMinuteOfHour(0)
                   .withSecondOfMinute(0);
        }

        return analyticsLogRepository.findAll(from, to);
    }

    @Override
    public Map<App, Integer> appsTriggered(DateTime from, DateTime to) {
        Map<App, Integer> output = new TreeMap<App, Integer>();
        List<AnalyticsLog> logs = findAll(from, to);
        for (AnalyticsLog log : logs) {
            for (App app : log.getOccuredEvent().getApps()) {
                if (!output.containsKey(app)) {
                    output.put(app, 0);
                }
                Integer value = output.get(app);
                value++;
                output.put(app, value);
            }
        }

        return output;
    }

    @Override
    public Map<Beacon, Integer> beaconsTriggered(DateTime from, DateTime to) {
        Map<Beacon, Integer> output = new TreeMap<Beacon, Integer>();
        List<AnalyticsLog> logs = findAll(from, to);
        for (AnalyticsLog log : logs) {
            for (Beacon beacon : log.getOccuredEvent().getBeacons()) {
                if (!output.containsKey(beacon)) {
                    output.put(beacon, 0);
                }
                Integer value = output.get(beacon);
                value++;
                output.put(beacon, value);
            }
        }

        return output;
    }

    @Override
    public Map<Event, Integer> eventsTriggered(DateTime from, DateTime to) {
        Map<Event, Integer> output = new TreeMap<Event, Integer>();
        List<AnalyticsLog> logs = findAll(from, to);
        for (AnalyticsLog log : logs) {
            Event event = log.getOccuredEvent();
            if (!output.containsKey(event)) {
                output.put(event, 0);
            }
            Integer value = output.get(event);
            value++;
            output.put(event, value);
        }

        return output;
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
