package uade.api.tpo_p2_mn_grupo_03.service.impl.productService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uade.api.tpo_p2_mn_grupo_03.dto.response.ProductResponseDTO;
import uade.api.tpo_p2_mn_grupo_03.model.Product;
import uade.api.tpo_p2_mn_grupo_03.repository.ProductRepository;
import uade.api.tpo_p2_mn_grupo_03.service.impl.productService.exception.ProductNotFoundException;
import uade.api.tpo_p2_mn_grupo_03.service.IProductService;

/**
 * Service for product operations.
 */
@Service
public class ProductService implements IProductService {
    @Autowired
    private ProductRepository productRepository;

    /**
     * Finds a product by ID and returns it as a DTO.
     *
     * @param id The ID of the product to find
     * @return The product DTO
     * @throws ProductNotFoundException if the product is not found
     */
    public ProductResponseDTO findById(Long id) {
        return productRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    /**
     * Converts a Product entity to a ProductResponseDTO.
     *
     * @param product The product entity to convert
     * @return The converted DTO
     */
    private ProductResponseDTO convertToDTO(Product product) {
        return ProductResponseDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(product.getStock())
                .images(product.getImages())
                .categoryId(product.getCategory().getId())
                .sellerId(product.getSeller().getId())
                .build();
    }
} 