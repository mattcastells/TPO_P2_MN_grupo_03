package uade.api.tpo_p2_mn_grupo_03.service.impl.userService;

import java.util.Optional;

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

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public UserResponseDTO checkAndFindById(Long id) throws UserNotFoundException {
        return findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new UserNotFoundException(id));
    }   

    public UserResponseDTO checkAndFindByEmail(String email) throws UserNotFoundException {
        return findByEmail(email)
                .map(this::convertToDTO)
                .orElseThrow(() -> new UserNotFoundException(email));
    }

    private UserResponseDTO convertToDTO(User user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .role(user.getRole().toString())
                .build();
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }
} 