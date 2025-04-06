package uade.api.tpo_p2_mn_grupo_03.dto.response;

import java.util.Set;

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
    private final int currentPage;
    private final int totalPages;
    private final T results;

    protected PaginatedResponse(Set<String> availableFilters, Set<String> appliedFilters, 
                              int totalResults, int currentPage, int totalPages, T results) {
        this.availableFilters = availableFilters;
        this.appliedFilters = appliedFilters;
        this.totalResults = totalResults;
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.results = results;
    }

} 