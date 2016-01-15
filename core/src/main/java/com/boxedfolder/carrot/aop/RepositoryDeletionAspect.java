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

package com.boxedfolder.carrot.aop;

import com.boxedfolder.carrot.domain.general.AbstractEntity;
import com.boxedfolder.carrot.domain.general.logs.EntityDeletionLog;
import com.boxedfolder.carrot.repository.TransactionLogRepository;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
@Aspect
@Component
public class RepositoryDeletionAspect {
    private TransactionLogRepository logRepository;

    @Pointcut("execution(* com.boxedfolder.carrot.repository.OrderedRepository+.delete(*))")
    public void deletionMethod() {
    }

    @After("deletionMethod()")
    public void logDeletionMethod(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        if (args[0] == null || !(args[0] instanceof AbstractEntity)) {
            return;
        }

        // Create DeletionLog for deleted entity
        AbstractEntity entity = (AbstractEntity)args[0];
        EntityDeletionLog deletionLog = new EntityDeletionLog();
        deletionLog.setType(entity.getClass());
        deletionLog.setDateTime(new DateTime());
        deletionLog.setEntityId(entity.getId());
        logRepository.save(deletionLog);
    }

    @Inject
    public void setLogRepository(TransactionLogRepository logRepository) {
        this.logRepository = logRepository;
    }
}