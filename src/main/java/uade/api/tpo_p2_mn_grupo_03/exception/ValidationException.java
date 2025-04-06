package uade.api.tpo_p2_mn_grupo_03.exception;

public class ValidationException extends BaseAPIException {
    public ValidationException(String message) {
        super(message, "VALIDATION_ERROR", 400);
    }
} 