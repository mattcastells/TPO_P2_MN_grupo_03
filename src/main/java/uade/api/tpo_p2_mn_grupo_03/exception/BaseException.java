package uade.api.tpo_p2_mn_grupo_03.exception;

/**
 * Base exception for all application exceptions.
 * Provides a common structure for error handling with message and code.
 */
public abstract class BaseException extends RuntimeException {
    private final String message;
    private final String code;

    /**
     * Constructor to create a new base exception.
     *
     * @param message The descriptive error message
     * @param code The unique code identifying the error type
     */
    protected BaseException(String message, String code) {
        super(message);
        this.message = message;
        this.code = code;
    }

    /**
     * Gets the descriptive error message.
     *
     * @return The error message
     */
    @Override
    public String getMessage() {
        return message;
    }

    /**
     * Gets the unique code identifying the error type.
     *
     * @return The error code
     */
    public String getCode() {
        return code;
    }
} 