package com.boxedfolder.carrot.repository;

import com.boxedfolder.carrot.domain.App;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
public interface AppRepository extends PagingAndSortingRepository<App, Long> {
}
