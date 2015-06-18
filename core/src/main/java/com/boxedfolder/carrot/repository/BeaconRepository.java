package com.boxedfolder.carrot.repository;

import com.boxedfolder.carrot.domain.Beacon;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
public interface BeaconRepository extends CrudRepository<Beacon, Long> {
}
