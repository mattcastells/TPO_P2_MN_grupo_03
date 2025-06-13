package uade.api.tpo_p2_mn_grupo_03.service.impl.categoryService;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import uade.api.tpo_p2_mn_grupo_03.dto.request.CategoryPatchRequestDTO;
import uade.api.tpo_p2_mn_grupo_03.dto.request.CategoryRequestDTO;
import uade.api.tpo_p2_mn_grupo_03.dto.response.CategoryResponseDTO;
import uade.api.tpo_p2_mn_grupo_03.exception.DuplicateEntityException;
import uade.api.tpo_p2_mn_grupo_03.mapper.CategoryMapper;
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
    private CategoryMapper categoryMapper;

    @Autowired
    private ProductRepository productRepository;
    
    @Override
    public List<CategoryResponseDTO> findAll() {
        return categoryRepository.findAll()
            .stream()
            .map(categoryMapper::toResponseDTO)
            .collect(Collectors.toList());
    }

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
        .map(categoryMapper::toResponseDTO)
                .orElseThrow(() -> new CategoryNotFoundException(id));
    }

    @Override
    public List<Category> findAllById(List<Long> ids) {
        return categoryRepository.findAllById(ids);
    }

    @Override
    public List<Category> findAllByParentId(List<Long> parentIds) {
        return categoryRepository.findAllByParentIdIn(parentIds);
    }

    @Override
    public CategoryResponseDTO create(CategoryRequestDTO categoryRequestDTO) {
        categoryRepository.findByNameIgnoreCase(categoryRequestDTO.getName())
            .ifPresent(category -> {
                throw new DuplicateEntityException("Category already exists");
            });
        Category parent = categoryRequestDTO.getParentId() != null ? categoryRepository.findById(categoryRequestDTO.getParentId())
            .orElseThrow(() -> new CategoryNotFoundException(categoryRequestDTO.getParentId())) : null;
        Category category = new Category(categoryRequestDTO.getName().toLowerCase(), parent);
        Category savedCategory = categoryRepository.save(category);
        return categoryMapper.toResponseDTO(savedCategory);
    }

    //Encuentra y actualiza el nombre de una categoria si ningun producto la utiliza
    @Override
    @Transactional
    public CategoryResponseDTO update(CategoryPatchRequestDTO categoryPatchRequestDTO){
        Category category = categoryRepository.findById(categoryPatchRequestDTO.getId())
            .orElseThrow(() -> new CategoryNotFoundException(categoryPatchRequestDTO.getId()));
        if(categoryPatchRequestDTO.getName() != null) {
            category.setName(categoryPatchRequestDTO.getName());
        }
        if(categoryPatchRequestDTO.getParentId() != null) {
            category.setParent(categoryPatchRequestDTO.getParentId() != null ? categoryRepository.findById(categoryPatchRequestDTO.getParentId())
            .orElseThrow(() -> new CategoryNotFoundException(categoryPatchRequestDTO.getParentId())) : null);
        }
        categoryRepository.save(category);
        return categoryMapper.toResponseDTO(category);
    }

    //Encuentra y borra el nombre de una categoría si ningún producto la utiliza
    @Override
    @Transactional
    public void delete(Long id) {
        Category category = categoryRepository.findById(id).orElse(null);
        if(category == null) {
            return;
        }
        if (categoryIsUsed(category) ) {
            throw new CategoryIsUsedException();
        } 
        categoryRepository.delete(category);
    }

    private boolean categoryIsUsed(Category category) {
        return !productRepository.findByCategory(category).isEmpty();
    }


}
