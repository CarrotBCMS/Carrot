package com.boxedfolder.carrot.aop;

import com.boxedfolder.carrot.config.Profiles;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Logs incoming (REST) requests.
 *
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
@Profile(Profiles.DEVELOP)
@Aspect
@Component
public class LoggingAspect {
    @Pointcut("execution(* com.boxedfolder.carrot.web.rest.*.*(..))")
    public void resourceMethod() {
    }

    @Pointcut("execution(* com.boxedfolder.carrot.sync.rest.*.*(..))")
    public void syncMethod() {
    }

    @Before("resourceMethod()")
    public void logResourceMethod(JoinPoint joinPoint) {
        logInfo(getClass(), joinPoint);
    }

    private void logInfo(Class theClass, JoinPoint joinPoint) {
        Logger log = LoggerFactory.getLogger(theClass);
        log.info("\n\nCarrot " + joinPoint.getSourceLocation() + " REST request with signature: " + joinPoint.getSignature() + "\nArguments: " + Arrays.toString(joinPoint.getArgs()) + "\n");
    }
}
