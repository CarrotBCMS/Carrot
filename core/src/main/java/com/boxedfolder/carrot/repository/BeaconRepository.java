package com.boxedfolder.carrot.repository;

import com.boxedfolder.carrot.domain.Beacon;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
public interface BeaconRepository extends PagingAndSortingRepository<Beacon, Long> {
}
