package com.boxedfolder.carrot.repository.impl;

import com.boxedfolder.carrot.domain.analytics.AnalyticsLog;
import com.boxedfolder.carrot.domain.general.logs.AuditLog;
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

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
