package uade.api.tpo_p2_mn_grupo_03.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import uade.api.tpo_p2_mn_grupo_03.dto.request.CreateProductRequestDTO;
import uade.api.tpo_p2_mn_grupo_03.dto.request.UpdateProductRequestDTO;
import uade.api.tpo_p2_mn_grupo_03.dto.response.ProductResponseDTO;
import uade.api.tpo_p2_mn_grupo_03.model.User;
import uade.api.tpo_p2_mn_grupo_03.service.IProductService;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;


/**
 * Controller for handling product-related operations.
 */
@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private IProductService productService;

    /**
     * Creates a new product.
     *
     * @param dto The product data
     * @return The created product DTO
     */
    @PostMapping(consumes = "multipart/form-data")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<ProductResponseDTO> createProduct(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("price") Double price,
            @RequestParam("stock") Integer stock,
            @RequestParam("category_id") Long categoryId,
            @RequestParam("images") List<MultipartFile> images,
            Authentication authentication) {

        User user = (User) authentication.getPrincipal();

        CreateProductRequestDTO dto = new CreateProductRequestDTO();
        dto.setName(name);
        dto.setDescription(description);
        dto.setPrice(price);
        dto.setStock(stock);
        dto.setCategoryId(categoryId);
        dto.setImageFiles(images);

        ProductResponseDTO createdProduct = productService.createProduct(dto, user);
        return ResponseEntity.ok(createdProduct);
    }

    /**
     * Retrieves all products.
     *
     * @return A list of product DTOs
     */
    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    /**
     * Retrieves a product by its ID.
     *
     * @param id The ID of the product to retrieve
     * @return The product DTO
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.findById(id));
    }

    /**
     * Updates an existing product.
     *
     * @param id The ID of the product to update
     * @param dto The updated product data
     * @return The updated product DTO
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<ProductResponseDTO> updateProduct(
            @PathVariable Long id,
            @RequestBody UpdateProductRequestDTO dto,
            Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(productService.updateProduct(id, dto, user));
    }

    /**
     * Deletes a product by its ID.
     *
     * @param id The ID of the product to delete
     * @return 204 No Content
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        productService.deleteProduct(id, user);
        return ResponseEntity.noContent().build();
    }
    
}
