package uade.api.tpo_p2_mn_grupo_03.dto.response;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.function.Function;

import com.fasterxml.jackson.annotation.JsonIgnore;

import uade.api.tpo_p2_mn_grupo_03.dto.filter.AppliedFilterDTO;
import uade.api.tpo_p2_mn_grupo_03.dto.filter.AppliedMultipleValueFilterDTO;
import uade.api.tpo_p2_mn_grupo_03.dto.filter.AvailableFilterDTO;
import uade.api.tpo_p2_mn_grupo_03.dto.filter.AvailableMultipleValueFilterDTO;
import uade.api.tpo_p2_mn_grupo_03.dto.filter.MultipleFilterValueDTO;
import uade.api.tpo_p2_mn_grupo_03.model.Category;
public class ProductPaginatedResponseDTO extends PaginatedResponse<ProductResponseDTO> {

    private static Map<String, Function<Object, Object>> filterFunctions = Map.of(
        "priceLessThan", (Function<Object, Object>) value -> Double.valueOf(value.toString()),
        "priceGreaterThan", (Function<Object, Object>) value -> Double.valueOf(value.toString()),
        "stockLessThan", (Function<Object, Object>) value -> Integer.valueOf(value.toString()),
        "stockGreaterThan", (Function<Object, Object>) value -> Integer.valueOf(value.toString()),
        "sellerId", (Function<Object, Object>) value -> Long.valueOf(value.toString()),
        "categoryIds", (Function<Object, Object>) value -> Arrays.stream(value.toString().split(","))
            .map(String::trim)
            .map(Long::valueOf)
            .collect(Collectors.toList()));
    @JsonIgnore
    private static List<String> singleValueFilters = List.of(
        "priceLessThan",
        "priceGreaterThan",
        "stockLessThan",
        "stockGreaterThan",
        "sellerId",
        "offset",
        "limit"
        );

    @JsonIgnore   
    private static List<String> multipleValueFilters = List.of("categoryIds");

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

    public static List<AvailableMultipleValueFilterDTO> getMultipleValueAvailableFilters(List<Category> categories) {
        List<AvailableMultipleValueFilterDTO> availableFilters = new ArrayList<>();
        AvailableMultipleValueFilterDTO multipleFilterDTO = new AvailableMultipleValueFilterDTO();
        multipleFilterDTO.setId("categoryIds");
        multipleFilterDTO.setValue(categories.stream().map(category -> {
            MultipleFilterValueDTO multipleFilterValueDTO = new MultipleFilterValueDTO();
            multipleFilterValueDTO.setId(category.getId());
            multipleFilterValueDTO.setDescription(category.getName());
            return multipleFilterValueDTO;
        }).collect(Collectors.toList()));
        availableFilters.add(multipleFilterDTO);
        return availableFilters;
    }

    public static List<AvailableFilterDTO> getSingleValueAvailableFilters(Map<String, Object> availableFiltersMap) {
        List<AvailableFilterDTO> availableFilters = new ArrayList<>();
        singleValueFilters.forEach(filter -> {
            if (availableFiltersMap.get(filter) != null) {
                availableFilters.add(
                    new AvailableFilterDTO(filter,
                    filterFunctions.get(filter).apply(availableFiltersMap.get(filter)))
                    );
            }
        });
        return availableFilters;
    }

    public static List<AvailableFilterDTO> getAvailableFilters(Map<String, Object> availableFiltersMap, List<Category> categories) {
        List<AvailableFilterDTO> availableFilters = new ArrayList<>();
        availableFilters.addAll(getSingleValueAvailableFilters(availableFiltersMap));
        availableFilters.addAll(getMultipleValueAvailableFilters(categories));
        return availableFilters;
    }
    public static List<AppliedFilterDTO> getAppliedFilters(
        List<Category> categories,
        Double priceLessThan,
        Double priceGreaterThan,
        Integer stockLessThan,
        Integer stockGreaterThan,
        Long sellerId,
        String name,
        Integer offset,
        Integer limit
    ) {
        List<AppliedFilterDTO> appliedFilters = new ArrayList<>();

        if (categories != null) {
            appliedFilters.add(new AppliedMultipleValueFilterDTO("categoryIds", categories.stream().map(
                category -> {
                    MultipleFilterValueDTO multipleFilterValueDTO = new MultipleFilterValueDTO();
                    multipleFilterValueDTO.setId(category.getId());
                    multipleFilterValueDTO.setDescription(category.getName());
                    return multipleFilterValueDTO;
                }
            ).collect(Collectors.toList())));
        }
        if (priceLessThan != null) {
            appliedFilters.add(new AppliedFilterDTO<Double>("priceLessThan", priceLessThan));
        }
        if (priceGreaterThan != null) {
            appliedFilters.add(new AppliedFilterDTO<Double>("priceGreaterThan", priceGreaterThan));
        }
        if (stockLessThan != null) {
            appliedFilters.add(new AppliedFilterDTO<Integer>("stockLessThan", stockLessThan));
        }
        if (stockGreaterThan != null) {
            appliedFilters.add(new AppliedFilterDTO<Integer>("stockGreaterThan", stockGreaterThan));
        }
        if (sellerId != null) {
            appliedFilters.add(new AppliedFilterDTO<Long>("sellerId", sellerId));
        }
        if (name != null) {
            appliedFilters.add(new AppliedFilterDTO<String>("name", name));
        }
        if (offset != null) {
            appliedFilters.add(new AppliedFilterDTO<Integer>("offset", offset));
        }
        if (limit != null) {
            appliedFilters.add(new AppliedFilterDTO<Integer>("limit", limit));
        }

        return appliedFilters;
    }
}   
