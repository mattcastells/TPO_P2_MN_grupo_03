package uade.api.tpo_p2_mn_grupo_03.dto.response;

import lombok.Builder;
import lombok.Value;

import java.time.Instant;

/**
 * DTO for category responses.
 */
@Value
@Builder
public class CategoryResponseDTO {
    private Long id;
    private String name;
    private Instant createdAt;
    private Instant updatedAt;
} 