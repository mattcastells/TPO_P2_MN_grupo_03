package uade.api.tpo_p2_mn_grupo_03.service;

import java.util.List;

import uade.api.tpo_p2_mn_grupo_03.dto.request.CategoryPatchRequestDTO;
import uade.api.tpo_p2_mn_grupo_03.dto.request.CategoryRequestDTO;
import uade.api.tpo_p2_mn_grupo_03.dto.response.CategoryResponseDTO;
import uade.api.tpo_p2_mn_grupo_03.model.Category;

/**
 * Service interface for category operations.
 */
public interface ICategoryService {
    CategoryResponseDTO findById(Long id);
    CategoryResponseDTO create(CategoryRequestDTO categoryRequestDTO);
    List<CategoryResponseDTO> findAll();
    CategoryResponseDTO update(CategoryPatchRequestDTO categoryRequestDTO);
    void delete(Long id);
    List<Category> findAllById(List<Long> ids);
    List<Category> findAllByParentId(List<Long> parentIds);
} 
