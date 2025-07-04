package uade.api.tpo_p2_mn_grupo_03.service.impl.orderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uade.api.tpo_p2_mn_grupo_03.dto.request.CreateOrderRequestDTO;
import uade.api.tpo_p2_mn_grupo_03.dto.request.ProductQuantityDTO;
import uade.api.tpo_p2_mn_grupo_03.dto.response.OrderProductResponseDTO;
import uade.api.tpo_p2_mn_grupo_03.dto.response.OrderResponseDTO;
import uade.api.tpo_p2_mn_grupo_03.model.Order;
import uade.api.tpo_p2_mn_grupo_03.model.OrderProduct;
import uade.api.tpo_p2_mn_grupo_03.model.OrderStatus;
import uade.api.tpo_p2_mn_grupo_03.repository.OrderRepository;
import uade.api.tpo_p2_mn_grupo_03.repository.ProductRepository;
import uade.api.tpo_p2_mn_grupo_03.service.impl.orderService.exception.OrderNotFoundException;
import uade.api.tpo_p2_mn_grupo_03.service.impl.productService.exception.ProductNotEnoughStockException;
import uade.api.tpo_p2_mn_grupo_03.service.impl.productService.exception.ProductNotFoundException;
import uade.api.tpo_p2_mn_grupo_03.service.IOrderService;
import uade.api.tpo_p2_mn_grupo_03.model.Product;
import uade.api.tpo_p2_mn_grupo_03.model.User;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;
import java.util.stream.Collectors;

/**
 * Service for order operations.
 */
@Service
public class OrderService implements IOrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;


    /**
     * Finds an order by ID and returns it as a DTO.
     *
     * @param id The ID of the order to find
     * @return The order DTO
     * @throws OrderNotFoundException if the order is not found
     */
    public OrderResponseDTO findById(Long id) {
        return orderRepository.findByIdWithProducts(id)
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
                .updatedAt(order.getUpdatedAt())
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
                .name(orderProduct.getProduct().getName())
                .price(orderProduct.getProduct().getPrice())
                .quantity(orderProduct.getQuantity())
                .subtotal(orderProduct.getSubtotal())
                .createdAt(orderProduct.getCreatedAt())
                .updatedAt(orderProduct.getUpdatedAt())
                .build();
    }

    // TODO: refactor in smaller methods
    public OrderResponseDTO createOrder(CreateOrderRequestDTO createOrderRequestDTO, User user) {
        Order order = new Order();
        if(order.getProducts() == null) {
            order.setProducts(new HashSet<>());
        }
        Map<Long, ProductQuantityDTO> productQuantitiesMemo = new HashMap<>();
        createOrderRequestDTO.getProducts().forEach(productQuantity -> {
            productQuantitiesMemo.put(productQuantity.getProductId(), productQuantity);
        });
        order.setUser(user);
        order.setStatus(OrderStatus.PENDING);
        List<Product> products = productRepository.findByIdIn(
            createOrderRequestDTO
            .getProducts()
            .stream()
            .map(ProductQuantityDTO::getProductId)
            .collect(Collectors.toList()));
        if(products.size() != createOrderRequestDTO.getProducts().size()) {
            throw new ProductNotFoundException(
                createOrderRequestDTO.getProducts()
                .stream()
                .map(ProductQuantityDTO::getProductId)
                .collect(Collectors.toList())
                .toString()
                );
        }
        for (Product product : products) {
            ProductQuantityDTO productQuantity = productQuantitiesMemo.get(product.getId());
            if(productQuantity.getQuantity() > product.getStock()) {
                throw new ProductNotEnoughStockException(product.getId(), product.getStock());
            }
            OrderProduct orderProduct = order.getProducts().stream()
                .filter(op -> op.getProduct().getId().equals(product.getId()))
                .findFirst()
                .map(existingOrderProduct -> {
                    existingOrderProduct.setQuantity(existingOrderProduct.getQuantity() + productQuantity.getQuantity());
                    return existingOrderProduct;
                })
                .orElseGet(() -> {
                    OrderProduct newOrderProduct = new OrderProduct();
                    newOrderProduct.setOrder(order);
                    newOrderProduct.setProduct(product);
                    newOrderProduct.setQuantity(productQuantity.getQuantity());
                    order.getProducts().add(newOrderProduct);
                    return newOrderProduct;
                });
            order.getProducts().add(orderProduct);
            orderProduct.calculateSubtotal();
            orderProduct.setOrder(order);
            product.setStock(product.getStock() - productQuantity.getQuantity());
            productRepository.save(product);
        }
        order.calculateTotal();
        Order savedOrder = orderRepository.save(order);
        return convertToDTO(savedOrder);
    }

    @Override
    public List<OrderResponseDTO> findByUserId(Long userId, Integer offset, Integer limit) {
        Pageable pageable = PageRequest.of(offset / limit, limit);
        return orderRepository.findByUserIdWithProducts(userId, pageable)
                .getContent()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
} 