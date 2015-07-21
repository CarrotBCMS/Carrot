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