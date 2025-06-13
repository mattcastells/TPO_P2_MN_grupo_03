package uade.api.tpo_p2_mn_grupo_03.service;

import uade.api.tpo_p2_mn_grupo_03.dto.request.CreateOrderRequestDTO;
import uade.api.tpo_p2_mn_grupo_03.dto.response.OrderResponseDTO;
import uade.api.tpo_p2_mn_grupo_03.model.User;

import java.util.List;

public interface IOrderService {
    // Define los métodos del servicio aquí
    OrderResponseDTO findById(Long id);
    // Otros métodos según sea necesario
    OrderResponseDTO createOrder(CreateOrderRequestDTO orderRequestDTO, User user);
    List<OrderResponseDTO> findByUserId(Long userId, Integer offset, Integer limit);
} 