package uade.api.tpo_p2_mn_grupo_03.dto.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

import uade.api.tpo_p2_mn_grupo_03.dto.filter.AppliedFilterDTO;
import uade.api.tpo_p2_mn_grupo_03.dto.filter.AvailableFilterDTO;

/**
 * Base class for paginated responses.
 * Contains common fields for all paginated responses.
 */
@Getter
@Setter
public abstract class PaginatedResponse<T> {
    private final List<AvailableFilterDTO> availableFilters;
    private final List<AppliedFilterDTO> appliedFilters;
    private final int totalResults;
    private final int offset;
    private final int limit;
    @Getter
    private final List<T> results;

    protected PaginatedResponse(
        List<AvailableFilterDTO> availableFilters,
        List<AppliedFilterDTO> appliedFilters,
        Integer totalResults,
        Integer offset,
        Integer limit,
        List<T> results
    ) {
        this.availableFilters = availableFilters;
        this.appliedFilters = appliedFilters;
        this.totalResults = totalResults;
        this.offset = offset;
        this.limit = limit;
        this.results = results;
    }
} 