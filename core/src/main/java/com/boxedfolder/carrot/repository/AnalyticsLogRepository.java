package com.boxedfolder.carrot.repository;

import com.boxedfolder.carrot.domain.App;
import com.boxedfolder.carrot.domain.Beacon;
import com.boxedfolder.carrot.domain.analytics.AnalyticsLog;
import com.boxedfolder.carrot.domain.event.Event;
import org.joda.time.DateTime;

import java.util.List;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
public interface AnalyticsLogRepository {
    List<AnalyticsLog> findAll();
    List<AnalyticsLog> findAll(Event event);
    List<AnalyticsLog> findAll(Beacon beacon);
    List<AnalyticsLog> findAll(App app);
    List<AnalyticsLog> findAll(DateTime from, DateTime to);

    long count();
    long count(Beacon beacon);
    long count(App app);
    long count(Event event);

    void save(AnalyticsLog log);
}
