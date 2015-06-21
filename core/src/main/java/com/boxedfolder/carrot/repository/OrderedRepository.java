package com.boxedfolder.carrot.repository;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
@NoRepositoryBean
public interface OrderedRepository<T> extends PagingAndSortingRepository<T, Long> {
    List<T> findAllByOrderByDateCreatedDesc();
}
