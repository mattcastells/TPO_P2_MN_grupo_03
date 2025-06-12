package uade.api.tpo_p2_mn_grupo_03.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
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

import com.fasterxml.jackson.databind.ObjectMapper;

import uade.api.tpo_p2_mn_grupo_03.dto.request.CreateProductRequestDTO;
import uade.api.tpo_p2_mn_grupo_03.dto.request.ProductDetailDTO;
import uade.api.tpo_p2_mn_grupo_03.dto.request.UpdateProductRequestDTO;
import uade.api.tpo_p2_mn_grupo_03.dto.response.ProductPaginatedResponseDTO;
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

    @Autowired
    private ObjectMapper mapper;

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
            @RequestParam(value = "details", required = false) String detailsJson,
            Authentication authentication) {

        User user = (User) authentication.getPrincipal();

        CreateProductRequestDTO dto = new CreateProductRequestDTO();
        dto.setName(name);
        dto.setDescription(description);
        dto.setPrice(price);
        dto.setStock(stock);
        dto.setCategoryId(categoryId);
        dto.setImageFiles(images);

        if (detailsJson != null && !detailsJson.isEmpty()) {
            try {
                List<ProductDetailDTO> details = mapper.readValue(detailsJson, 
                    mapper.getTypeFactory().constructCollectionType(List.class, ProductDetailDTO.class));
                dto.setDetails(details);
            } catch (Exception e) {
                throw new RuntimeException("Error parsing details JSON", e);
            }
        }
        ProductResponseDTO createdProduct = productService.createProduct(dto, user);
        return ResponseEntity.ok(createdProduct);
    }

    /**
     * Retrieves all products.
     *
     * @return A list of product DTOs
     */
    @GetMapping
    public ResponseEntity<ProductPaginatedResponseDTO> getAllProducts(
            @RequestParam(required = false) List<Long> categoryIds,
            @RequestParam(required = false) Double priceLessThan,
            @RequestParam(required = false) Double priceGreaterThan,
            @RequestParam(required = false) Integer stockLessThan,
            @RequestParam(required = false) Integer stockGreaterThan,
            @RequestParam(required = false) Long sellerId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false, defaultValue = "0") Integer offset,
            @RequestParam(required = false, defaultValue = "20") Integer limit
    ) {
        ProductPaginatedResponseDTO products = productService.getFilteredProducts(
            categoryIds, priceLessThan, priceGreaterThan, stockLessThan, stockGreaterThan,
            sellerId, name, offset, limit
        );
        return ResponseEntity.ok(products);
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
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) Double price,
            @RequestParam(required = false) Integer stock,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) List<MultipartFile> images,
            @RequestParam(value = "details", required = false) String detailsJson,
            Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        UpdateProductRequestDTO dto = new UpdateProductRequestDTO();
        dto.setName(name);
        dto.setDescription(description);
        dto.setPrice(price);
        dto.setStock(stock);
        dto.setCategoryId(categoryId);
        dto.setImageFiles(images);

        if (detailsJson != null && !detailsJson.isEmpty()) {
            try {
                List<ProductDetailDTO> details = mapper.readValue(detailsJson, 
                    mapper.getTypeFactory().constructCollectionType(List.class, ProductDetailDTO.class));
                dto.setDetails(details);
            } catch (Exception e) {
                throw new RuntimeException("Error parsing details JSON", e);
            }
        }
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

    /**
     * Retrieves a product image by its ID.
     *
     * @param productId The ID of the product
     * @param imageId The ID of the image
     * @return The image data
     */
    @GetMapping("/{productId}/image/{imageId}")
    public ResponseEntity<byte[]> getProductImage(
            @PathVariable Long productId,
            @PathVariable Long imageId) {
        byte[] imageData = productService.getProductImage(productId, imageId);
        return ResponseEntity.ok()
            .contentType(MediaType.IMAGE_JPEG)
            .body(imageData);
    }
}
