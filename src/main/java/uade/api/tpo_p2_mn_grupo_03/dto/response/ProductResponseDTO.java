package uade.api.tpo_p2_mn_grupo_03.dto.response;

import java.time.Instant;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uade.api.tpo_p2_mn_grupo_03.dto.request.ProductDetailDTO;

/**
 * DTO for product responses.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDTO {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Integer stock;
    private List<String> imageUrls;
    private List<ProductDetailDTO> details;
    private Long categoryId;
    private Long sellerId;
    private Instant createdAt;
    private Instant updatedAt;
}
