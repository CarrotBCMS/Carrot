package com.boxedfolder.carrot.domain.analytics;

import org.springframework.stereotype.Repository;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
public interface AnalyticsAggregator {
    long countBeacons();
    long countEvents();
    long countApps();
}
