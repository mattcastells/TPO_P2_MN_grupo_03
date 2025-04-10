package uade.api.tpo_p2_mn_grupo_03.mapper;

import uade.api.tpo_p2_mn_grupo_03.model.Product;
import uade.api.tpo_p2_mn_grupo_03.model.ProductImage;
import uade.api.tpo_p2_mn_grupo_03.model.User;
import uade.api.tpo_p2_mn_grupo_03.model.Category;
import uade.api.tpo_p2_mn_grupo_03.dto.request.CreateProductRequestDTO;
import uade.api.tpo_p2_mn_grupo_03.dto.request.UpdateProductRequestDTO;
import uade.api.tpo_p2_mn_grupo_03.dto.response.ProductResponseDTO;

import java.time.Instant;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

public class ProductMapper {

    public static Product toEntity(CreateProductRequestDTO dto, Category category, User user) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStock(dto.getStock());
        product.setImages(dto.getImages());
        product.setCategory(category);
        product.setSeller(user);
        product.setCreatedAt(Instant.now());
        product.setUpdatedAt(Instant.now());

        // Mapear imágenes BLOB si fueron cargadas
        if (dto.getImageFiles() != null && !dto.getImageFiles().isEmpty()) {
            List<ProductImage> imageEntities = dto.getImageFiles().stream().map(file -> {
                try {
                    ProductImage img = new ProductImage();
                    img.setData(file.getBytes());
                    img.setProduct(product);
                    return img;
                } catch (Exception e) {
                    throw new RuntimeException("Error reading image file", e);
                }
            }).collect(Collectors.toList());
            product.setImageFiles(imageEntities);
        }

        return product;
    }

    public static void updateEntity(Product product, UpdateProductRequestDTO dto, Category categoryIfUpdated) {
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStock(dto.getStock());
        product.setImages(dto.getImages());
        product.setUpdatedAt(Instant.now());
        if (categoryIfUpdated != null) {
            product.setCategory(categoryIfUpdated);
        }
    }

    public static ProductResponseDTO toResponse(Product product) {
        return ProductResponseDTO.builder()
            .id(product.getId())
            .name(product.getName())
            .description(product.getDescription())
            .price(product.getPrice())
            .stock(product.getStock())
            .images(product.getImages())
            .categoryId(product.getCategory().getId())
            .sellerId(product.getSeller() != null ? product.getSeller().getId() : null)
            .createdAt(product.getCreatedAt())
            .updatedAt(product.getUpdatedAt())
            .imagesBase64(
                product.getImageFiles().stream()
                    .map(img -> Base64.getEncoder().encodeToString(img.getData()))
                    .collect(Collectors.toList())
            )
            .build();
    }    
}
