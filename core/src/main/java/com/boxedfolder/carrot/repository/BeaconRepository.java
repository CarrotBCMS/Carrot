package com.boxedfolder.carrot.repository;

import com.boxedfolder.carrot.domain.Beacon;
import org.springframework.stereotype.Repository;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
@Repository
public interface BeaconRepository extends OrderedRepository<Beacon> {
}
