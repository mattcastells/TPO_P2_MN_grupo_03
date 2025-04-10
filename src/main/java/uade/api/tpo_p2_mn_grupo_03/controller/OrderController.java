package uade.api.tpo_p2_mn_grupo_03.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uade.api.tpo_p2_mn_grupo_03.dto.request.CreateOrderRequestDTO;
import uade.api.tpo_p2_mn_grupo_03.dto.response.OrderResponseDTO;
import uade.api.tpo_p2_mn_grupo_03.model.User;
import uade.api.tpo_p2_mn_grupo_03.service.IOrderService;

/**
 * Controller for order operations.
 */
@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private IOrderService orderService;

    /**
     * Finds an order by ID.
     *
     * @param id The ID of the order to find
     * @return The order DTO
     */
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('BUYER')")
    public ResponseEntity<OrderResponseDTO> createOrder(
            @RequestBody CreateOrderRequestDTO orderRequestDTO,
            Authentication authentication) {
                User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(orderService.createOrder(orderRequestDTO, user));
    }
} 