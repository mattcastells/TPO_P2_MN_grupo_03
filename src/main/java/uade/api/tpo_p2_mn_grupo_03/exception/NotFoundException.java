package uade.api.tpo_p2_mn_grupo_03.exception;

/**
 * Exception thrown when a requested resource is not found.
 * Returns HTTP 404 status code.
 */
public class NotFoundException extends BaseAPIException {
    /**
     * Creates a new NotFoundException with a custom message.
     *
     * @param message The descriptive error message
     */
    public NotFoundException(String message) {
        super(message, "NOT_FOUND", 404);
    }

    /**
     * Creates a new NotFoundException for a specific resource and ID.
     *
     * @param resource The type of resource that was not found
     * @param id The ID of the resource that was not found
     */
    public NotFoundException(String resource, Long id) {
        super(String.format("%s with ID %d not found", resource, id), "NOT_FOUND", 404);
    }

    public NotFoundException(String resource, String identifier) {
        super(String.format("%s with %s not found", resource, identifier), "NOT_FOUND", 404);
    }
} 