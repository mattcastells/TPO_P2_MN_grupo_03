package uade.api.tpo_p2_mn_grupo_03.exception;

/**
 * Exception thrown when the request is malformed or invalid.
 * Returns HTTP 400 status code.
 */
public class BadRequestException extends BaseAPIException {
    /**
     * Creates a new BadRequestException with a custom message.
     *
     * @param message The descriptive error message
     */
    public BadRequestException(String message) {
        super(message, "BAD_REQUEST", 400);
    }
} 