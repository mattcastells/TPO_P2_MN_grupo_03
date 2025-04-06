package uade.api.tpo_p2_mn_grupo_03.service;

import uade.api.tpo_p2_mn_grupo_03.dto.response.UserResponseDTO;

public interface IUserService {
    // Define los métodos del servicio aquí
    UserResponseDTO findById(Long id);
    // Otros métodos según sea necesario
} 