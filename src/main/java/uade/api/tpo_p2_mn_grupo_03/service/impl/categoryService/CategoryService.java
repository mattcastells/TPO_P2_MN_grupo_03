package uade.api.tpo_p2_mn_grupo_03.service.impl.categoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import uade.api.tpo_p2_mn_grupo_03.dto.request.CategoryPatchRequestDTO;
import uade.api.tpo_p2_mn_grupo_03.dto.request.CategoryRequestDTO;
import uade.api.tpo_p2_mn_grupo_03.dto.response.CategoryResponseDTO;
import uade.api.tpo_p2_mn_grupo_03.exception.DuplicateEntityException;
import uade.api.tpo_p2_mn_grupo_03.model.Category;
import uade.api.tpo_p2_mn_grupo_03.repository.CategoryRepository;
import uade.api.tpo_p2_mn_grupo_03.repository.ProductRepository;
import uade.api.tpo_p2_mn_grupo_03.service.ICategoryService;
import uade.api.tpo_p2_mn_grupo_03.service.impl.categoryService.exception.CategoryIsUsedException;
import uade.api.tpo_p2_mn_grupo_03.service.impl.categoryService.exception.CategoryNotFoundException;

/**
 * Service for category operations.
 */
@Service
public class CategoryService implements ICategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

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

    //Encuentra y actualiza el nombre de una categoria si ningun producto la utiliza
    @Override
    @Transactional
    public CategoryResponseDTO update(CategoryPatchRequestDTO categoryPatchRequestDTO){
        Category category = categoryRepository.findById(categoryPatchRequestDTO.getId())
            .orElseThrow(() -> new CategoryNotFoundException(categoryPatchRequestDTO.getId()));
        if ( categoryIsUsed(category) ) {
            throw new CategoryIsUsedException();
        } else {
            category.setName(categoryPatchRequestDTO.getName());
            categoryRepository.save(category);
            return convertToDTO(category);
        }
    }

    //Encuentra y borra el nombre de una categoría si ningún producto la utiliza
    @Override
    @Transactional
    public CategoryResponseDTO delete(CategoryRequestDTO categoryRequestDTO) {
        Category category = categoryRepository.findByName(categoryRequestDTO.getName())
            .orElseThrow( () -> new CategoryNotFoundException("La categoria '" + categoryRequestDTO.getName() + "' no existe"));
        if ( categoryIsUsed(category) ) {
            throw new CategoryIsUsedException();
        } else {
            categoryRepository.delete(category);
            return convertToDTO(category);
        }
    }

    private boolean categoryIsUsed(Category category) {
        return !productRepository.findByCategory(category).isEmpty();
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