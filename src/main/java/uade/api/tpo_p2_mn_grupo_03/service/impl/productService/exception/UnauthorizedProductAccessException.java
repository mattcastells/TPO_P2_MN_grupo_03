package uade.api.tpo_p2_mn_grupo_03.service.impl.productService.exception;

import uade.api.tpo_p2_mn_grupo_03.exception.BadRequestException;

/**
 * Exception thrown when a user tries to access a product they don't own.
 */
public class UnauthorizedProductAccessException extends BadRequestException {
    public UnauthorizedProductAccessException(String action) {
        super("No tienes permisos para " + action + " este producto");
    }
}
