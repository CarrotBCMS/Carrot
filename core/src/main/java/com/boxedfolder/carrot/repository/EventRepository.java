package com.boxedfolder.carrot.repository;

import com.boxedfolder.carrot.domain.event.Event;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
public interface EventRepository extends PagingAndSortingRepository<Event, Long> {
}
