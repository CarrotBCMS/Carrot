package com.boxedfolder.web.sync;

import com.boxedfolder.carrot.domain.App;
import com.boxedfolder.carrot.domain.Beacon;
import com.boxedfolder.carrot.domain.NotificationEvent;
import com.boxedfolder.carrot.domain.util.View;
import com.boxedfolder.carrot.service.SyncService;
import com.boxedfolder.carrot.web.sync.SyncResource;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.joda.time.DateTime;
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

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
@RunWith(MockitoJUnitRunner.class)
public class SyncResourceTest {
    @Mock
    private SyncService service;
    private MockMvc restUserMockMvc;
    private ObjectMapper mapper;
    private Map<String, Object> testData;
    private Long timestamp;
    private App app;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SyncResource resource = new SyncResource();
        resource.setSyncService(service);
        restUserMockMvc = MockMvcBuilders.standaloneSetup(resource).build();
        mapper = new ObjectMapper();

        testData = new HashMap<>();
        app = new App();
        app.setName("Testapp");
        app.setId(5L);
        app.setDateCreated(new DateTime());
        app.setDateUpdated(new DateTime());
        app.setApplicationKey(UUID.randomUUID());

        App secondApp = new App();
        secondApp.setName("Testapp 2");
        secondApp.setId(2L);
        secondApp.setDateCreated(new DateTime());
        secondApp.setDateUpdated(new DateTime());
        secondApp.setApplicationKey(UUID.randomUUID());

        NotificationEvent event = new NotificationEvent();
        event.setName("Event 1");
        event.setId(1L);
        event.setMessage("Test");
        event.setTitle("test");
        event.setDateUpdated(new DateTime());
        event.setDateCreated(new DateTime());
        event.getApps().add(app);

        Beacon beacon = new Beacon();
        beacon.setName("Beacon");
        beacon.setId(1L);
        beacon.setMajor(2);
        beacon.setMinor(1);
        beacon.setUuid(UUID.randomUUID());
        beacon.setDateUpdated(new DateTime());
        beacon.setDateCreated(new DateTime());

        timestamp = 0L;
        testData.put("timestamp", System.currentTimeMillis() / 1000L);

        Map<String, Object> beaconResult = new HashMap<>();
        beaconResult.put("createdOrUpdated", Arrays.asList(beacon));

        // First sync, add empty list
        beaconResult.put("deleted", new ArrayList<>());

        testData.put("beacons", beaconResult);

        Map<String, Object> eventResult = new HashMap<>();
        eventResult.put("createdOrUpdated", Arrays.asList(event));
        testData.put("events", eventResult);
    }

    @Test
    public void testSyncData() throws Exception {
        mapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);
        mapper.setConfig(mapper.getSerializationConfig().withView(View.Sync.class));
        String value = mapper.writeValueAsString(testData);

        when(service.sync((Long)anyObject(), (String)anyObject())).thenReturn(testData);
        restUserMockMvc.perform(get("/sync")
                .param("app_key", app.getApplicationKey().toString())
                .accept(MediaType.APPLICATION_JSON))
                       .andExpect(status().isOk())
                       .andExpect(content().string(value));
    }
}
