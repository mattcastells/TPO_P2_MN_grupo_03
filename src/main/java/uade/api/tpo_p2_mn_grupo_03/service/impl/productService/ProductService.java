package uade.api.tpo_p2_mn_grupo_03.service.impl.productService;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import uade.api.tpo_p2_mn_grupo_03.dto.request.CreateProductRequestDTO;
import uade.api.tpo_p2_mn_grupo_03.dto.request.UpdateProductRequestDTO;
import uade.api.tpo_p2_mn_grupo_03.dto.response.CategoryResponseDTO;
import uade.api.tpo_p2_mn_grupo_03.dto.response.ProductPaginatedResponseDTO;
import uade.api.tpo_p2_mn_grupo_03.dto.response.ProductResponseDTO;
import uade.api.tpo_p2_mn_grupo_03.dto.filter.AppliedFilterDTO;
import uade.api.tpo_p2_mn_grupo_03.dto.filter.AvailableFilterDTO;
import uade.api.tpo_p2_mn_grupo_03.mapper.ProductMapper;
import uade.api.tpo_p2_mn_grupo_03.model.Category;
import uade.api.tpo_p2_mn_grupo_03.model.Product;
import uade.api.tpo_p2_mn_grupo_03.model.ProductDetail;
import uade.api.tpo_p2_mn_grupo_03.model.ProductImage;
import uade.api.tpo_p2_mn_grupo_03.model.User;
import uade.api.tpo_p2_mn_grupo_03.repository.ProductDetailRepository;
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

    @Autowired
    private ProductDetailRepository productDetailRepository;

    @Autowired
    private ProductMapper productMapper;

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
        Product product = productMapper.toEntity(dto, category, user);
        product.setDetails(dto.getDetails().stream()
            .map(detailDTO -> {
                ProductDetail detail = new ProductDetail();
                detail.setName(detailDTO.getName());
                detail.setDescription(detailDTO.getDescription());
                detail.setProduct(product);
                return detail;
            }).collect(Collectors.toList()));
        
        Product savedProduct = productRepository.save(product);

        return productMapper.toResponse(savedProduct);
    }

    /**
     * Retrieves all products as response DTOs.
     *
     * @return A list of product response DTOs
     */
    @Override
    public List<ProductResponseDTO> getAllProducts() {
        return productRepository.findAll().stream()
            .map(productMapper::toResponse)
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
        product = productMapper.updateEntity(product, dto, updatedCategory, user);

        Product updated = productRepository.save(product);
        return productMapper.toResponse(updated, true);
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
        Product product = productRepository.findById(productId).orElse(null);

        if (product == null) {
            return;
        }

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
        Product product = productRepository.findByIdWithDetails(productId)
            .orElseThrow(() -> new ProductNotFoundException(productId));

        return productMapper.toResponse(product, true);
    }

    /**
     * Retrieves products with optional filters and pagination.
     * Only products with available stock are returned.
     *
     * @param categoryNames List of category names to filter by (optional)
     * @param offset        The starting index for pagination (default is 0)
     * @param limit         The maximum number of products to return (default is 10)
     * @return A list of filtered product response DTOs
     */
    public ProductPaginatedResponseDTO getFilteredProducts(
            List<Long> categoryIds,
            Double priceLessThan,
            Double priceGreaterThan,
            Integer stockLessThan,
            Integer stockGreaterThan,
            Long sellerId,
            String name,
            Integer offset,
            Integer limit
    ) {
        if(stockGreaterThan == null || (stockGreaterThan != null && stockGreaterThan <= 0)) {
            stockGreaterThan = 1;
        }
        Pageable pageable = PageRequest.of(offset / limit, limit);
        
        Page<Product> page = productRepository.findWithFilters(
            categoryIds,
            priceLessThan,
            priceGreaterThan,
            stockLessThan,
            stockGreaterThan,
            sellerId,
            name,
            pageable
        );
        Map<String, Object> availableFiltersMap = productRepository.findAvailableFilterValues(
            categoryIds,
            priceLessThan,
            priceGreaterThan,
            stockLessThan,
            stockGreaterThan,
            sellerId,
            name);

        String categoriesString = Optional.ofNullable(
            availableFiltersMap.get("categoryIds")
            ).map(Object::toString)
            .orElse(null);
        List<Long> availableCategoriesIds = categoriesString != null ? Arrays.asList(categoriesString.split(","))
            .stream()
            .map(Long::parseLong)
            .collect(Collectors.toList())
            : new ArrayList<>();
        List<Category> availableCategories = availableCategoriesIds.isEmpty() ?
        new ArrayList<>() 
        : categoryService.findAllByParentId(availableCategoriesIds).stream()
        .filter(category -> {
            return categoryIds == null || !categoryIds.contains(category.getId());
        }).collect(Collectors.toList());
        List<AvailableFilterDTO> availableFilters = ProductPaginatedResponseDTO
        .getAvailableFilters(availableFiltersMap, availableCategories);
        List<AppliedFilterDTO> appliedFilters = ProductPaginatedResponseDTO.getAppliedFilters(
            categoryIds == null || categoryIds.isEmpty() ? new ArrayList<>() : categoryService.findAllById(categoryIds),
            priceLessThan,
            priceGreaterThan,
            stockLessThan,
            stockGreaterThan,
            sellerId,
            name,
            offset,
            limit
        );
        return new ProductPaginatedResponseDTO(
            availableFilters,
            appliedFilters,
            (int) page.getTotalElements(),
            offset,
            limit,
            page.getContent().stream().map(productMapper::toResponse).collect(Collectors.toList())
        );
    }

    @Override
    public byte[] getProductImage(Long productId, Long imageId) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new ProductNotFoundException(productId));

        return product.getImageFiles().stream()
            .filter(img -> img.getId().equals(imageId))
            .findFirst()
            .map(ProductImage::getData)
            .orElseThrow(() -> new ProductNotFoundException("Image not found for product " + productId));
    }
}
