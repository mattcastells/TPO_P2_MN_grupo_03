package uade.api.tpo_p2_mn_grupo_03.service.impl.userService.exception;

import uade.api.tpo_p2_mn_grupo_03.exception.NotFoundException;

/**
 * Exception thrown when a user is not found.
 */
public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException(Long id) {
        super("User", id);
    }

    public UserNotFoundException(String username) {
        super("User", username);
    }
} 