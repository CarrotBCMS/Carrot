/*
 * Carrot - beacon management
 * Copyright (C) 2015 Heiko Dreyer
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.boxedfolder.web.client;

import com.boxedfolder.carrot.domain.App;
import com.boxedfolder.carrot.domain.util.View;
import com.boxedfolder.carrot.service.AppService;
import com.boxedfolder.carrot.web.client.AppResource;
import com.fasterxml.jackson.databind.MapperFeature;
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
    private ObjectMapper mapper;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AppResource resource = new AppResource();
        resource.setService(service);
        restUserMockMvc = MockMvcBuilders.standaloneSetup(resource).build();

        mapper = new ObjectMapper();
        mapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);
        mapper.setConfig(mapper.getSerializationConfig().withView(View.Client.class));

        // Create 3 apps
        testData = new ArrayList<App>();
        App app = new App();
        app.setName("App 1");
        testData.add(app);

        app = new App();
        app.setName("App 2");
        testData.add(app);

        app = new App();
        app.setName("App 3");
        testData.add(app);
    }

    @Test
    public void testGetAllApps() throws Exception {
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
        String value = mapper.writeValueAsString(app);
        restUserMockMvc.perform(get("/client/apps/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(value));
    }
}
