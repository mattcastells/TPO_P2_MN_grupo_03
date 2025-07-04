package uade.api.tpo_p2_mn_grupo_03.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryPatchRequestDTO {

    private Long id;

    @NotBlank(message = "The name cannot be empty")
    @Size(min = 3, max = 50, message = "The name must be between 3 and 50 characters")
    private String name;

    @Min(value = 1, message = "The parentId must be greater than 0")
    private Long parentId;
}
