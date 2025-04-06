package uade.api.tpo_p2_mn_grupo_03.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponseDTO {
    private Integer status;
    private String error;
    private String message;
    private String code;
} 