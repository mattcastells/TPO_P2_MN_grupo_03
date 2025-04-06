package uade.api.tpo_p2_mn_grupo_03.service.impl.userService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uade.api.tpo_p2_mn_grupo_03.dto.response.UserResponseDTO;
import uade.api.tpo_p2_mn_grupo_03.model.User;
import uade.api.tpo_p2_mn_grupo_03.repository.UserRepository;
import uade.api.tpo_p2_mn_grupo_03.service.IUserService;
import uade.api.tpo_p2_mn_grupo_03.service.impl.userService.exception.UserNotFoundException;

/**
 * Service for user operations.
 */
@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Finds a user by ID and returns it as a DTO.
     *
     * @param id The ID of the user to find
     * @return The user DTO
     * @throws UserNotFoundException if the user is not found
     */
    public UserResponseDTO findById(Long id) {
        return userRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    /**
     * Converts a User entity to a UserResponseDTO.
     *
     * @param user The user entity to convert
     * @return The converted DTO
     */
    private UserResponseDTO convertToDTO(User user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .role(user.getRole().toString())
                .build();
    }
} 