/*
 * Carrot - beacon management
 * Copyright (C) 2015 Heiko Dreyer
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

import com.boxedfolder.carrot.domain.general.logs.RemovedRelationshipLog;
import com.boxedfolder.carrot.domain.general.logs.TransactionLog;
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
    public List<Long> findDeletedIDsByDateTimeAndClass(DateTime dateTime, Class clazz, Long userId) {
        return entityManager.createQuery("SELECT l.entityId FROM EntityDeletionLog l WHERE l.dateTime > :dateTime AND " +
                "l.type = :class AND l.userId = :user", Long.class)
                            .setParameter("dateTime", dateTime)
                            .setParameter("class", clazz)
                            .setParameter("user", userId)
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
