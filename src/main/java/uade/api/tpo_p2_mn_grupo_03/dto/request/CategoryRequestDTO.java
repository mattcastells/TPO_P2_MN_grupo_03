package uade.api.tpo_p2_mn_grupo_03.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * DTO for category creation and update requests.
 */
@Data
public class CategoryRequestDTO {
    @JsonProperty("name")
    private String name;
} 