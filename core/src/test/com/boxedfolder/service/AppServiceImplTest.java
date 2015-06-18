package com.boxedfolder.service;

import com.boxedfolder.carrot.domain.App;
import com.boxedfolder.carrot.repository.AppRepository;
import com.boxedfolder.carrot.service.impl.AppServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
@RunWith(MockitoJUnitRunner.class)
public class AppServiceImplTest {
    @Mock
    private AppRepository repository;
    private AppServiceImpl service;
    private List<App> testData;

    @Before
    public void setUp() {
        service = new AppServiceImpl();
        service.setRepository(repository);
        testData = new ArrayList<App>();

        // Create 3 different apps
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
    public void testRetrieveAllApps() {
        when(repository.findAll()).thenReturn(testData);
        List<App> apps = service.findAll();
        assertEquals(apps, testData);
    }

    @Test
    public void testRetrieveSingleApp() {
        when(repository.findOne(1L)).thenReturn(testData.get(0));
        App app = service.find(1L);
        assertEquals(app, testData.get(0));
    }

    @Test
    public void testSaveApp() {
        when(repository.save(testData.get(0))).thenReturn(testData.get(0));
        App app = service.save(testData.get(0));
        assertEquals(app, testData.get(0));
    }
}
