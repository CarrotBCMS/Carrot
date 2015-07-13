package com.boxedfolder.carrot.repository;

import com.boxedfolder.carrot.domain.general.DeletionLog;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
@Repository
public interface DeletionLogRepository extends CrudRepository<DeletionLog, Long> {
}
