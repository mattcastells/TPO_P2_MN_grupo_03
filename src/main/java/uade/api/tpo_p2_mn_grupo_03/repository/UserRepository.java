package uade.api.tpo_p2_mn_grupo_03.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uade.api.tpo_p2_mn_grupo_03.model.User;

import java.util.Optional;

/**
 * Repository interface for User entity.
 * Provides methods to interact with the database for User operations.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Finds a user by their email.
     *
     * @param email The email to search for
     * @return An Optional containing the user if found
     */
    Optional<User> findByEmail(String email);
} 