package com.boxedfolder.carrot.repository;

import com.boxedfolder.carrot.domain.general.logs.AuditLog;
import com.boxedfolder.carrot.domain.general.logs.RemovedRelationshipLog;
import org.joda.time.DateTime;

import java.util.List;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
public interface AuditLogRepository {
    List<Long> findDeletedIDsByDateTimeAndClass(DateTime dateTime, Class clazz);
    AuditLog save(AuditLog log);
    void delete(AuditLog log);

    RemovedRelationshipLog findOne(Long id);
    RemovedRelationshipLog findOne(Long eventId, Long beaconId);
}
