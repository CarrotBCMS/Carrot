package com.boxedfolder.carrot.domain.analytics.impl;

import com.boxedfolder.carrot.domain.App;
import com.boxedfolder.carrot.domain.Beacon;
import com.boxedfolder.carrot.domain.analytics.AnalyticsAggregator;
import com.boxedfolder.carrot.domain.event.Event;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.transaction.Transactional;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
@Repository
@Transactional
public class AnalyticsAggregatorImpl implements AnalyticsAggregator {
    @PersistenceContext
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

    private TypedQuery<Long> getCountQuery(Class clazz) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        query.select(builder.count(query.from(clazz)));
        return entityManager.createQuery(query);
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
