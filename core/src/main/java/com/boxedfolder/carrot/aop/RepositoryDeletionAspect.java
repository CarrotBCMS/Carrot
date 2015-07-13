package com.boxedfolder.carrot.aop;

import com.boxedfolder.carrot.domain.general.AbstractEntity;
import com.boxedfolder.carrot.domain.general.DeletionLog;
import com.boxedfolder.carrot.repository.DeletionLogRepository;
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
    private DeletionLogRepository logRepository;

    @Pointcut("execution(* org.springframework.data.repository.CrudRepository+.delete(*))")
    public void deletionMethod() {
    }

    @After("deletionMethod()")
    public void logResourceMethod(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        if (args[0] == null || !(args[0] instanceof AbstractEntity)) {
            return;
        }

        // Create DeletionLog for deleted repository object
        AbstractEntity entity = (AbstractEntity)args[0];
        DeletionLog deletionLog = new DeletionLog();
        deletionLog.setType(entity.getClass());
        deletionLog.setDateTime(new DateTime());
        deletionLog.setEntityId(entity.getId());
        logRepository.save(deletionLog);
    }

    @Inject
    public void setLogRepository(DeletionLogRepository logRepository) {
        this.logRepository = logRepository;
    }
}