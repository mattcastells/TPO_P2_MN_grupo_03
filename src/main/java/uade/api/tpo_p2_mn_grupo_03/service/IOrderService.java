package uade.api.tpo_p2_mn_grupo_03.service;

import uade.api.tpo_p2_mn_grupo_03.dto.response.OrderResponseDTO;

public interface IOrderService {
    // Define los métodos del servicio aquí
    OrderResponseDTO findById(Long id);
    // Otros métodos según sea necesario
} 