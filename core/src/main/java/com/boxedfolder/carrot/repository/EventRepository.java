package com.boxedfolder.carrot.repository;

import com.boxedfolder.carrot.domain.Event;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
@Repository
public interface EventRepository extends OrderedRepository<Event> {
    List<Event> findByDateUpdatedAfter(DateTime dateTime);
}
