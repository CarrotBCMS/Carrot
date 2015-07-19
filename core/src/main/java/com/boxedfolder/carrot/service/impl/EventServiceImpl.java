package com.boxedfolder.carrot.service.impl;

import com.boxedfolder.carrot.domain.App;
import com.boxedfolder.carrot.domain.Event;
import com.boxedfolder.carrot.domain.general.logs.RemovedRelationshipLog;
import com.boxedfolder.carrot.repository.AuditLogRepository;
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
    private AuditLogRepository logRepository;

    @Override
    public Event save(Event object) {
        Event oldObject = repository.findOne(object.getId());

        if (oldObject != null) {
            // Check if there are event-beacon relationship changes
            List<App> difference = new ArrayList<>(oldObject.getApps());
            difference.removeAll(object.getApps());

            for (App app : difference) {
                RemovedRelationshipLog log = logRepository.findOne(oldObject.getId(), app.getId());
                if (log == null) {
                    log = new RemovedRelationshipLog();
                    log.setAppId(app.getId());
                    log.setEventId(oldObject.getId());
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
    public void setLogRepository(AuditLogRepository logRepository) {
        this.logRepository = logRepository;
    }
}
