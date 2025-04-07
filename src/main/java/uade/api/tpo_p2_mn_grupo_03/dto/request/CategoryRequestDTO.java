package uade.api.tpo_p2_mn_grupo_03.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for category creation and update requests.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRequestDTO {

    /**
     * The name of the category.
     * Must be between 3 and 50 characters and not blank.
     */
    @NotBlank(message = "The name cannot be empty")
    @Size(min = 3, max = 50, message = "The name must be between 3 and 50 characters")
    private String name;
}
