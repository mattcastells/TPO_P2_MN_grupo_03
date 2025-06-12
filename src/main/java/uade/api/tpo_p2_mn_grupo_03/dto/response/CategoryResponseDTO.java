package uade.api.tpo_p2_mn_grupo_03.dto.response;

import lombok.Builder;
import lombok.Value;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.Instant;
import java.util.List;

/**
 * DTO for category responses.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponseDTO {
    private Long id;
    private String name;
    private Long parentId;
    private Instant createdAt;
    private Instant updatedAt;
} 