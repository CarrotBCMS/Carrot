package com.boxedfolder.carrot.aop;

import com.boxedfolder.carrot.config.security.AuthenticationHelper;
import com.boxedfolder.carrot.domain.User;
import com.boxedfolder.carrot.domain.general.AbstractUserRelatedEntity;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

import javax.inject.Inject;
import java.io.Serializable;

/**
 * DO THIS WITH A FILTER
 */

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
public class UserDataInterceptor extends EmptyInterceptor {
    AuthenticationHelper authenticationHelper;

    @Override
    public boolean onLoad(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        if (entity != null && entity instanceof AbstractUserRelatedEntity) {
            User user = authenticationHelper.getCurrentUser();
            User objectUser = ((AbstractUserRelatedEntity)entity).getUser();
            if (objectUser == null || user.getId() != objectUser.getId()) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        if (entity != null && entity instanceof AbstractUserRelatedEntity) {
            User user = authenticationHelper.getCurrentUser();
            ((AbstractUserRelatedEntity)entity).setUser(user);
        }

        return false;
    }

    @Inject
    public void setAuthenticationHelper(AuthenticationHelper authenticationHelper) {
        this.authenticationHelper = authenticationHelper;
    }
}