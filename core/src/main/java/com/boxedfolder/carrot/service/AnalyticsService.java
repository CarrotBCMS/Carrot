/*
 * Carrot - beacon content management
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
