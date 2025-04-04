package uade.api.tpo_p2_mn_grupo_03.exception;

/**
 * Base exception for all REST API related exceptions.
 * Extends BaseException adding HTTP status code.
 */
public abstract class BaseAPIException extends BaseException {
    private final int statusCode;

    /**
     * Constructor to create a new API exception.
     *
     * @param message The descriptive error message
     * @param code The unique code identifying the error type
     * @param statusCode The HTTP status code associated with the error
     */
    protected BaseAPIException(String message, String code, int statusCode) {
        super(message, code);
        this.statusCode = statusCode;
    }

    /**
     * Gets the HTTP status code associated with the error.
     *
     * @return The HTTP status code
     */
    public int getStatusCode() {
        return statusCode;
    }
} 