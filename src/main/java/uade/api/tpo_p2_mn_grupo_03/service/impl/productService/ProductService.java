package uade.api.tpo_p2_mn_grupo_03.service.impl.productService;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uade.api.tpo_p2_mn_grupo_03.dto.request.CreateProductRequestDTO;
import uade.api.tpo_p2_mn_grupo_03.dto.request.UpdateProductRequestDTO;
import uade.api.tpo_p2_mn_grupo_03.dto.response.CategoryResponseDTO;
import uade.api.tpo_p2_mn_grupo_03.dto.response.ProductResponseDTO;
import uade.api.tpo_p2_mn_grupo_03.mapper.ProductMapper;
import uade.api.tpo_p2_mn_grupo_03.model.Category;
import uade.api.tpo_p2_mn_grupo_03.model.Product;
import uade.api.tpo_p2_mn_grupo_03.model.User;
import uade.api.tpo_p2_mn_grupo_03.repository.ProductRepository;
import uade.api.tpo_p2_mn_grupo_03.service.IProductService;
import uade.api.tpo_p2_mn_grupo_03.service.impl.categoryService.CategoryService;
import uade.api.tpo_p2_mn_grupo_03.service.impl.productService.exception.ProductNotFoundException;
import uade.api.tpo_p2_mn_grupo_03.service.impl.productService.exception.UnauthorizedProductAccessException;

/**
 * Service implementation for handling product operations.
 */
@Service
public class ProductService implements IProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryService categoryService;

    /**
     * Creates a new product and persists it.
     *
     * @param dto  The product creation data
     * @param user The authenticated user creating the product
     * @return The created product as a response DTO
     */
    @Override
    public ProductResponseDTO createProduct(CreateProductRequestDTO dto, User user) {
        // Retrieve category from service
        CategoryResponseDTO categoryDTO = categoryService.findById(dto.getCategoryId());
        Category category = new Category();
        category.setId(categoryDTO.getId());

        // Map DTO to entity and persist
        Product product = ProductMapper.toEntity(dto, category, user);
        Product savedProduct = productRepository.save(product);

        return ProductMapper.toResponse(savedProduct);
    }

    /**
     * Retrieves all products as response DTOs.
     *
     * @return A list of product response DTOs
     */
    @Override
    public List<ProductResponseDTO> getAllProducts() {
        return productRepository.findAll().stream()
            .map(ProductMapper::toResponse)
            .collect(Collectors.toList());
    }

    /**
     * Updates a product by its ID.
     *
     * @param productId The ID of the product to update
     * @param dto       The updated product data
     * @param user      The authenticated user attempting the update
     * @return The updated product as a response DTO
     * @throws ProductNotFoundException           if the product does not exist
     * @throws UnauthorizedProductAccessException if the user is not the product's seller
     */
    @Override
    public ProductResponseDTO updateProduct(Long productId, UpdateProductRequestDTO dto, User user) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new ProductNotFoundException(productId));

        if (!product.getSeller().getId().equals(user.getId())) {
            throw new UnauthorizedProductAccessException("update");
        }

        // Load new category if changed
        Category updatedCategory = null;
        if (dto.getCategoryId() != null && !dto.getCategoryId().equals(product.getCategory().getId())) {
            CategoryResponseDTO categoryDTO = categoryService.findById(dto.getCategoryId());
            updatedCategory = new Category();
            updatedCategory.setId(categoryDTO.getId());
        }

        // Apply changes
        ProductMapper.updateEntity(product, dto, updatedCategory);

        Product updated = productRepository.save(product);
        return ProductMapper.toResponse(updated);
    }

    /**
     * Deletes a product by its ID.
     *
     * @param productId The ID of the product to delete
     * @param user      The authenticated user attempting the deletion
     * @throws ProductNotFoundException           if the product does not exist
     * @throws UnauthorizedProductAccessException if the user is not the product's seller
     */
    @Override
    public void deleteProduct(Long productId, User user) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new ProductNotFoundException(productId));

        if (!product.getSeller().getId().equals(user.getId())) {
            throw new UnauthorizedProductAccessException("delete");
        }

        productRepository.delete(product);
    }

    /**
     * Finds a product by ID and returns it as a response DTO.
     *
     * @param productId The ID of the product to find
     * @return The product response DTO
     * @throws ProductNotFoundException if the product does not exist
     */
    @Override
    public ProductResponseDTO findById(Long productId) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new ProductNotFoundException(productId));

        return ProductMapper.toResponse(product);
    }
}
