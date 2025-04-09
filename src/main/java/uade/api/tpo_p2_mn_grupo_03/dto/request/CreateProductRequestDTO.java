package uade.api.tpo_p2_mn_grupo_03.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

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
    @NotBlank(message = "Name is required")
    @Size(min = 5, max = 50, message = "Name must be between 5 and 50 characters")
    private String name;

    /**
     * The description of the product.
     */
    @NotBlank(message = "Description is required")
    @Size(min = 5, max = 50, message = "Name must be between 5 and 50 characters")
    private String description;

    /**
     * The price of the product.
     */
    @NotNull(message = "Price is required")
    @Positive(message = "Price must be greater than 0")
    private Double price;

    /**
     * The stock quantity available.
     */
    @NotNull(message = "Stock is required")
    @Positive(message = "Stock must be greater than 0")
    private Integer stock;

    /**
     * A set of image URLs for the product.
     */
    @NotNull(message = "Images set cannot be null")
    @Size(min = 1, message = "At least one image is required")
    private Set<@NotBlank(message = "Image URL cannot be blank") String>  images;

    /**
     * The ID of the category this product belongs to.
     */
    @NotNull(message = "Category ID is required")
    @Positive(message = "Category ID must be a positive number")
    private Long categoryId;
}
