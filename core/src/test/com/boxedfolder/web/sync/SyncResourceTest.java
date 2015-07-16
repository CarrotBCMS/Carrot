package com.boxedfolder.web.sync;

import com.boxedfolder.carrot.service.SyncService;
import com.boxedfolder.carrot.web.sync.SyncResource;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
@RunWith(MockitoJUnitRunner.class)
public class SyncResourceTest {
    @Mock
    private SyncService service;
    private MockMvc restUserMockMvc;
    private ObjectMapper mapper;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SyncResource resource = new SyncResource();
        restUserMockMvc = MockMvcBuilders.standaloneSetup(resource).build();
        mapper = new ObjectMapper();
    }

    @Test
    public void testSyncData() {

    }
}
