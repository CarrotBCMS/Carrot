package com.boxedfolder.carrot.repository;

import com.boxedfolder.carrot.domain.Event;
import org.springframework.stereotype.Repository;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
@Repository
public interface EventRepository extends OrderedRepository<Event> {
}
