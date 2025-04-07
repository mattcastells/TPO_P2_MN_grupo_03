package uade.api.tpo_p2_mn_grupo_03.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * Data Transfer Object for product creation requests.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductRequestDTO {

    /**
     * The name of the product.
     */
    private String name;

    /**
     * The description of the product.
     */
    private String description;

    /**
     * The price of the product.
     */
    private Double price;

    /**
     * The stock quantity available.
     */
    private Integer stock;

    /**
     * A set of image URLs for the product.
     */
    private Set<String> images;

    /**
     * The ID of the category this product belongs to.
     */
    private Long categoryId;

    /**
     * The ID of the seller who owns the product.
     */
    private Long sellerId;
}
