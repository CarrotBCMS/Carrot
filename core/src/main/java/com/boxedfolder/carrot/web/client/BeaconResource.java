package com.boxedfolder.carrot.web.client;

import com.boxedfolder.carrot.domain.Beacon;
import com.boxedfolder.carrot.service.BeaconService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
@RestController
@RequestMapping("/client/beacons")
public class BeaconResource extends CrudResource<BeaconService, Beacon> {
}
