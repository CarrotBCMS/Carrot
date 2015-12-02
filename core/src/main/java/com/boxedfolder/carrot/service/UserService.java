package com.boxedfolder.carrot.service;

import com.boxedfolder.carrot.domain.User;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
public interface UserService extends CrudService<User> {
    User findByEmail(String email);

    User createUser(String email, String password, Boolean isEnabled);
    User updateUser(Long id, User updatedUser);
    void activateUser(String email, String token);
    void resendActivationMail(String email);
    void requestResetPassword(String email);
    void resetPassword(String email, String token);
}
