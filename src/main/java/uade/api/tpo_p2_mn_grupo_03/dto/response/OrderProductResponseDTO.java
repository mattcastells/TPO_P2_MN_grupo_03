package uade.api.tpo_p2_mn_grupo_03.dto.response;

import lombok.Builder;
import lombok.Value;
import java.time.Instant;

/**
 * DTO for order product responses.
 */
@Value
@Builder
public class OrderProductResponseDTO {
    private Long productId;
    private Integer quantity;
    private Double subtotal;
    private Instant createdAt;
    private Instant updatedAt;
} 