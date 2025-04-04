package uade.api.tpo_p2_mn_grupo_03.service.impl.categoryService.exception;

import uade.api.tpo_p2_mn_grupo_03.exception.NotFoundException;

/**
 * Exception thrown when a category is not found.
 */
public class CategoryNotFoundException extends NotFoundException {
    public CategoryNotFoundException(Long id) {
        super("Category", id);
    }
} 