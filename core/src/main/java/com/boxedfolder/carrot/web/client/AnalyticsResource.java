package com.boxedfolder.carrot.web.client;

import com.boxedfolder.carrot.service.AnalyticsService;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.fasterxml.jackson.databind.util.JSONWrappedObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
@RestController
@RequestMapping("/client/analytics")
public class AnalyticsResource {
    protected AnalyticsService service;

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/count", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> countEntities() {
        Map<String, String> output = new TreeMap<String, String>();
        output.put("beacons", Long.toString(service.countBeacons()));
        output.put("apps", Long.toString(service.countApps()));
        output.put("events", Long.toString(service.countEvents()));
        return output;
    }

    @Inject
    public void setService(AnalyticsService service) {
        this.service = service;
    }
}
