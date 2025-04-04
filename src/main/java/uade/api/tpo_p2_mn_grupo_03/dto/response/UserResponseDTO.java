package uade.api.tpo_p2_mn_grupo_03.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uade.api.tpo_p2_mn_grupo_03.model.UserRole;

/**
 * DTO for user responses.
 * Does not include sensitive information like password.
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("email")
    private String email;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("role")
    private String role;
} 