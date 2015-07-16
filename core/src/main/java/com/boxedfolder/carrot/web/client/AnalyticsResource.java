package com.boxedfolder.carrot.web.client;

import com.boxedfolder.carrot.domain.analytics.AnalyticsLog;
import com.boxedfolder.carrot.domain.analytics.AnalyticsTransfer;
import com.boxedfolder.carrot.domain.util.View;
import com.boxedfolder.carrot.service.AnalyticsService;
import com.fasterxml.jackson.annotation.JsonView;
import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

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
        Map<String, String> output = new TreeMap<>();
        output.put("beacons", Long.toString(service.countBeacons()));
        output.put("apps", Long.toString(service.countApps()));
        output.put("events", Long.toString(service.countEvents()));
        return output;
    }

    @JsonView(View.Meta.class)
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/logs", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AnalyticsLog> getAnalyticsLogs(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) DateTime from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) DateTime to)
    {
        return service.findAll(from, to);
    }

    @JsonView(View.Meta.class)
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/logs", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public AnalyticsLog createAnalyticsLogs(@RequestBody @Valid AnalyticsLog log,
                                            @RequestParam(required = true, value = "app_key") String appKey)
    {
        return service.save(log, UUID.fromString(appKey));
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/apps", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AnalyticsTransfer> getAppStatistics(
            @RequestParam(required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) DateTime from,
            @RequestParam(required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) DateTime to)
    {
        return service.appsTriggered(from, to);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/beacons", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AnalyticsTransfer> getBeaconStatistics(
            @RequestParam(required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) DateTime from,
            @RequestParam(required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) DateTime to)
    {
        return service.beaconsTriggered(from, to);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/events", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AnalyticsTransfer> getEventStatistics(
            @RequestParam(required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) DateTime from,
            @RequestParam(required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) DateTime to)
    {
        return service.eventsTriggered(from, to);
    }

    @Inject
    public void setService(AnalyticsService service) {
        this.service = service;
    }
}
