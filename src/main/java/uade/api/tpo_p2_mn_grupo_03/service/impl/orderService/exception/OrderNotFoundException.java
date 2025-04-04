package uade.api.tpo_p2_mn_grupo_03.service.impl.orderService.exception;

import uade.api.tpo_p2_mn_grupo_03.exception.NotFoundException;

/**
 * Exception thrown when an order is not found.
 */
public class OrderNotFoundException extends NotFoundException {
    public OrderNotFoundException(Long id) {
        super("Order", id);
    }
} 