package com.boxedfolder.web.client;

import com.boxedfolder.carrot.domain.App;
import com.boxedfolder.carrot.domain.Beacon;
import com.boxedfolder.carrot.domain.analytics.AnalyticsLog;
import com.boxedfolder.carrot.domain.analytics.AnalyticsTransfer;
import com.boxedfolder.carrot.domain.event.Event;
import com.boxedfolder.carrot.domain.event.NotificationEvent;
import com.boxedfolder.carrot.domain.util.View;
import com.boxedfolder.carrot.service.AnalyticsService;
import com.boxedfolder.carrot.web.client.AnalyticsResource;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
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

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.notNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    public void testAddAnalyticsLogs() throws Exception {
        mapper.setConfig(mapper.getSerializationConfig().withView(View.Meta.class));
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

        AnalyticsLog log = testData.get(0);
        String value = mapper.writeValueAsString(log);

        when(service.save((AnalyticsLog)anyObject(), (UUID)anyObject())).thenReturn(log);
        restUserMockMvc.perform(post("/client/analytics/logs")
                .param("appKey", app.getApplicationKey().toString())
                .content(value)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                       .andExpect(status().isCreated())
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
        List<AnalyticsTransfer> list = new ArrayList<AnalyticsTransfer>();
        AnalyticsTransfer transfer = new AnalyticsTransfer();
        transfer.setId(app.getId());
        transfer.setName(app.getName());
        transfer.setCount(22);
        list.add(transfer);

        String value = mapper.writeValueAsString(list);
        DateTime from = new DateTime().minusDays(10);
        DateTime to = new DateTime().minusDays(3);
        when(service.appsTriggered(from, to)).thenReturn(list);
        DateTimeFormatter formatter = ISODateTimeFormat.dateTime();
        String requestString = "/client/analytics/apps?from=" + from.toString(formatter) + "&to=" + to.toString(formatter);
        restUserMockMvc.perform(get(requestString)
                .accept(MediaType.APPLICATION_JSON))
                       .andExpect(status().isOk())
                       .andExpect(content().string(value));
    }

    @Test
    public void testGetBeaconsStatistics() throws Exception {
        List<AnalyticsTransfer> list = new ArrayList<AnalyticsTransfer>();
        AnalyticsTransfer transfer = new AnalyticsTransfer();
        transfer.setId(beacon.getId());
        transfer.setName(beacon.getName());
        transfer.setCount(22);
        list.add(transfer);

        String value = mapper.writeValueAsString(list);
        DateTime from = new DateTime().minusDays(10);
        DateTime to = new DateTime().minusDays(3);
        when(service.beaconsTriggered(from, to)).thenReturn(list);
        DateTimeFormatter formatter = ISODateTimeFormat.dateTime();
        String requestString = "/client/analytics/beacons?from=" + from.toString(formatter) + "&to=" + to.toString(formatter);
        restUserMockMvc.perform(get(requestString)
                .accept(MediaType.APPLICATION_JSON))
                       .andExpect(status().isOk())
                       .andExpect(content().string(value));
    }

    @Test
    public void testGetEventsStatistics() throws Exception {
        List<AnalyticsTransfer> list = new ArrayList<AnalyticsTransfer>();
        AnalyticsTransfer transfer = new AnalyticsTransfer();
        transfer.setId(event.getId());
        transfer.setName(event.getName());
        transfer.setCount(22);
        list.add(transfer);

        String value = mapper.writeValueAsString(list);
        DateTime from = new DateTime().minusDays(7);
        DateTime to = new DateTime().minusDays(3);
        when(service.eventsTriggered(from, to)).thenReturn(list);
        DateTimeFormatter formatter = ISODateTimeFormat.dateTime();
        String requestString = "/client/analytics/events?from=" + from.toString(formatter) + "&to=" + to.toString(formatter);
        restUserMockMvc.perform(get(requestString)
                .accept(MediaType.APPLICATION_JSON))
                       .andExpect(status().isOk())
                       .andExpect(content().string(value));
    }
}
