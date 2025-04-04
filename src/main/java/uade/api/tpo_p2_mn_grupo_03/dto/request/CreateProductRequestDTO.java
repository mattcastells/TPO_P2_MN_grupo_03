package uade.api.tpo_p2_mn_grupo_03.dto.request;

import uade.api.tpo_p2_mn_grupo_03.model.Category;
import lombok.Data;

import java.util.Set;

/**
 * DTO for product creation and update requests.
 */
@Data
public class CreateProductRequestDTO {
    private String name;
    private String description;
    private Double price;
    private Integer stock;
    private Set<String> images;
    private Long categoryId;
} 