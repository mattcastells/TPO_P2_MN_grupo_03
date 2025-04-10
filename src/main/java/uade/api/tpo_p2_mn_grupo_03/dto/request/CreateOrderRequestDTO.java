package uade.api.tpo_p2_mn_grupo_03.dto.request;

import lombok.Data;
import java.util.List;

@Data
public class CreateOrderRequestDTO {
    private List<ProductQuantityDTO> products;
} 