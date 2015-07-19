package com.boxedfolder.carrot.repository.impl;

import com.boxedfolder.carrot.domain.general.logs.AuditLog;
import com.boxedfolder.carrot.domain.general.logs.RemovedRelationshipLog;
import com.boxedfolder.carrot.repository.AuditLogRepository;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
@Repository
@Transactional
public class AudigLogRepositoryImpl implements AuditLogRepository {
    private EntityManager entityManager;

    @Override
    public List<Long> findDeletedIDsByDateTimeAndClass(DateTime dateTime, Class clazz) {
        return entityManager.createQuery("SELECT l.entityId FROM EntityDeletionLog l WHERE l.dateTime > :dateTime AND l.type = :class", Long.class)
                            .setParameter("dateTime", dateTime)
                            .setParameter("class", clazz)
                            .getResultList();
    }

    @Override
    public AuditLog save(AuditLog log) {
        return entityManager.merge(log);
    }

    @Override
    public RemovedRelationshipLog findOne(Long id) {
        return entityManager.find(RemovedRelationshipLog.class, id);
    }

    @Override
    public RemovedRelationshipLog findOne(Long eventId, Long appId) {
        List<RemovedRelationshipLog> results = entityManager.createQuery("SELECT l FROM RemovedRelationshipLog " +
                "l WHERE l.eventId = :eventId AND l.appId = :appId", RemovedRelationshipLog.class)
                                                            .setParameter("eventId", eventId)
                                                            .setParameter("appId", appId)
                                                            .getResultList();
        RemovedRelationshipLog log = null;
        if (!results.isEmpty()) {
            log = results.get(0);
        }
        return log;
    }

    @Override
    public List<RemovedRelationshipLog> findAll(Long appId) {
        return entityManager.createQuery("SELECT l FROM RemovedRelationshipLog l WHERE l.appId = :appId",
                RemovedRelationshipLog.class)
                            .setParameter("appId", appId).getResultList();
    }

    @Override
    public void delete(AuditLog log) {
        AuditLog aLog = findOne(log.getId());
        entityManager.remove(aLog);
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
