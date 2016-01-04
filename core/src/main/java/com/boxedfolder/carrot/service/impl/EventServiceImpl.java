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
import com.boxedfolder.carrot.domain.general.logs.RemovedRelationshipLog;
import com.boxedfolder.carrot.repository.TransactionLogRepository;
import com.boxedfolder.carrot.repository.EventRepository;
import com.boxedfolder.carrot.service.EventService;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
@Service
public class EventServiceImpl extends CrudServiceImpl<Event, EventRepository> implements EventService {
    private TransactionLogRepository logRepository;

    @Override
    public Event save(Event object) {
        Event oldObject = null;
        if (object.getId() != null) {
            oldObject = repository.findOne(object.getId());
        }

        if (oldObject != null) {
            // Check if there are event-app relationship changes
            List<App> difference = new ArrayList<>(oldObject.getApps());
            difference.removeAll(object.getApps());

            for (App app : difference) {
                RemovedRelationshipLog log = logRepository.findOne(oldObject.getId(), app.getId());
                if (log == null) {
                    log = new RemovedRelationshipLog();
                    log.setAppId(app.getId());
                    log.setEventId(oldObject.getId());
                    log.setUserId(app.getUser().getId());
                }
                log.setDateTime(new DateTime());
                logRepository.save(log);
            }

            // Remove all obsolete logs
            difference = new ArrayList<>(object.getApps());
            difference.removeAll(oldObject.getApps());

            for (App app : difference) {
                RemovedRelationshipLog log = logRepository.findOne(object.getId(), app.getId());
                if (log != null) {
                    logRepository.delete(log);
                }
            }
        }

        return super.save(object);
    }

    @Inject
    public void setLogRepository(TransactionLogRepository logRepository) {
        this.logRepository = logRepository;
    }
}
