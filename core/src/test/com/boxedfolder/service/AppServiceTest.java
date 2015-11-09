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
public class AppServiceTest {
    @Mock
    private AppRepository repository;
    private AppServiceImpl service;
    private List<App> testData;

    @Before
    public void setUp() {
        service = new AppServiceImpl();
        service.setRepository(repository);
        testData = new ArrayList<>();

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
        when(repository.findAllByOrderByDateCreatedDesc()).thenReturn(testData);
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
