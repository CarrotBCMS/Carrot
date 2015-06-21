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
}
