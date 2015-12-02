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
        }
    }

    @Inject
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
