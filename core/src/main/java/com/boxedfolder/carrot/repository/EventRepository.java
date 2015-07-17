package com.boxedfolder.carrot.repository;

import com.boxedfolder.carrot.domain.App;
import com.boxedfolder.carrot.domain.Event;
import org.joda.time.DateTime;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
@Repository
public interface EventRepository extends OrderedRepository<Event> {
    @Query("SELECT DISTINCT e FROM #{#entityName} e, IN(e.apps) a WHERE e.dateUpdated > ?1 AND a = ?2")
    List<Event> findByDateUpdated(DateTime dateTime, App app);
}
