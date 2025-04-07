package uade.api.tpo_p2_mn_grupo_03.service.impl.productService;

import java.time.Instant;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uade.api.tpo_p2_mn_grupo_03.dto.request.CreateProductRequestDTO;
import uade.api.tpo_p2_mn_grupo_03.dto.request.UpdateProductRequestDTO;
import uade.api.tpo_p2_mn_grupo_03.dto.response.ProductResponseDTO;
import uade.api.tpo_p2_mn_grupo_03.model.Product;
import uade.api.tpo_p2_mn_grupo_03.repository.CategoryRepository;
import uade.api.tpo_p2_mn_grupo_03.repository.ProductRepository;
import uade.api.tpo_p2_mn_grupo_03.service.impl.productService.exception.ProductNotFoundException;
import uade.api.tpo_p2_mn_grupo_03.service.IProductService;
import uade.api.tpo_p2_mn_grupo_03.model.Category;

/**
 * Service implementation for handling product operations.
 */
@Service
public class ProductService implements IProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    /**
     * Creates a new product and persists it.
     *
     * @param dto The product creation data
     * @return The created product as a response DTO
     */
    @Override
    public ProductResponseDTO createProduct(CreateProductRequestDTO dto) {
        // Find category
        Category category = categoryRepository.findById(dto.getCategoryId())
            .orElseThrow(() -> new RuntimeException("Category not found"));

        // TODO: Add seller logic when available
        // User seller = userRepository.findById(dto.getSellerId())
        //     .orElseThrow(() -> new RuntimeException("Seller not found"));
        // product.setSeller(seller);

        // Map DTO to entity
        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStock(dto.getStock());
        product.setImages(dto.getImages());
        product.setCategory(category);
        product.setCreatedAt(Instant.now());
        product.setUpdatedAt(Instant.now());

        // Persist product
        Product savedProduct = productRepository.save(product);

        // Build and return response DTO
        return ProductResponseDTO.builder()
            .id(savedProduct.getId())
            .name(savedProduct.getName())
            .description(savedProduct.getDescription())
            .price(savedProduct.getPrice())
            .stock(savedProduct.getStock())
            .images(savedProduct.getImages())
            .categoryId(savedProduct.getCategory().getId())
            .sellerId(savedProduct.getSeller() != null ? savedProduct.getSeller().getId() : null)
            .createdAt(savedProduct.getCreatedAt())
            .updatedAt(savedProduct.getUpdatedAt())
            .build();
    }

    /**
     * Retrieves all products as response DTOs.
     *
     * @return List of product response DTOs
     */
    @Override
    public List<ProductResponseDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::convertToDTO)
                .toList();
    }

    /**
     * Updates a product by its ID.
     *
     * @param id The ID of the product to update
     * @param dto The updated product data
     * @return The updated product as a response DTO
     */
    @Override
    public ProductResponseDTO updateProduct(Long id, UpdateProductRequestDTO dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStock(dto.getStock());
        product.setImages(dto.getImages());
        product.setCategory(category);
        product.setUpdatedAt(Instant.now());

        return convertToDTO(productRepository.save(product));
    }

    /**
     * Deletes a product by its ID.
     *
     * @param id The ID of the product to delete
     */
    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        productRepository.delete(product);
    }

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
                .sellerId(product.getSeller() != null ? product.getSeller().getId() : null)
                .build();
    }
}
