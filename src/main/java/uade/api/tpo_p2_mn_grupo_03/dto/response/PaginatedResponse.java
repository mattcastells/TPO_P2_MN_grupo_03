package uade.api.tpo_p2_mn_grupo_03.dto.response;

import java.util.Set;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Base class for paginated responses.
 * Contains common fields for all paginated responses.
 */
@Getter
@Setter
public abstract class PaginatedResponse<T> {
    private final Set<String> availableFilters;
    private final Set<String> appliedFilters;
    private final int totalResults;
    private final int offset;
    private final int limit;
    @Getter
    private final List<T> results;

    protected PaginatedResponse(
        Set<String> availableFilters,
        Set<String> appliedFilters,
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