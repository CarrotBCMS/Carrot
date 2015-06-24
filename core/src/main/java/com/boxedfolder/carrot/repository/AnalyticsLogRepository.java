package com.boxedfolder.carrot.repository;

import com.boxedfolder.carrot.domain.App;
import com.boxedfolder.carrot.domain.Beacon;
import com.boxedfolder.carrot.domain.analytics.AnalyticsLog;

import java.util.List;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
public interface AnalyticsLogRepository {
    void save(AnalyticsLog log);
    List<AnalyticsLog> findAll();
    List<AnalyticsLog> findAll(Beacon beacon);
    List<AnalyticsLog> findAll(App app);
}
