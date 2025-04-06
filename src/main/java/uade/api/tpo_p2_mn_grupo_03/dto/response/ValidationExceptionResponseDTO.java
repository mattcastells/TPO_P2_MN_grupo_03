package uade.api.tpo_p2_mn_grupo_03.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ValidationExceptionResponseDTO extends ErrorResponseDTO {
    private List<ValidationErrorResponseDTO> errors = List.of();

    public ValidationExceptionResponseDTO(Integer status, String error, String message, String code, List<ValidationErrorResponseDTO> errors) {
        super(status, error, message, code);
        this.errors = errors;
    }
} 