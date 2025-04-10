package uade.api.tpo_p2_mn_grupo_03.dto.response;

import java.util.List;
import java.util.Set;

public class ProductPaginatedResponseDTO extends PaginatedResponse<ProductResponseDTO> {
    public ProductPaginatedResponseDTO(
        Set<String> availableFilters,
        Set<String> appliedFilters,
        Integer totalResults,
        Integer offset,
        Integer limit,
        List<ProductResponseDTO> results
        ) {
            super(
                availableFilters,
                appliedFilters,
                totalResults,
                offset,
                limit,
                results
                );
    }
}   
