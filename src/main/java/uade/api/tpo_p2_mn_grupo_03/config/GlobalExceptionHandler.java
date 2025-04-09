package uade.api.tpo_p2_mn_grupo_03.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import uade.api.tpo_p2_mn_grupo_03.dto.response.ErrorResponseDTO;
import uade.api.tpo_p2_mn_grupo_03.dto.response.ValidationErrorResponseDTO;
import uade.api.tpo_p2_mn_grupo_03.dto.response.ValidationExceptionResponseDTO;
import uade.api.tpo_p2_mn_grupo_03.exception.BaseAPIException;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<String> handleAccessDeniedException(AuthorizationDeniedException ex) {
        String message = ex.getMessage();        
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(message);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponseDTO> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        ErrorResponseDTO response = new ErrorResponseDTO(
            HttpStatus.BAD_REQUEST.value(),
            "Bad Request",
            "malformed JSON request",
            "INVALID_JSON"
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationExceptionResponseDTO> handleValidationExceptions(MethodArgumentNotValidException ex) {
        ValidationExceptionResponseDTO response = new ValidationExceptionResponseDTO(
            HttpStatus.BAD_REQUEST.value(),
            HttpStatus.BAD_REQUEST.getReasonPhrase(),
            "Error de validación",
            "VALIDATION_ERROR",
            ex.getBindingResult().getAllErrors().stream()
                .map(error -> {
                    String fieldName = ((FieldError) error).getField();
                    String errorMessage = error.getDefaultMessage();
                    return new ValidationErrorResponseDTO(fieldName, errorMessage);
                })
                .collect(Collectors.toList())
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BaseAPIException.class)
    public ResponseEntity<ErrorResponseDTO> handleBaseAPIException(BaseAPIException ex) {
        ErrorResponseDTO response = new ErrorResponseDTO(
            ex.getStatusCode(),
            HttpStatus.valueOf(ex.getStatusCode()).getReasonPhrase(),
            ex.getMessage(),
            ex.getCode()
        );

        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getStatusCode()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGenericException(Exception ex) {
        ErrorResponseDTO response = new ErrorResponseDTO(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "Internal Server Error",
            "Ha ocurrido un error inesperado",
            "INTERNAL_SERVER_ERROR"
        );

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
} 