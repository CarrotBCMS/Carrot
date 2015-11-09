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
 * Logs incoming requests.
 *
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
@Profile(Profiles.DEVELOP)
@Aspect
@Component
public class LoggingAspect {
    @Pointcut("execution(* com.boxedfolder.carrot.web.*.*(..))")
    public void resourceMethod() {
    }

    @Before("resourceMethod()")
    public void logResourceMethod(JoinPoint joinPoint) {
        logInfo(getClass(), joinPoint);
    }

    private void logInfo(Class theClass, JoinPoint joinPoint) {
        Logger log = LoggerFactory.getLogger(theClass);
        log.info("\n\nCarrot request with signature: "
                + joinPoint.getSignature()
                + "\nArguments: " + Arrays.toString(joinPoint.getArgs()) + "\n");
    }
}
