/*
 * Carrot - beacon management
 * Copyright (C) 2015 Heiko Dreyer
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

package com.boxedfolder.carrot.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
public final class GeneralExceptions {
    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Entity not found.")
    public static class NotFoundException extends RuntimeException {
    }

    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY, reason = "Entity already exists.")
    public static class AlreadyExistsException extends RuntimeException {
    }

    @ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Application key invalid.")
    public static class InvalidAppKey extends RuntimeException {
    }

    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY, reason = "Analytics log invalid.")
    public static class InvalidLog extends RuntimeException {
    }
}
