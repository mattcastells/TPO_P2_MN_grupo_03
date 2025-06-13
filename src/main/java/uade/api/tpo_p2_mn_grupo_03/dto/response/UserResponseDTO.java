package uade.api.tpo_p2_mn_grupo_03.dto.response;

import lombok.Builder;
import lombok.Value;
import java.time.Instant;

/**
 * DTO for user responses.
 * Does not include sensitive information like password.
 */
@Value
@Builder
public class UserResponseDTO {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String role;
    private Instant createdAt;
    private Instant updatedAt;
    private UserAddressDTO address;
} 