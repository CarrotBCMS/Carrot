package com.boxedfolder.carrot.repository;

import com.boxedfolder.carrot.domain.App;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
public interface AppRepository extends CrudRepository<App, Long> {
}
