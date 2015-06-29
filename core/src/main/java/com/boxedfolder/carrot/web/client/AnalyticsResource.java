package com.boxedfolder.carrot.web.client;

import com.boxedfolder.carrot.domain.analytics.AnalyticsLog;
import com.boxedfolder.carrot.service.AnalyticsService;
import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;
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

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/logs", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AnalyticsLog> getAnalyticsLogs(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) DateTime from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) DateTime to)
    {
        return service.findAll(from, to);
    }

    @Inject
    public void setService(AnalyticsService service) {
        this.service = service;
    }
}
