package com.boxedfolder.carrot.service.impl;

import com.boxedfolder.carrot.domain.Beacon;
import com.boxedfolder.carrot.repository.BeaconRepository;
import com.boxedfolder.carrot.service.BeaconService;
import org.springframework.stereotype.Service;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
@Service
public class BeaconServiceImpl extends CrudServiceImpl<Beacon, BeaconRepository> implements BeaconService {
}
