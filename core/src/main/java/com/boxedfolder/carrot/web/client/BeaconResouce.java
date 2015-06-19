package com.boxedfolder.carrot.web.client;

import com.boxedfolder.carrot.domain.Beacon;
import com.boxedfolder.carrot.service.BeaconService;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
@RequestMapping("/client/beacons")
public class BeaconResouce extends CrudResource<BeaconService, Beacon> {
}
