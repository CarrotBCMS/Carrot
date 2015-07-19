package com.boxedfolder.carrot.domain.general.logs;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
@Table(name = "removed_relationship_log")
@Entity
public class RemovedRelationshipLog extends AuditLog {
    @Column(name = "app_id")
    private Long appId;

    @Column(name = "event_id")
    private Long eventId;

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public static List<Long> asEventList(List<RemovedRelationshipLog> logs) {
        List<Long> result = new ArrayList<>();
        for (RemovedRelationshipLog log : logs) {
            result.add(log.getEventId());
        }
        return result;
    }
}
