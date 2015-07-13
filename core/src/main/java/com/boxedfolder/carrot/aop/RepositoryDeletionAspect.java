package com.boxedfolder.carrot.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
@Aspect
@Component
public class RepositoryDeletionAspect {
    @Pointcut("execution(* org.springframework.data.repository.CrudRepository+.delete(*))")
    public void deletionMethod() {
    }

    @Before("deletionMethod()")
    public void logResourceMethod(JoinPoint joinPoint) {
        Logger log = LoggerFactory.getLogger(getClass());
        log.info("DELETED " + joinPoint);
    }
}
