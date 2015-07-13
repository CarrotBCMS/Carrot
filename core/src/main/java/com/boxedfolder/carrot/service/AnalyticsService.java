package com.boxedfolder.carrot.service;

import com.boxedfolder.carrot.domain.analytics.AnalyticsLog;
import com.boxedfolder.carrot.domain.analytics.AnalyticsTransfer;
import org.joda.time.DateTime;

import java.util.List;
import java.util.UUID;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
public interface AnalyticsService {
    long countBeacons();
    long countApps();
    long countEvents();

    AnalyticsLog save(AnalyticsLog object, UUID appKey);

    List<AnalyticsLog> findAll();
    List<AnalyticsLog> findAll(DateTime from, DateTime to);

    List<AnalyticsTransfer> appsTriggered(DateTime from, DateTime to);
    List<AnalyticsTransfer> beaconsTriggered(DateTime from, DateTime to);
    List<AnalyticsTransfer> eventsTriggered(DateTime from, DateTime to);
}
