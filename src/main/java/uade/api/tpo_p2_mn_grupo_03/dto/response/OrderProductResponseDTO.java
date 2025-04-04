package uade.api.tpo_p2_mn_grupo_03.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

/**
 * DTO for order product responses.
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderProductResponseDTO {
    @JsonProperty("product_id")
    private Long productId;

    @JsonProperty("quantity")
    private Integer quantity;

    @JsonProperty("subtotal")
    private Double subtotal;
} 