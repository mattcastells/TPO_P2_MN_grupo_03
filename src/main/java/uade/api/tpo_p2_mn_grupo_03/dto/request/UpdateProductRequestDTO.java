package uade.api.tpo_p2_mn_grupo_03.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Data Transfer Object for product update requests.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProductRequestDTO {

    /**
     * The updated name of the product.
     */
    private String name;

    /**
     * The updated description of the product.
     */
    private String description;

    /**
     * The updated price of the product.
     */
    private Double price;

    /**
     * The updated stock quantity.
     */
    private Integer stock;

    /**
     * A set of updated image URLs for the product.
     */
    private List<MultipartFile> imageFiles;

    /**
     * The ID of the updated category.
     */
    private Long categoryId;

    /**
     * The ID of the updated seller.
     */
    private Long sellerId;

    /**
     * The updated list of product details.
     */
    private List<ProductDetailDTO> details;
}
