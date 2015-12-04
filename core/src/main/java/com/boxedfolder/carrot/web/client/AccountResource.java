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

package com.boxedfolder.carrot.web.client;

import com.boxedfolder.carrot.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
@RestController
@RequestMapping("/client")
public class AccountResource {
    private UserService service;

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/activate", method = RequestMethod.GET)
    public void activate(@RequestParam("email") String email, @RequestParam("token") String token) {
        if (email != null && token != null) {
            service.activateUser(email, token);
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/forgot", method = RequestMethod.GET)
    public void forgot(@RequestParam("email") String email) {
        if (email != null) {
            service.requestResetPassword(email);
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/reset", method = RequestMethod.GET)
    public void reset(@RequestParam("email") String email, @RequestParam("token") String token) {
        if (email != null && token != null) {
            service.resetPassword(email, token);
        }
    }

    @Inject
    public void setService(UserService service) {
        this.service = service;
    }
}
