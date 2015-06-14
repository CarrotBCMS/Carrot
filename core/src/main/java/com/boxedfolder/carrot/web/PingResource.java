package com.boxedfolder.carrot.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
@RestController
public class PingResource {

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/ping", method = RequestMethod.GET)
    public void ping() {
        if (email != null && token != null) {
            service.activateUser(email, token);
        }
    }
}
