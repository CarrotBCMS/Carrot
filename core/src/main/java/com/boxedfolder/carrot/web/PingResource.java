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

package com.boxedfolder.carrot.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * For testing purposes only.
 *
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
@RestController
public class PingResource {
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/client/ping", method = RequestMethod.GET)
    public String ping() {
        return "Ping. (Secured)";
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/sync/ping", method = RequestMethod.GET)
    public String syncPing() {
        return "Ping. (Unsecured)";
    }
}
