package com.boxedfolder.web.client;

import com.boxedfolder.carrot.domain.App;
import com.boxedfolder.carrot.domain.Beacon;
import com.boxedfolder.carrot.domain.analytics.AnalyticsLog;
import com.boxedfolder.carrot.domain.event.Event;
import com.boxedfolder.carrot.domain.event.NotificationEvent;
import com.boxedfolder.carrot.domain.util.View;
import com.boxedfolder.carrot.service.AnalyticsService;
import com.boxedfolder.carrot.web.client.AnalyticsResource;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.*;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
@RunWith(MockitoJUnitRunner.class)
public class AnalyticsResourceTest {
    @Mock
    private AnalyticsService service;
    private MockMvc restUserMockMvc;
    private ObjectMapper mapper;
    private List<AnalyticsLog> testData;
    private App app;
    private App secondApp;
    private Beacon beacon;
    private NotificationEvent event;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AnalyticsResource resource = new AnalyticsResource();
        resource.setService(service);
        restUserMockMvc = MockMvcBuilders.standaloneSetup(resource).build();

        mapper = new ObjectMapper();
        mapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);
        mapper.setConfig(mapper.getSerializationConfig().withView(View.Client.class));

        testData = new ArrayList<AnalyticsLog>();

        app = new App();
        app.setName("Testapp");
        app.setId(5L);
        app.setDateCreated(new DateTime());
        app.setDateUpdated(new DateTime());
        app.setApplicationKey(UUID.randomUUID());

        secondApp = new App();
        secondApp.setName("Testapp 2");
        secondApp.setId(2L);
        secondApp.setDateCreated(new DateTime());
        secondApp.setDateUpdated(new DateTime());
        secondApp.setApplicationKey(UUID.randomUUID());

        event = new NotificationEvent();
        event.setName("Event 2");
        event.setId(1L);
        event.setMessage("Test");
        event.setTitle("test");
        event.setDateUpdated(new DateTime());
        event.setDateCreated(new DateTime());
        event.getApps().add(app);

        beacon = new Beacon();
        beacon.setName("Event 2");
        beacon.setId(1L);
        beacon.setDateUpdated(new DateTime());
        beacon.setDateCreated(new DateTime());

        AnalyticsLog log = new AnalyticsLog();
        log.setId(11L);
        log.setDateUpdated(new DateTime());
        log.setDateCreated(new DateTime());
        log.setOccuredEvent(event);
        log.setBeacon(beacon);

        AnalyticsLog secondLog = new AnalyticsLog();
        secondLog.setId(1212L);
        secondLog.setDateUpdated(new DateTime());
        secondLog.setDateCreated(new DateTime().minusDays(5));
        secondLog.setOccuredEvent(event);
        secondLog.setBeacon(beacon);

        testData.add(log);
        testData.add(secondLog);
    }

    @Test
    public void testCountObjects() throws Exception {
        String output = "{\"apps\":\"1\",\"beacons\":\"2\",\"events\":\"4\"}";
        when(service.countApps()).thenReturn(1L);
        when(service.countEvents()).thenReturn(4L);
        when(service.countBeacons()).thenReturn(2L);
        restUserMockMvc.perform(get("/client/analytics/count")
                .accept(MediaType.APPLICATION_JSON))
                       .andExpect(status().isOk())
                       .andExpect(content().string(output));
    }

    @Test
    public void testGetAllAnalyticsLogs() throws Exception {
        String value = mapper.writeValueAsString(testData);

        when(service.findAll(null, null)).thenReturn(testData);
        restUserMockMvc.perform(get("/client/analytics/logs")
                .accept(MediaType.APPLICATION_JSON))
                       .andExpect(status().isOk())
                       .andExpect(content().string(value));
    }

    @Test
    public void testGetAllAnalyticsLogsWithRange() throws Exception {
        List<AnalyticsLog> sTestData = new ArrayList<AnalyticsLog>();
        sTestData.add(testData.get(1));

        String value = mapper.writeValueAsString(sTestData);
        DateTime from = new DateTime().minusDays(10);
        DateTime to = new DateTime().minusDays(3);

        when(service.findAll(from, to)).thenReturn(sTestData);
        DateTimeFormatter formatter = ISODateTimeFormat.dateTime();
        String requestString = "/client/analytics/logs?from=" + from.toString(formatter) + "&to=" + to.toString(formatter);
        restUserMockMvc.perform(get(requestString)
                .accept(MediaType.APPLICATION_JSON))
                       .andExpect(status().isOk())
                       .andExpect(content().string(value));
    }

    @Test
    public void testGetAppsStatistics() throws Exception {
        Map<App, Integer> map = new HashMap<App, Integer>();
        map.put(app, 5);
        map.put(secondApp, 3);

        String value = mapper.writeValueAsString(map);
        DateTime from = new DateTime().minusDays(10);
        DateTime to = new DateTime().minusDays(3);
        when(service.appsTriggered(from, to)).thenReturn(map);
        DateTimeFormatter formatter = ISODateTimeFormat.dateTime();
        String requestString = "/client/analytics/apps?from=" + from.toString(formatter) + "&to=" + to.toString(formatter);
        restUserMockMvc.perform(get(requestString)
                .accept(MediaType.APPLICATION_JSON))
                       .andExpect(status().isOk())
                       .andExpect(content().string(value));
    }

    @Test
    public void testGetBeaconsStatistics() throws Exception {
        Map<Beacon, Integer> map = new HashMap<Beacon, Integer>();
        map.put(beacon, 15);

        String value = mapper.writeValueAsString(map);
        DateTime from = new DateTime().minusDays(10);
        DateTime to = new DateTime().minusDays(3);
        when(service.beaconsTriggered(from, to)).thenReturn(map);
        DateTimeFormatter formatter = ISODateTimeFormat.dateTime();
        String requestString = "/client/analytics/beacons?from=" + from.toString(formatter) + "&to=" + to.toString(formatter);
        restUserMockMvc.perform(get(requestString)
                .accept(MediaType.APPLICATION_JSON))
                       .andExpect(status().isOk())
                       .andExpect(content().string(value));
    }

    @Test
    public void testGetEventsStatistics() throws Exception {
        Map<Event, Integer> map = new HashMap<Event, Integer>();
        map.put(event, 323);

        String value = mapper.writeValueAsString(map);
        DateTime from = new DateTime().minusDays(7);
        DateTime to = new DateTime().minusDays(3);
        when(service.eventsTriggered(from, to)).thenReturn(map);
        DateTimeFormatter formatter = ISODateTimeFormat.dateTime();
        String requestString = "/client/analytics/events?from=" + from.toString(formatter) + "&to=" + to.toString(formatter);
        restUserMockMvc.perform(get(requestString)
                .accept(MediaType.APPLICATION_JSON))
                       .andExpect(status().isOk())
                       .andExpect(content().string(value));
    }
}
