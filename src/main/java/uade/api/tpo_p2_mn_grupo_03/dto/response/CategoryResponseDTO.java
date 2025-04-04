package uade.api.tpo_p2_mn_grupo_03.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

/**
 * DTO for category responses.
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponseDTO {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;
} 