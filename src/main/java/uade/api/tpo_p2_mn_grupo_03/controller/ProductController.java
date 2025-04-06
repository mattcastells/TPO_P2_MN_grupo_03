package uade.api.tpo_p2_mn_grupo_03.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uade.api.tpo_p2_mn_grupo_03.dto.response.ProductResponseDTO;
import uade.api.tpo_p2_mn_grupo_03.service.IProductService;

/**
 * Controller for product operations.
 */
@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private IProductService productService;

    /**
     * Finds a product by ID.
     *
     * @param id The ID of the product to find
     * @return The product DTO
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.findById(id));
    }
} 