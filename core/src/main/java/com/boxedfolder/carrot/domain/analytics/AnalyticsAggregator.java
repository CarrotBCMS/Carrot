package com.boxedfolder.carrot.domain.analytics;

import com.boxedfolder.carrot.domain.App;
import com.boxedfolder.carrot.domain.Beacon;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
public interface AnalyticsAggregator {
    long countBeacons();
    long countEvents();
    long countApps();
}
