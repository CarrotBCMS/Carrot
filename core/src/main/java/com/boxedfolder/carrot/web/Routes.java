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

package com.boxedfolder.carrot.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
@Controller
public class Routes {
    // This is necessary for Angulars html5 mode. It basically routes everything to index.html.
    @RequestMapping({
            "/beacons",
            "/beacons/{id:\\w+}",
            "/events",
            "/events/{id:\\w+}",
            "/apps",
            "/apps/{id:\\w+}",
            "/login",
            "/activate",
            "/register"
    })
    public String index() {
        return "index";
    }
}
