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
