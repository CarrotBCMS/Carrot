package com.boxedfolder.carrot.repository;

import com.boxedfolder.carrot.domain.Beacon;
import com.boxedfolder.carrot.domain.event.Event;
import com.boxedfolder.carrot.domain.event.TextEvent;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
@Repository
public interface EventRepository extends PagingAndSortingRepository<Event, Long> {
}
