package uade.api.tpo_p2_mn_grupo_03.service.impl.orderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uade.api.tpo_p2_mn_grupo_03.dto.response.OrderProductResponseDTO;
import uade.api.tpo_p2_mn_grupo_03.dto.response.OrderResponseDTO;
import uade.api.tpo_p2_mn_grupo_03.model.Order;
import uade.api.tpo_p2_mn_grupo_03.model.OrderProduct;
import uade.api.tpo_p2_mn_grupo_03.repository.OrderRepository;
import uade.api.tpo_p2_mn_grupo_03.service.impl.orderService.exception.OrderNotFoundException;
import uade.api.tpo_p2_mn_grupo_03.service.IOrderService;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service for order operations.
 */
@Service
public class OrderService implements IOrderService {
    @Autowired
    private OrderRepository orderRepository;
    /**
     * Finds an order by ID and returns it as a DTO.
     *
     * @param id The ID of the order to find
     * @return The order DTO
     * @throws OrderNotFoundException if the order is not found
     */
    public OrderResponseDTO findById(Long id) {
        return orderRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new OrderNotFoundException(id));
    }

    /**
     * Converts an Order entity to an OrderResponseDTO.
     *
     * @param order The order entity to convert
     * @return The converted DTO
     */
    private OrderResponseDTO convertToDTO(Order order) {
        Set<OrderProductResponseDTO> productDTOs = order.getProducts().stream()
                .map(this::convertOrderProductToDTO)
                .collect(Collectors.toSet());

        return OrderResponseDTO.builder()
                .id(order.getId())
                .userId(order.getUser().getId())
                .total(order.getTotal())
                .createdAt(order.getCreatedAt())
                .status(order.getStatus())
                .products(productDTOs)
                .build();
    }

    /**
     * Converts an OrderProduct entity to an OrderProductResponseDTO.
     *
     * @param orderProduct The order product entity to convert
     * @return The converted DTO
     */
    private OrderProductResponseDTO convertOrderProductToDTO(OrderProduct orderProduct) {
        return OrderProductResponseDTO.builder()
                .productId(orderProduct.getProduct().getId())
                .quantity(orderProduct.getQuantity())
                .subtotal(orderProduct.getSubtotal())
                .build();
    }
} 