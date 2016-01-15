/*
 * Carrot - beacon content management
 * Copyright (C) 2016 Heiko Dreyer
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.boxedfolder.carrot.repository.impl;

import com.boxedfolder.carrot.domain.App;
import com.boxedfolder.carrot.domain.Beacon;
import com.boxedfolder.carrot.domain.analytics.AnalyticsLog;
import com.boxedfolder.carrot.domain.Event;
import com.boxedfolder.carrot.repository.AnalyticsLogRepository;
import org.joda.time.DateTime;
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
public class AnalyticsLogRepositoryImpl implements AnalyticsLogRepository {
    private EntityManager entityManager;

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
    public AnalyticsLog save(AnalyticsLog log) {
        return entityManager.merge(log);
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

    @Override
    public List<AnalyticsLog> findAll(DateTime from, DateTime to) {
        return entityManager.createQuery("SELECT a FROM AnalyticsLog a WHERE a.dateCreated BETWEEN :from AND :to", AnalyticsLog.class)
                            .setParameter("from", from)
                            .setParameter("to", to)
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
