package uade.api.tpo_p2_mn_grupo_03.service.impl.productService.exception;

import uade.api.tpo_p2_mn_grupo_03.exception.NotFoundException;

/**
 * Exception thrown when a product is not found.
 */
public class ProductNotFoundException extends NotFoundException {
    public ProductNotFoundException(Long id) {
        super("Product", id);
    }
} 