package com.boxedfolder.carrot.web.sync;

import com.boxedfolder.carrot.domain.analytics.AnalyticsLog;
import com.boxedfolder.carrot.service.SyncService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
@RestController
@RequestMapping("/sync")
public class SyncResource {
    private SyncService syncService;

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> sync(@RequestParam(required = true, value = "ts")Long timestamp,
                                    @RequestParam(required = true, value = "app_key") String appKey) {
        return syncService.sync(timestamp, appKey);
    }

    @Inject
    public void setSyncService(SyncService syncService) {
        this.syncService = syncService;
    }
}
