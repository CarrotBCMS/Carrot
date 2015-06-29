package com.boxedfolder.carrot.service;

import com.boxedfolder.carrot.domain.analytics.AnalyticsLog;
import org.joda.time.DateTime;

import java.util.List;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
public interface AnalyticsService {
    long countBeacons();
    long countApps();
    long countEvents();

    List<AnalyticsLog> findAll();
    List<AnalyticsLog> findAll(DateTime from, DateTime to);
}
