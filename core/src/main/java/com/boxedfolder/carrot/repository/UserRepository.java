package com.boxedfolder.carrot.repository;

import com.boxedfolder.carrot.domain.User;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
public interface UserRepository extends OrderedRepository<User> {
    User findByEmail(String email);
}
