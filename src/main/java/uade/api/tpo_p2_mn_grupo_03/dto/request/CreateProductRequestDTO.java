package uade.api.tpo_p2_mn_grupo_03.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductRequestDTO {
    private String name;
    private String description;
    private Double price;
    private Integer stock;
    private Set<String> images;
    private Long categoryId;
} 