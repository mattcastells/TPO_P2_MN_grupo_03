package uade.api.tpo_p2_mn_grupo_03.exception;

/**
 * Exception thrown when trying to create or update an entity that already exists.
 * Returns HTTP 409 status code (Conflict).
 */
public class DuplicateEntityException extends BaseAPIException {
    /**
     * Creates a new DuplicateEntityException with a custom message.
     *
     * @param message The descriptive error message
     */
    public DuplicateEntityException(String message) {
        super(message, "DUPLICATE_ENTITY", 409);
    }
} 