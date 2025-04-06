package uade.api.tpo_p2_mn_grupo_03.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import uade.api.tpo_p2_mn_grupo_03.dto.request.CategoryRequestDTO;
import uade.api.tpo_p2_mn_grupo_03.dto.response.CategoryResponseDTO;
import uade.api.tpo_p2_mn_grupo_03.service.ICategoryService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CategoryControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ICategoryService categoryService;

    @Test
    void createCategory_WhenValidRequest_ShouldReturnCreated() throws Exception {
        CategoryRequestDTO request = new CategoryRequestDTO();
        request.setName("Test Category");

        mockMvc.perform(post("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Category"));
    }

    @Test
    void createCategory_WhenDuplicateName_ShouldReturnConflict() throws Exception {
        // Primero creamos una categoría
        CategoryRequestDTO request = new CategoryRequestDTO();
        request.setName("Duplicate Category");
        categoryService.create(request);

        // Intentamos crear otra con el mismo nombre
        mockMvc.perform(post("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.code").value("DUPLICATE_ENTITY"));
    }

    @Test
    void findById_WhenCategoryExists_ShouldReturnCategory() throws Exception {
        // Primero creamos una categoría
        CategoryRequestDTO request = new CategoryRequestDTO();
        request.setName("Find Category");
        CategoryResponseDTO category = categoryService.create(request);

        mockMvc.perform(get("/categories/{id}", category.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(category.getId()))
                .andExpect(jsonPath("$.name").value("Find Category"));
    }

    @Test
    void findById_WhenCategoryDoesNotExist_ShouldReturnNotFound() throws Exception {
        mockMvc.perform(get("/categories/{id}", 999L))
                .andExpect(status().isNotFound());
    }
} 