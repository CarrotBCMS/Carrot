package com.boxedfolder.carrot.repository;

import com.boxedfolder.carrot.domain.general.logs.TransactionLog;
import com.boxedfolder.carrot.domain.general.logs.RemovedRelationshipLog;
import org.joda.time.DateTime;

import java.util.List;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
public interface AuditLogRepository {
    List<Long> findDeletedIDsByDateTimeAndClass(DateTime dateTime, Class clazz);
    TransactionLog save(TransactionLog log);
    void delete(TransactionLog log);

    RemovedRelationshipLog findOne(Long id);
    RemovedRelationshipLog findOne(Long eventId, Long appId);
    List<RemovedRelationshipLog> findAll(DateTime dateTime, Long appId);
}
