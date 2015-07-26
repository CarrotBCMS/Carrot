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
            "/login"
    })
    public String index() {
        return "index";
    }
}
