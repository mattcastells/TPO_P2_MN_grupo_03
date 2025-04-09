package uade.api.tpo_p2_mn_grupo_03.service.impl.categoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uade.api.tpo_p2_mn_grupo_03.dto.request.CategoryRequestDTO;
import uade.api.tpo_p2_mn_grupo_03.dto.response.CategoryResponseDTO;
import uade.api.tpo_p2_mn_grupo_03.exception.DuplicateEntityException;
import uade.api.tpo_p2_mn_grupo_03.model.Category;
import uade.api.tpo_p2_mn_grupo_03.repository.CategoryRepository;
import uade.api.tpo_p2_mn_grupo_03.service.ICategoryService;
import uade.api.tpo_p2_mn_grupo_03.service.impl.categoryService.exception.CategoryNotFoundException;

/**
 * Service for category operations.
 */
@Service
public class CategoryService implements ICategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    /**
     * Finds a category by ID and returns it as a DTO.
     *
     * @param id The ID of the category to find
     * @return The category DTO
     * @throws CategoryNotFoundException if the category is not found
     */
    @Override
    public CategoryResponseDTO findById(Long id) {
        return categoryRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new CategoryNotFoundException(id));
    }

    @Override
    public CategoryResponseDTO create(CategoryRequestDTO categoryRequestDTO) {
        categoryRepository.findByNameIgnoreCase(categoryRequestDTO.getName())
            .ifPresent(category -> {
                throw new DuplicateEntityException("Category already exists");
            });
        Category category = new Category(categoryRequestDTO.getName().toLowerCase());
        Category savedCategory = categoryRepository.save(category);
        return convertToDTO(savedCategory);
    }

    /**
     * Converts a Category entity to a CategoryResponseDTO.
     *
     * @param category The category entity to convert
     * @return The converted DTO
     */
    private CategoryResponseDTO convertToDTO(Category category) {
        return CategoryResponseDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .createdAt(category.getCreatedAt())
                .updatedAt(category.getUpdatedAt())
                .build();
    }

} 