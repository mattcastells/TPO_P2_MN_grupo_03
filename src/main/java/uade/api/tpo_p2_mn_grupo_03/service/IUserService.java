package uade.api.tpo_p2_mn_grupo_03.service;

import uade.api.tpo_p2_mn_grupo_03.dto.request.UpdateUserRequestDTO;
import uade.api.tpo_p2_mn_grupo_03.dto.request.UpdateUserAddressRequestDTO;
import uade.api.tpo_p2_mn_grupo_03.dto.response.UserResponseDTO;
import uade.api.tpo_p2_mn_grupo_03.model.User;
import uade.api.tpo_p2_mn_grupo_03.service.impl.userService.exception.UserNotFoundException;

import java.util.Optional;

public interface IUserService {
    Optional<User> findById(Long id);
    Optional<User> findByEmail(String email);
    UserResponseDTO checkAndFindById(Long id) throws UserNotFoundException;
    UserResponseDTO checkAndFindByEmail(String email) throws UserNotFoundException;
    void delete(Long id);
    UserResponseDTO updateUser(UpdateUserRequestDTO dto, User user);
    UserResponseDTO updateUserAddress(UpdateUserAddressRequestDTO dto, User user);
} 