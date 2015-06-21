package com.boxedfolder.carrot.repository;

import com.boxedfolder.carrot.domain.Beacon;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
@Repository
public interface BeaconRepository extends OrderedRepository<Beacon> {
    Beacon findFirstByUuidAndMajorAndMinor(UUID uuid, int major, int minor);
}
