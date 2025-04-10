package uade.api.tpo_p2_mn_grupo_03.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uade.api.tpo_p2_mn_grupo_03.model.Product;
import uade.api.tpo_p2_mn_grupo_03.model.Category;
import uade.api.tpo_p2_mn_grupo_03.model.User;

import java.util.List;

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
} 