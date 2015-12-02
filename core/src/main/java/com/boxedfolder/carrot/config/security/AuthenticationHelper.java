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

package com.boxedfolder.carrot.config.security;

import com.boxedfolder.carrot.domain.User;
import com.boxedfolder.carrot.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
@Component
public class AuthenticationHelper {
    private UserService userService;
    private AuthenticationManager authenticationManager;

    private String getCurrentUsername() {
        return (String)SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public void setAuthentication(Authentication authentication) {
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public Authentication authenticate(Authentication authentication) {
        return authenticationManager.authenticate(authentication);
    }

    public void setupCompleteAuthentication(Authentication authentication) {
        authentication = authenticate(authentication);
        setAuthentication(authentication);
    }

    public User getCurrentUser() {
        String currentEmail = getCurrentUsername();
        return userService.findByEmail(currentEmail);
    }

    @Inject
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Inject
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}