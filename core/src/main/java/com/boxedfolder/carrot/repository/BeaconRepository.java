/*
 * Carrot - beacon management
 * Copyright (C) 2016 Heiko Dreyer
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.boxedfolder.carrot.repository;

import com.boxedfolder.carrot.domain.Beacon;
import com.boxedfolder.carrot.domain.User;
import org.joda.time.DateTime;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
@Repository
public interface BeaconRepository extends UserRelatedRepository<Beacon> {
    Beacon findFirstByUuidAndMajorAndMinor(UUID uuid, int major, int minor);

    @Query("SELECT DISTINCT b FROM Beacon b WHERE b.dateUpdated > ?1")
    List<Beacon> findByDateUpdated(DateTime dateTime);

    @Query("SELECT DISTINCT b FROM Beacon b WHERE b.dateUpdated > ?1 AND b.user = ?2")
    List<Beacon> findByDateUpdatedAndUser(DateTime dateTime, User user);
}
