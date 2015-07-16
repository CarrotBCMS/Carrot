package com.boxedfolder.carrot.repository;

import com.boxedfolder.carrot.domain.Beacon;
import com.boxedfolder.carrot.domain.general.EntityDeletionLog;
import org.joda.time.DateTime;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
@Repository
public interface EntityDeletionLogRepository extends CrudRepository<EntityDeletionLog, Long> {
    @Query("SELECT l.entityId FROM EntityDeletionLog l WHERE l.dateTime > ?1 AND l.type = ?2")
    List<Long> findDeletedIDsByDateTimeAndClass(DateTime dateTime, Class clazz);
}
