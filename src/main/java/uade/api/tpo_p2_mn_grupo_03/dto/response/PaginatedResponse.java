package uade.api.tpo_p2_mn_grupo_03.dto.response;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * Base class for paginated responses.
 * Contains common fields for all paginated responses.
 */
@Getter
@Setter
public abstract class PaginatedResponse<T> {
    @JsonProperty("available_filters")
    private final Set<String> availableFilters;
    @JsonProperty("applied_filters")
    private final Set<String> appliedFilters;
    @JsonProperty("total_results")
    private final int totalResults;
    @JsonProperty("current_page")
    private final int currentPage;
    @JsonProperty("total_pages")
    private final int totalPages;
    @JsonProperty("results")
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