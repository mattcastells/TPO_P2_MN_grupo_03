package uade.api.tpo_p2_mn_grupo_03.dto.response;

import java.util.List;

import uade.api.tpo_p2_mn_grupo_03.dto.filter.AppliedFilterDTO;
import uade.api.tpo_p2_mn_grupo_03.dto.filter.AvailableFilterDTO;

public class ProductPaginatedResponseDTO extends PaginatedResponse<ProductResponseDTO> {
    public ProductPaginatedResponseDTO(
        List<AvailableFilterDTO> availableFilters,
        List<AppliedFilterDTO> appliedFilters,
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
