package com.boxedfolder.carrot.domain.analytics.impl;

import com.boxedfolder.carrot.domain.App;
import com.boxedfolder.carrot.domain.Beacon;
import com.boxedfolder.carrot.domain.analytics.AnalyticsAggregator;
import com.boxedfolder.carrot.domain.analytics.AnalyticsLog;
import com.boxedfolder.carrot.domain.event.Event;
import com.boxedfolder.carrot.repository.AnalyticsLogRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.transaction.Transactional;
import java.util.List;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
@Repository
@Transactional
public class AnalyticsAggregatorImpl implements AnalyticsAggregator, AnalyticsLogRepository {
    private EntityManager entityManager;

    @Override
    public long countBeacons() {
        return getCountQuery(Beacon.class).getSingleResult();
    }

    @Override
    public long countEvents() {
        return getCountQuery(Event.class).getSingleResult();
    }

    @Override
    public long countApps() {
        return getCountQuery(App.class).getSingleResult();
    }

    @Override
    public long count() {
        return getCountQuery(AnalyticsLog.class).getSingleResult();
    }

    @Override
    public long count(Beacon beacon) {
        return entityManager.createQuery("SELECT COUNT(a) FROM AnalyticsLog a WHERE a.beacon = :beacon", Long.class)
                            .setParameter("beacon", beacon)
                            .getSingleResult();
    }

    @Override
    public long count(App app) {
        return entityManager.createQuery("SELECT COUNT(a) FROM AnalyticsLog a INNER JOIN a.occuredEvent.apps b WITH b = :app", Long.class)
                            .setParameter("app", app)
                            .getSingleResult();
    }

    @Override
    public long count(Event event) {
        return entityManager.createQuery("SELECT COUNT(a) FROM AnalyticsLog a WHERE a.occuredEvent = :event", Long.class)
                            .setParameter("event", event)
                            .getSingleResult();
    }

    @Override
    public void save(AnalyticsLog log) {
        entityManager.merge(log);
    }

    @Override
    public List<AnalyticsLog> findAll() {
        return entityManager.createQuery("SELECT a FROM AnalyticsLog a", AnalyticsLog.class)
                            .getResultList();
    }

    @Override
    public List<AnalyticsLog> findAll(Beacon beacon) {
        return entityManager.createQuery("SELECT a FROM AnalyticsLog a WHERE a.beacon = :beacon", AnalyticsLog.class)
                            .setParameter("beacon", beacon)
                            .getResultList();
    }

    @Override
    public List<AnalyticsLog> findAll(App app) {
        return entityManager.createQuery("SELECT a FROM AnalyticsLog a INNER JOIN a.occuredEvent.apps b WITH b = :app", AnalyticsLog.class)
                            .setParameter("app", app)
                            .getResultList();
    }

    @Override
    public List<AnalyticsLog> findAll(Event event) {
        return entityManager.createQuery("SELECT a FROM AnalyticsLog a WHERE a.occuredEvent = :event", AnalyticsLog.class)
                            .setParameter("event", event)
                            .getResultList();
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    private TypedQuery<Long> getCountQuery(Class clazz) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        query.select(builder.count(query.from(clazz)));
        return entityManager.createQuery(query);
    }
}
