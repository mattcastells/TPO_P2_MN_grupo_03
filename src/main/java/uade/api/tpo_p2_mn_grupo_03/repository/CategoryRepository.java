package uade.api.tpo_p2_mn_grupo_03.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uade.api.tpo_p2_mn_grupo_03.model.Category;

/**
 * Repository interface for Category entity.
 * Provides methods to interact with the database for Category operations.
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByNameIgnoreCase(String name);
}