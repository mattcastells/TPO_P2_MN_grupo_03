package uade.api.tpo_p2_mn_grupo_03.service.impl.userService.exception;

import uade.api.tpo_p2_mn_grupo_03.exception.BadRequestException;

/**
 * Exception thrown when a user is not found.
 */
public class UserAlreadyExistsException extends BadRequestException {
    public UserAlreadyExistsException() {
        super("User already exists");
    }
} 