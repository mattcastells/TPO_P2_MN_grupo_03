package uade.api.tpo_p2_mn_grupo_03.service.impl.userService;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import uade.api.tpo_p2_mn_grupo_03.dto.request.UpdateUserRequestDTO;
import uade.api.tpo_p2_mn_grupo_03.dto.request.UpdateUserAddressRequestDTO;
import uade.api.tpo_p2_mn_grupo_03.dto.response.UserResponseDTO;
import uade.api.tpo_p2_mn_grupo_03.model.User;
import uade.api.tpo_p2_mn_grupo_03.model.UserRole;
import uade.api.tpo_p2_mn_grupo_03.model.UserAddress;
import uade.api.tpo_p2_mn_grupo_03.repository.UserRepository;
import uade.api.tpo_p2_mn_grupo_03.service.IUserService;
import uade.api.tpo_p2_mn_grupo_03.service.impl.userService.exception.UserNotFoundException;
import uade.api.tpo_p2_mn_grupo_03.dto.response.UserAddressDTO;

/**
 * Service for user operations.
 */
@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private Validator validator;

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public UserResponseDTO checkAndFindById(Long id) throws UserNotFoundException {
        return userRepository.findByIdWithAddress(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    public UserResponseDTO checkAndFindByEmail(String email) throws UserNotFoundException {
        return findByEmail(email)
                .map(this::convertToDTO)
                .orElseThrow(() -> new UserNotFoundException(email));
    }

    private UserAddressDTO convertAddressToDTO(UserAddress address) {
        if (address == null) return null;
        return UserAddressDTO.builder()
                .street(address.getStreet())
                .postalCode(address.getPostalCode())
                .city(address.getCity())
                .state(address.getState())
                .phone(address.getPhone())
                .build();
    }

    private UserResponseDTO convertToDTO(User user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .role(user.getRole().toString())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .address(convertAddressToDTO(user.getAddress()))
                .build();
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserResponseDTO updateUser(UpdateUserRequestDTO dto, User authenticatedUser) {
        authenticatedUser.setFirstName(dto.getFirstName());
        authenticatedUser.setLastName(dto.getLastName());
        authenticatedUser.setEmail(dto.getEmail());
        
        if (dto.getRole() != null && authenticatedUser.getRole().equals("ADMIN")) {
            authenticatedUser.setRole(UserRole.valueOf(dto.getRole()));
        }

        userRepository.save(authenticatedUser);
        return convertToDTO(authenticatedUser);
    }

    @Override
    public UserResponseDTO updateUserAddress(UpdateUserAddressRequestDTO dto, User authenticatedUser) {
        UserAddress address = authenticatedUser.getAddress();
        if(address == null) {
            address = createNewAddress(dto);
        } else {
            updateAddress(address, dto);
        }
        authenticatedUser.setAddress(address);
        userRepository.save(authenticatedUser);
        return checkAndFindById(authenticatedUser.getId());
    }
    private void updateAddress(UserAddress address, UpdateUserAddressRequestDTO dto) {
        if(dto.getStreet() != null) {
            address.setStreet(dto.getStreet());
        }
        if(dto.getPostalCode() != null) {
            address.setPostalCode(dto.getPostalCode());
        }
        if(dto.getCity() != null) {
            address.setCity(dto.getCity());
        }
        if(dto.getState() != null) {
            address.setState(dto.getState());
        }
        if(dto.getPhone() != null) {
            address.setPhone(dto.getPhone());
        }
    }
    public UserAddress createNewAddress(UpdateUserAddressRequestDTO dto) {
        Set<ConstraintViolation<UpdateUserAddressRequestDTO>> violations = validator.validate(dto);
        if(!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        UserAddress address = new UserAddress();
        updateAddress(address, dto);
        return address;
    }
} 