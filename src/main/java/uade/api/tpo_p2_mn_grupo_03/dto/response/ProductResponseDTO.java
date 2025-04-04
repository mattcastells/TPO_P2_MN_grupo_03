package uade.api.tpo_p2_mn_grupo_03.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uade.api.tpo_p2_mn_grupo_03.model.Category;

import java.util.Set;

/**
 * DTO for product responses.
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDTO {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("price")
    private Double price;

    @JsonProperty("stock")
    private Integer stock;

    @JsonProperty("images")
    private Set<String> images;

    @JsonProperty("category_id")
    private Long categoryId;

    @JsonProperty("seller_id")
    private Long sellerId;
} 