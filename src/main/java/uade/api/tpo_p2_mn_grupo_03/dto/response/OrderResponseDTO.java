package uade.api.tpo_p2_mn_grupo_03.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uade.api.tpo_p2_mn_grupo_03.model.OrderStatus;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * DTO for order responses.
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDTO {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("total")
    private Double total;

    @JsonProperty("date")
    private LocalDateTime date;

    @JsonProperty("status")
    private OrderStatus status;

    @JsonProperty("products")
    private Set<OrderProductResponseDTO> products;
} 