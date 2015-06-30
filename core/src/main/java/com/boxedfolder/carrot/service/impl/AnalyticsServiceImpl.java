package com.boxedfolder.carrot.service.impl;

import com.boxedfolder.carrot.domain.App;
import com.boxedfolder.carrot.domain.Beacon;
import com.boxedfolder.carrot.domain.analytics.AnalyticsLog;
import com.boxedfolder.carrot.domain.analytics.AnalyticsTransfer;
import com.boxedfolder.carrot.domain.event.Event;
import com.boxedfolder.carrot.repository.AnalyticsLogRepository;
import com.boxedfolder.carrot.repository.AppRepository;
import com.boxedfolder.carrot.repository.BeaconRepository;
import com.boxedfolder.carrot.repository.EventRepository;
import com.boxedfolder.carrot.service.AnalyticsService;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

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
    public List<AnalyticsTransfer> appsTriggered(DateTime from, DateTime to) {
        List<AnalyticsTransfer> output = new ArrayList<AnalyticsTransfer>();
        List<AnalyticsLog> logs = findAll(from, to);
        for (AnalyticsLog log : logs) {
            for (App app : log.getOccuredEvent().getApps()) {
                AnalyticsTransfer transfer = new AnalyticsTransfer();
                transfer.setId(app.getId());
                transfer.setName(app.getName());
                if (!output.contains(transfer)) {
                    output.add(transfer);
                } else {
                    transfer = output.get(output.indexOf(transfer));
                }
                Integer value = transfer.getCount();
                value++;
                transfer.setCount(value);

            }
        }

        return output;
    }

    @Override
    public List<AnalyticsTransfer> beaconsTriggered(DateTime from, DateTime to) {
        List<AnalyticsTransfer> output = new ArrayList<AnalyticsTransfer>();
        List<AnalyticsLog> logs = findAll(from, to);
        for (AnalyticsLog log : logs) {
            for (Beacon beacon : log.getOccuredEvent().getBeacons()) {
                AnalyticsTransfer transfer = new AnalyticsTransfer();
                transfer.setId(beacon.getId());
                transfer.setName(beacon.getName());
                if (!output.contains(transfer)) {
                    output.add(transfer);
                } else {
                    transfer = output.get(output.indexOf(transfer));
                }
                Integer value = transfer.getCount();
                value++;
                transfer.setCount(value);
            }
        }

        return output;
    }

    @Override
    public List<AnalyticsTransfer> eventsTriggered(DateTime from, DateTime to) {
        List<AnalyticsTransfer> output = new ArrayList<AnalyticsTransfer>();
        List<AnalyticsLog> logs = findAll(from, to);
        for (AnalyticsLog log : logs) {
            Event event = log.getOccuredEvent();
            AnalyticsTransfer transfer = new AnalyticsTransfer();
            transfer.setId(event.getId());
            transfer.setName(event.getName());
            if (!output.contains(transfer)) {
                output.add(transfer);
            } else {
                transfer = output.get(output.indexOf(transfer));
            }
            Integer value = transfer.getCount();
            value++;
            transfer.setCount(value);
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
