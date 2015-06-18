package com.boxedfolder.web.client;

import com.boxedfolder.carrot.domain.App;
import com.boxedfolder.carrot.service.AppService;
import com.boxedfolder.carrot.web.client.AppResource;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.notNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 **/
@RunWith(MockitoJUnitRunner.class)
public class AppResourceTest {
    @Mock
    private AppService service;
    private MockMvc restUserMockMvc;
    private List<App> testData;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AppResource resource = new AppResource();
        resource.setService(service);
        restUserMockMvc = MockMvcBuilders.standaloneSetup(resource).build();

        // Create 3 apps
        testData = new ArrayList<App>();
        App app = new App();
        app.setName("App 1");
        testData.add(app);

        app = new App();
        app.setName("App 2");
        testData.add(app);
        testData.add(app);

        app = new App();
        app.setName("App 3");
        testData.add(app);
        testData.add(app);
    }

    @Test
    public void testGetAllApps() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String value = mapper.writeValueAsString(testData);

        when(service.findAll()).thenReturn(testData);
        restUserMockMvc.perform(get("/client/apps")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(value));
    }

    @Test
    public void testAddApp() throws Exception {
        App app = testData.get(0);
        ObjectMapper mapper = new ObjectMapper();
        String value = mapper.writeValueAsString(app);
        given(service.save((App)notNull())).willReturn(app);
        restUserMockMvc.perform(post("/client/apps")
                .content(value)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().string(value));
    }

    @Test
    public void testDeleteApp() throws Exception {
        restUserMockMvc.perform(delete("/client/apps/0"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetSingleApp() throws Exception {
        App app = testData.get(0);
        given(service.find(1L)).willReturn(app);
        ObjectMapper mapper = new ObjectMapper();
        String value = mapper.writeValueAsString(app);
        restUserMockMvc.perform(get("/client/apps/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(value));
    }
}
