package com.boxedfolder.carrot.domain.general.logs;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
@Table(name = "removed_relationship_log")
@Entity
public class RemovedRelationshipLog extends AuditLog {
    @Column(name = "beacon_id")
    private Long beaconId;

    @Column(name = "event_id")
    private Long eventId;

    public Long getBeaconId() {
        return beaconId;
    }

    public void setBeaconId(Long beaconId) {
        this.beaconId = beaconId;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }
}
