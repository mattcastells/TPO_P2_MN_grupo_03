package uade.api.tpo_p2_mn_grupo_03.service.impl.productService.exception;

import uade.api.tpo_p2_mn_grupo_03.exception.BadRequestException;

/**
 * Exception thrown when a product is not found.
 */
public class ProductNotEnoughStockException extends BadRequestException {
    public ProductNotEnoughStockException(Long id, Integer stock) {
        super(String.format("Product with ID %d has only %d units in stock", id, stock));
    }
} 