/*
 * Carrot - beacon management
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

import com.boxedfolder.carrot.config.security.AuthenticationHelper;
import com.boxedfolder.carrot.domain.User;
import com.boxedfolder.carrot.domain.general.AbstractUserRelatedEntity;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
@Aspect
@Component
public class RepositoryUserSaveAspect {
    private static final Logger logger = LoggerFactory.getLogger(RepositoryUserSaveAspect.class);
    private AuthenticationHelper authenticationHelper;

    @Pointcut("execution(* com.boxedfolder.carrot.repository.UserRelatedRepository+.save(*))")
    public void saveMethod() {
    }

    @Before("saveMethod()")
    public void addUserToSave(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();

        if (args[0] == null) {
            return;
        }

        checkObject(args[0]);

        if (args[0] instanceof Iterable) {
            for (Object object : (Iterable<? extends Object>)args[0]) {
                checkObject(object);
            }
        }
    }

    private void checkObject(Object object) {
        if (object instanceof AbstractUserRelatedEntity) {
            addUser((AbstractUserRelatedEntity)object);
        }
    }

    private void addUser(AbstractUserRelatedEntity object) {
        User currentUser = authenticationHelper.getCurrentUser();
        logger.info("User: " + currentUser.getUsername());
        logger.info("Entity: " + object.toString());
        if (currentUser != null) {
            logger.info("Adding current user to saved entity...");
            object.setUser(currentUser);
        }
    }

    @Inject
    public void setAuthenticationHelper(AuthenticationHelper authenticationHelper) {
        this.authenticationHelper = authenticationHelper;
    }
}
