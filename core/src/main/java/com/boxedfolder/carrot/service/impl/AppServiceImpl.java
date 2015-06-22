package com.boxedfolder.carrot.service.impl;

import com.boxedfolder.carrot.domain.App;
import com.boxedfolder.carrot.repository.AppRepository;
import com.boxedfolder.carrot.service.AppService;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
@Service
public class AppServiceImpl extends CrudServiceImpl<App, AppRepository> implements AppService {
    // Override this to generate a random UUID for our new application
    @Override
    public App save(App object) {
        if (object.getId() == null) {
            object.setDateCreated(new DateTime());
            object.setApplicationKey(UUID.randomUUID()); // Add a random UUID
        } else {
            // If it is already persisted - fetch it and update creation date
            App oldObject = find(object.getId());
            object.setDateCreated(oldObject.getDateCreated());
            object.setApplicationKey(oldObject.getApplicationKey());
        }

        object.setDateUpdated(new DateTime()); // Mark as updated
        return repository.save(object);
    }
}
