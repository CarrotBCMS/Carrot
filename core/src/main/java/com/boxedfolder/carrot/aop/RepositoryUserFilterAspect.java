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

import com.boxedfolder.carrot.config.security.AuthenticationHelper;
import com.boxedfolder.carrot.domain.general.AbstractUserRelatedEntity;
import com.boxedfolder.carrot.repository.UserRepository;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
@Aspect
@Component
public class RepositoryUserFilterAspect {
    private static final Logger logger = LoggerFactory.getLogger(RepositoryUserFilterAspect.class);
    private AuthenticationHelper authenticationHelper;

    @Pointcut("execution(* com.boxedfolder.carrot.repository.OrderedRepository+.find*(..))")
    public void findMethod() {
    }

    @Around("findMethod()")
    public Object aroundFindMethod(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object returnValue = proceedingJoinPoint.proceed();
        // Bail out if this is a call from UserRepository
        if (proceedingJoinPoint.getSignature().getDeclaringType() == UserRepository.class) {
            return returnValue;
        }

        logger.debug("Starting find method: " + proceedingJoinPoint.toString());
        AbstractUserRelatedEntity entity = null;
        String userEmail = authenticationHelper.getCurrentUser().getEmail();

        // This is a single entity
        if (returnValue instanceof AbstractUserRelatedEntity) {
            logger.debug("Return value is single entity");
            entity = (AbstractUserRelatedEntity)returnValue;
            if (entity.getUser() == null ||
                    !entity.getUser().getEmail().equals(userEmail)) {
                return null;
            }
        }

        // This is a collection
        if (returnValue instanceof Iterable) {
            logger.debug("Return value is List or Iterator");
            List returnEntites = new ArrayList();
            Iterable entities = (Iterable)returnValue;
            for (Object object : entities) {
                if (object instanceof AbstractUserRelatedEntity) {
                    entity = (AbstractUserRelatedEntity)object;
                    if (entity.getUser() != null &&
                            entity.getUser().getEmail().equals(userEmail)) {
                        returnEntites.add(entity);
                    }
                }
            }

            return returnEntites;
        }

        logger.debug("Return value: " + returnValue.toString());

        return returnValue;
    }

    @Inject
    public void setAuthenticationHelper(AuthenticationHelper authenticationHelper) {
        this.authenticationHelper = authenticationHelper;
    }
}
