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

package com.boxedfolder.carrot.service.impl;

import com.boxedfolder.carrot.domain.App;
import com.boxedfolder.carrot.domain.Event;
import com.boxedfolder.carrot.domain.analytics.AnalyticsLog;
import com.boxedfolder.carrot.domain.analytics.AnalyticsTransfer;
import com.boxedfolder.carrot.exceptions.GeneralExceptions;
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
import java.util.UUID;

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
    public AnalyticsLog save(AnalyticsLog object, UUID appKey) {
        App app = appRepository.findByApplicationKey(appKey);
        if (app == null) {
            throw new GeneralExceptions.InvalidAppKey();
        }

        // Refresh objects
        object.setApp(app);
        object.setOccuredEvent(eventRepository.findOne(object.getOccuredEvent().getId()));
        object.setBeacon(beaconRepository.findOne(object.getBeacon().getId()));

        if (!isValid(object, app)) {
            throw new GeneralExceptions.InvalidLog();
        }

        return analyticsLogRepository.save(object);
    }

    @Override
    public List<AnalyticsTransfer> appsTriggered(DateTime from, DateTime to) {
        List<AnalyticsTransfer> output = new ArrayList<>();
        List<AnalyticsLog> logs = findAll(from, to);
        for (AnalyticsLog log : logs) {
            if (log.getApp() == null) {
                break;
            }
            AnalyticsTransfer transfer = new AnalyticsTransfer();
            transfer.setId(log.getApp().getId());
            transfer.setName(log.getApp().getName());
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

    @Override
    public List<AnalyticsTransfer> beaconsTriggered(DateTime from, DateTime to) {
        List<AnalyticsTransfer> output = new ArrayList<>();
        List<AnalyticsLog> logs = findAll(from, to);
        for (AnalyticsLog log : logs) {
            AnalyticsTransfer transfer = new AnalyticsTransfer();
            transfer.setId(log.getBeacon().getId());
            transfer.setName(log.getBeacon().getName());
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

    @Override
    public List<AnalyticsTransfer> eventsTriggered(DateTime from, DateTime to) {
        List<AnalyticsTransfer> output = new ArrayList<>();
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

    private boolean isValid(AnalyticsLog log, App app) {
        return log.getOccuredEvent().getApps().contains(app);
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
