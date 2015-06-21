package com.boxedfolder.carrot.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
public final class GeneralExceptions {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public static class NotFoundException extends RuntimeException {
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public static class AlreadyExistsException extends RuntimeException {
    }
}
