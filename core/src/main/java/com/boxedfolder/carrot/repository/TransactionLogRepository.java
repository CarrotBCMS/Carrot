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

import com.boxedfolder.carrot.domain.User;
import com.boxedfolder.carrot.domain.general.logs.RemovedRelationshipLog;
import com.boxedfolder.carrot.domain.general.logs.TransactionLog;
import org.joda.time.DateTime;

import java.util.List;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
public interface TransactionLogRepository {
    List<Long> findDeletedIDsByDateTimeAndClass(DateTime dateTime, Class clazz, Long userId);
    TransactionLog save(TransactionLog log);
    void delete(TransactionLog log);

    RemovedRelationshipLog findOne(Long id);
    RemovedRelationshipLog findOne(Long eventId, Long appId);
    List<RemovedRelationshipLog> findAll(DateTime dateTime, Long appId);
}
