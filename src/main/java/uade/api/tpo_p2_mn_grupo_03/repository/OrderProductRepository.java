package uade.api.tpo_p2_mn_grupo_03.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uade.api.tpo_p2_mn_grupo_03.model.OrderProduct;

/**
 * Repository interface for OrderProduct entity.
 * Provides methods to interact with the database for OrderProduct operations.
 */
@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {
} 