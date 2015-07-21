package com.boxedfolder.carrot.repository.impl;

import com.boxedfolder.carrot.domain.general.logs.TransactionLog;
import com.boxedfolder.carrot.domain.general.logs.RemovedRelationshipLog;
import com.boxedfolder.carrot.repository.TransactionLogRepository;
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
public class TransactionLogRepositoryImpl implements TransactionLogRepository {
    private EntityManager entityManager;

    @Override
    public List<Long> findDeletedIDsByDateTimeAndClass(DateTime dateTime, Class clazz) {
        return entityManager.createQuery("SELECT l.entityId FROM EntityDeletionLog l WHERE l.dateTime > :dateTime AND " +
                "l.type = :class", Long.class)
                            .setParameter("dateTime", dateTime)
                            .setParameter("class", clazz)
                            .getResultList();
    }

    @Override
    public TransactionLog save(TransactionLog log) {
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
    public List<RemovedRelationshipLog> findAll(DateTime dateTime, Long appId) {
        return entityManager.createQuery("SELECT l FROM RemovedRelationshipLog l WHERE l.appId = :appId AND " +
                        "l.dateTime > :dateTime",
                RemovedRelationshipLog.class)
                            .setParameter("appId", appId)
                            .setParameter("dateTime", dateTime)
                            .getResultList();
    }

    @Override
    public void delete(TransactionLog log) {
        TransactionLog aLog = findOne(log.getId());
        entityManager.remove(aLog);
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
