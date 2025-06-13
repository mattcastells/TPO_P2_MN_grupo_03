package uade.api.tpo_p2_mn_grupo_03.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uade.api.tpo_p2_mn_grupo_03.model.Order;
import uade.api.tpo_p2_mn_grupo_03.model.User;
import uade.api.tpo_p2_mn_grupo_03.model.OrderStatus;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Order entity.
 * Provides methods to interact with the database for Order operations.
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    /**
     * Finds all orders for a specific user.
     *
     * @param user The user to filter by
     * @return A list of orders for the specified user
     */
    List<Order> findByUser(User user);

    /**
     * Finds all orders for a user with a specific status.
     *
     * @param user The user to filter by
     * @param status The status to filter by
     * @return A list of orders matching both criteria
     */
    List<Order> findByUserAndStatus(User user, OrderStatus status);
    Optional<Order> findFirstByUserAndStatus(User user, OrderStatus status);

    @Query("SELECT o FROM Order o LEFT JOIN FETCH o.products WHERE o.id = :id")
    Optional<Order> findByIdWithProducts(@Param("id") Long id);

    @Query("SELECT o FROM Order o LEFT JOIN FETCH o.products WHERE o.user.id = :userId")
    Page<Order> findByUserIdWithProducts(@Param("userId") Long userId, Pageable pageable);
} 