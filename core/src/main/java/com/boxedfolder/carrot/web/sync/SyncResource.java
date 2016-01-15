/*
 * Carrot - beacon content management
 * Copyright (C) 2016 Heiko Dreyer
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

package com.boxedfolder.carrot.web.sync;

import com.boxedfolder.carrot.domain.util.View;
import com.boxedfolder.carrot.service.SyncService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.Map;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
@RestController
@RequestMapping("/sync")
public class SyncResource {
    private SyncService syncService;

    @JsonView(View.Sync.class)
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> sync(@RequestParam(required = false, value = "ts") Long timestamp,
                                    @RequestParam(required = true, value = "app_key") String appKey)
    {
        return syncService.sync(timestamp, appKey);
    }

    @Inject
    public void setSyncService(SyncService syncService) {
        this.syncService = syncService;
    }
}
