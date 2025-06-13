package uade.api.tpo_p2_mn_grupo_03.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uade.api.tpo_p2_mn_grupo_03.model.Product;
import uade.api.tpo_p2_mn_grupo_03.model.Category;
import uade.api.tpo_p2_mn_grupo_03.model.User;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Repository interface for Product entity.
 * Provides methods to interact with the database for Product operations.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByIdIn(List<Long> ids);
    /**
     * Finds all products in a specific category.
     *
     * @param category The category to filter by
     * @return A list of products in the specified category
     */
    List<Product> findByCategory(Category category);

    /**
     * Finds all products from a specific seller.
     *
     * @param seller The seller to filter by
     * @return A list of products from the specified seller
     */
    List<Product> findBySeller(User seller);

    /**
     * Finds all products with stock greater than zero.
     *
     * @return A list of products with available stock
     */
    List<Product> findByStockGreaterThan(Integer stock);

    /**
     * Finds all products with a price less than or equal to the specified price.
     *
     * @param price The maximum price
     * @return A list of products within the price range
     */
    List<Product> findByPriceLessThanEqual(Double price);

    /**
     * Finds all products with a price greater than or equal to the specified price.
     *
     * @param price The minimum price
     * @return A list of products within the price range
     */
    List<Product> findByPriceGreaterThanEqual(Double price);

    /**
     * Finds all products that belong to any of the given category names.
     *
     * @param categoryNames The list of category names to filter by
     * @return A list of products belonging to the specified categories
     */
    List<Product> findByCategory_NameIn(List<String> categoryNames);
    List<Product> findByPriceBetweenAndCategoryId(Double minPrice, Double maxPrice, Long categoryId);

    @Query("SELECT p FROM Product p WHERE " +
           "(:categoryIds IS NULL OR p.category.id IN :categoryIds) AND " +
           "(:priceLessThan IS NULL OR p.price <= :priceLessThan) AND " +
           "(:priceGreaterThan IS NULL OR p.price > :priceGreaterThan) AND " +
           "(:stockLessThan IS NULL OR p.stock < :stockLessThan) AND " +
           "(:stockGreaterThan IS NULL OR p.stock >= :stockGreaterThan) AND " +
           "(:sellerId IS NULL OR p.seller.id = :sellerId) AND " +
           "(:name IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%')))")
    Page<Product> findWithFilters(
            @Param("categoryIds") List<Long> categoryIds,
            @Param("priceLessThan") Double priceLessThan,
            @Param("priceGreaterThan") Double priceGreaterThan,
            @Param("stockLessThan") Integer stockLessThan,
            @Param("stockGreaterThan") Integer stockGreaterThan,
            @Param("sellerId") Long sellerId,
            @Param("name") String name,
            Pageable pageable
    );

    @Query("SELECT new map(" +
           "MIN(p.price) as priceGreaterThan, " +
           "MAX(p.price) as priceLessThan, " +
           "MIN(p.stock) as stockGreaterThan, " +
           "MAX(p.stock) as stockLessThan, " +
           "GROUP_CONCAT(DISTINCT CAST(p.category.id AS string)) as categoryIds, " +
           "GROUP_CONCAT(DISTINCT CAST(p.seller.id AS string)) as sellerId, " +
           "GROUP_CONCAT(DISTINCT CAST(p.name AS string)) as name) " +
           "FROM Product p " +
           "WHERE " +
           "(:categoryIds IS NULL OR p.category.id IN :categoryIds) AND " +
           "(:priceLessThan IS NULL OR p.price <= :priceLessThan) AND " +
           "(:priceGreaterThan IS NULL OR p.price > :priceGreaterThan) AND " +
           "(:stockLessThan IS NULL OR p.stock < :stockLessThan) AND " +
           "(:stockGreaterThan IS NULL OR p.stock > :stockGreaterThan) AND " +
           "(:sellerId IS NULL OR p.seller.id = :sellerId) AND " +
           "(:name IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%')))")
    Map<String, Object> findAvailableFilterValues(
            @Param("categoryIds") List<Long> categoryIds,
            @Param("priceLessThan") Double priceLessThan,
            @Param("priceGreaterThan") Double priceGreaterThan,
            @Param("stockLessThan") Integer stockLessThan,
            @Param("stockGreaterThan") Integer stockGreaterThan,
            @Param("sellerId") Long sellerId,
            @Param("name") String name
    );

    @Query("SELECT p FROM Product p LEFT JOIN FETCH p.details WHERE p.id = :id")
    Optional<Product> findByIdWithDetails(@Param("id") Long id);
} 