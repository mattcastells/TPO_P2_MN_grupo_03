package uade.api.tpo_p2_mn_grupo_03.exception;

/**
 * Exception thrown when authentication is required or has failed.
 * Returns HTTP 401 status code.
 */
public class UnauthorizedException extends BaseAPIException {
    /**
     * Creates a new UnauthorizedException with a custom message.
     *
     * @param message The descriptive error message
     */
    public UnauthorizedException(String message) {
        super(message, "UNAUTHORIZED", 401);
    }
} 