package uade.api.tpo_p2_mn_grupo_03.service;

import uade.api.tpo_p2_mn_grupo_03.dto.request.CreateProductRequestDTO;
import uade.api.tpo_p2_mn_grupo_03.dto.request.UpdateProductRequestDTO;
import uade.api.tpo_p2_mn_grupo_03.dto.response.ProductResponseDTO;

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
    ProductResponseDTO createProduct(CreateProductRequestDTO dto);

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
    ProductResponseDTO updateProduct(Long id, UpdateProductRequestDTO dto);

    /**
     * Deletes a product by its ID.
     *
     * @param id The ID of the product to delete
     */
    void deleteProduct(Long id);
}
