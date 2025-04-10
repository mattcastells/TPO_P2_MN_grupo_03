package uade.api.tpo_p2_mn_grupo_03.service.impl.orderService.exception;

import uade.api.tpo_p2_mn_grupo_03.exception.BadRequestException;

/**
 * Exception thrown when an order is not found.
 */
public class DuplicatedPendingOrderException extends BadRequestException {
    public DuplicatedPendingOrderException() {
        super("The user already has a pending order");
    }
} 