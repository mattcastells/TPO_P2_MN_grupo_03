package uade.api.tpo_p2_mn_grupo_03.dto.response;

import lombok.Builder;
import lombok.Value;
import uade.api.tpo_p2_mn_grupo_03.model.OrderStatus;

import java.time.Instant;
import java.util.Set;

/**
 * DTO for order responses.
 */
@Value
@Builder
public class OrderResponseDTO {
    private Long id;
    private Long userId;
    private Double total;
    private Instant createdAt;
    private Instant updatedAt;
    private OrderStatus status;
    private Set<OrderProductResponseDTO> products;
} 