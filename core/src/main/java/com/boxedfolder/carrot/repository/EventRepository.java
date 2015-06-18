package com.boxedfolder.carrot.repository;

import com.boxedfolder.carrot.domain.event.Event;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
public interface EventRepository extends CrudRepository<Event, Long> {
}
