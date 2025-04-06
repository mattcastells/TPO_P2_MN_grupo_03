package uade.api.tpo_p2_mn_grupo_03.unit.service.impl.categoryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uade.api.tpo_p2_mn_grupo_03.dto.request.CategoryRequestDTO;
import uade.api.tpo_p2_mn_grupo_03.dto.response.CategoryResponseDTO;
import uade.api.tpo_p2_mn_grupo_03.exception.DuplicateEntityException;
import uade.api.tpo_p2_mn_grupo_03.model.Category;
import uade.api.tpo_p2_mn_grupo_03.repository.CategoryRepository;
import uade.api.tpo_p2_mn_grupo_03.service.impl.categoryService.CategoryService;
import uade.api.tpo_p2_mn_grupo_03.service.impl.categoryService.exception.CategoryNotFoundException;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    private CategoryRequestDTO categoryRequestDTO;
    private Category category;
    private CategoryResponseDTO expectedResponse;

    @BeforeEach
    void setUp() {
        categoryRequestDTO = new CategoryRequestDTO();
        categoryRequestDTO.setName("Test Category");

        category = new Category();
        category.setId(1L);
        category.setName("Test Category");
        category.setCreatedAt(Instant.now());
        category.setUpdatedAt(Instant.now());

        expectedResponse = CategoryResponseDTO.builder()
                .id(1L)
                .name("Test Category")
                .createdAt(category.getCreatedAt())
                .updatedAt(category.getUpdatedAt())
                .build();
    }

    @Test
    void findById_WhenCategoryExists_ShouldReturnCategory() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        CategoryResponseDTO result = categoryService.findById(1L);

        assertNotNull(result);
        assertEquals(expectedResponse.getId(), result.getId());
        assertEquals(expectedResponse.getName(), result.getName());
        verify(categoryRepository).findById(1L);
    }

    @Test
    void findById_WhenCategoryDoesNotExist_ShouldThrowNotFoundException() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(CategoryNotFoundException.class, () -> categoryService.findById(1L));
        verify(categoryRepository).findById(1L);
    }

    @Test
    void create_WhenCategoryDoesNotExist_ShouldCreateNewCategory() {
        when(categoryRepository.findByName("Test Category")).thenReturn(Optional.empty());
        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        CategoryResponseDTO result = categoryService.create(categoryRequestDTO);

        assertNotNull(result);
        assertEquals(expectedResponse.getId(), result.getId());
        assertEquals(expectedResponse.getName(), result.getName());
        verify(categoryRepository).findByName("Test Category");
        verify(categoryRepository).save(any(Category.class));
    }

    @Test
    void create_WhenCategoryExists_ShouldThrowDuplicateEntityException() {
        when(categoryRepository.findByName("Test Category")).thenReturn(Optional.of(category));

        assertThrows(DuplicateEntityException.class, () -> categoryService.create(categoryRequestDTO));
        verify(categoryRepository).findByName("Test Category");
        verify(categoryRepository, never()).save(any(Category.class));
    }
} 