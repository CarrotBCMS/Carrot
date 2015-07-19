package com.boxedfolder.carrot.service.impl;

import com.boxedfolder.carrot.domain.Beacon;
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
            List<Beacon> difference = new ArrayList<>(oldObject.getBeacons());
            difference.removeAll(object.getBeacons());

            for (Beacon beacon : difference) {
                RemovedRelationshipLog log = logRepository.findOne(oldObject.getId(), beacon.getId());
                if (log == null) {
                    log = new RemovedRelationshipLog();
                    log.setBeaconId(beacon.getId());
                    log.setEventId(oldObject.getId());
                }
                log.setDateTime(new DateTime());
                logRepository.save(log);
            }

            // Remove all obsolete logs
            difference = new ArrayList<>(object.getBeacons());
            difference.removeAll(oldObject.getBeacons());

            for (Beacon beacon : difference) {
                RemovedRelationshipLog log = logRepository.findOne(object.getId(), beacon.getId());
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
