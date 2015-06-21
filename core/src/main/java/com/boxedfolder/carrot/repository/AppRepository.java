package com.boxedfolder.carrot.repository;

import com.boxedfolder.carrot.domain.App;
import org.springframework.stereotype.Repository;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
@Repository
public interface AppRepository extends OrderedRepository<App> {
}
