package uade.api.tpo_p2_mn_grupo_03.service;

import java.util.List;

import uade.api.tpo_p2_mn_grupo_03.dto.request.CategoryRequestDTO;
import uade.api.tpo_p2_mn_grupo_03.dto.response.CategoryResponseDTO;

/**
 * Service interface for category operations.
 */
public interface ICategoryService {

    /**
     * Finds a category by its ID.
     *
     * @param id The ID of the category to find
     * @return The category as a response DTO
     */
    CategoryResponseDTO findById(Long id);

    /**
     * Creates a new category.
     *
     * @param categoryRequestDTO The category creation request
     * @return The created category as a response DTO
     */
    CategoryResponseDTO create(CategoryRequestDTO categoryRequestDTO);

    List<CategoryResponseDTO> findAll();

}
