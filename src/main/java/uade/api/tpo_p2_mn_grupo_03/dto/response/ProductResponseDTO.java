package uade.api.tpo_p2_mn_grupo_03.dto.response;

import lombok.Builder;
import lombok.Value;

import java.util.Set;
import java.time.Instant;

/**
 * DTO for product responses.
 */
@Value
@Builder
public class ProductResponseDTO {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Integer stock;
    private Set<String> images;
    private Long categoryId;
    private Long sellerId;
    private Instant createdAt;
    private Instant updatedAt;
} 