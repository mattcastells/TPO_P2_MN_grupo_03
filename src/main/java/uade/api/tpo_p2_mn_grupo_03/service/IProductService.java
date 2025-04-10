package uade.api.tpo_p2_mn_grupo_03.service;

import uade.api.tpo_p2_mn_grupo_03.dto.request.CreateProductRequestDTO;
import uade.api.tpo_p2_mn_grupo_03.dto.request.UpdateProductRequestDTO;
import uade.api.tpo_p2_mn_grupo_03.dto.response.ProductPaginatedResponseDTO;
import uade.api.tpo_p2_mn_grupo_03.dto.response.ProductResponseDTO;
import uade.api.tpo_p2_mn_grupo_03.model.User;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service interface for product operations.
 */
public interface IProductService {

    /**
     * Creates a new product.
     *
     * @param dto The product creation data
     * @return The created product as a response DTO
     */
    ProductResponseDTO createProduct(CreateProductRequestDTO dto, User user);

    /**
     * Retrieves all products.
     *
     * @return A list of product response DTOs
     */
    List<ProductResponseDTO> getAllProducts();

    /**
     * Finds a product by its ID.
     *
     * @param id The ID of the product to retrieve
     * @return The product as a response DTO
     */
    ProductResponseDTO findById(Long id);

    /**
     * Updates an existing product.
     *
     * @param id The ID of the product to update
     * @param dto The updated product data
     * @return The updated product as a response DTO
     */
    ProductResponseDTO updateProduct(Long productId, UpdateProductRequestDTO dto, User user);

    /**
     * Deletes a product by its ID.
     *
     * @param id The ID of the product to delete
     */
    void deleteProduct(Long productId, User user);

    /**
     * Retrieves products with optional filters and pagination.
     * Only products with available stock are returned.
     *
     * @param categoryNames List of category names to filter by (optional)
     * @param offset        The starting index for pagination (default is 0)
     * @param limit         The maximum number of products to return (default is 10)
     * @return A list of filtered product response DTOs
     */
    ProductPaginatedResponseDTO getFilteredProducts(
        List<Long> categoryIds,
        Double priceLessThan,
        Double priceGreaterThan,
        Integer stockLessThan,
        Integer stockGreaterThan,
        Long sellerId,
        int offset,
        int limit
    );


}
