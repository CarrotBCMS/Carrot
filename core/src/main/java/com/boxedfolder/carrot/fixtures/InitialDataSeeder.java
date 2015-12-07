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

package com.boxedfolder.carrot.fixtures;

import com.boxedfolder.carrot.config.Profiles;
import com.boxedfolder.carrot.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * Pre-populates database with default user.
 * Only available in develop mode.
 *
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
@Profile({Profiles.DEVELOP, Profiles.TESTING})
@Component
public class InitialDataSeeder {
    private static Logger log = Logger.getLogger(InitialDataSeeder.class.getName());
    private UserService userService;

    @PostConstruct
    public void seedData() {
        if (userService.count() == 0) {
            log.info("Seeding database with initial data -> email: mail@carrot.re, password: password");
            userService.createUser("mail@carrot.re", "password", true);

            log.info("Seeding database with initial data -> email: test@carrot.re, password: password");
            userService.createUser("test@carrot.re", "password", true);
        }
    }

    @Inject
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
