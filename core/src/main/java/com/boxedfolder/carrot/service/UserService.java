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
