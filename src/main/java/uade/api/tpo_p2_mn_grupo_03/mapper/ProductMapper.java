package uade.api.tpo_p2_mn_grupo_03.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import uade.api.tpo_p2_mn_grupo_03.model.Product;
import uade.api.tpo_p2_mn_grupo_03.model.ProductDetail;
import uade.api.tpo_p2_mn_grupo_03.model.ProductImage;
import uade.api.tpo_p2_mn_grupo_03.model.User;
import uade.api.tpo_p2_mn_grupo_03.model.Category;
import uade.api.tpo_p2_mn_grupo_03.dto.request.CreateProductRequestDTO;
import uade.api.tpo_p2_mn_grupo_03.dto.request.ProductDetailDTO;
import uade.api.tpo_p2_mn_grupo_03.dto.request.UpdateProductRequestDTO;
import uade.api.tpo_p2_mn_grupo_03.dto.response.ProductResponseDTO;
import uade.api.tpo_p2_mn_grupo_03.config.ApiConfig;

import java.time.Instant;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductMapper {

    @Autowired
    private ApiConfig apiConfig;

    public Product toEntity(CreateProductRequestDTO dto, Category category, User user) {
        Product product = new Product();
        return toEntity(product, dto, category, user);
    }

    private List<ProductImage> getImages(Product product, List<MultipartFile> imageFiles) {
        return imageFiles.stream().map(file -> {
            try {
                ProductImage img = new ProductImage();
                img.setData(file.getBytes());
                img.setProduct(product);
                return img;
            } catch (Exception e) {
                throw new RuntimeException("Error reading image file", e);
            }
        }).collect(Collectors.toList());
    }

    private List<ProductDetail> getDetails(Product product, List<ProductDetailDTO> productDetailDTO) {
        return productDetailDTO.stream()
            .map(detailDTO -> {
                ProductDetail detail = new ProductDetail();
                detail.setName(detailDTO.getName());
                detail.setDescription(detailDTO.getDescription());
                detail.setProduct(product);
                return detail;
            })
            .collect(Collectors.toList());
    }

    public Product toEntity(Product product, CreateProductRequestDTO dto, Category category, User user) {
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStock(dto.getStock());
        product.setImages(dto.getImages());
        product.setCategory(category);
        product.setSeller(user);
        product.setCreatedAt(Instant.now());
        product.setUpdatedAt(Instant.now());

        // Mapear detalles si fueron proporcionados
        if (dto.getDetails() != null && !dto.getDetails().isEmpty()) {
            product.setDetails(getDetails(product, dto.getDetails()));
        }

        // Mapear imágenes BLOB si fueron cargadas
        if (dto.getImageFiles() != null && !dto.getImageFiles().isEmpty()) {
            product.setImageFiles(getImages(product, dto.getImageFiles()));
        }

        return product;
    }


    public Product updateEntity(Product product, UpdateProductRequestDTO dto, Category categoryIfUpdated, User user) {
        if(dto.getName() != null) {
            product.setName(dto.getName());
        }
        if(dto.getDescription() != null) {
            product.setDescription(dto.getDescription());
        }
        if(dto.getPrice() != null) {
            product.setPrice(dto.getPrice());
        }
        if(dto.getStock() != null) {
            product.setStock(dto.getStock());
        }
        if(dto.getCategoryId() != null) {
            product.setCategory(categoryIfUpdated);
        }
        if(dto.getSellerId() != null) {
            User seller = new User();
            seller.setId(dto.getSellerId());
            product.setSeller(seller);
        }
        product.setUpdatedAt(Instant.now());

        // Mapear detalles si fueron proporcionados
        if (dto.getDetails() != null && !dto.getDetails().isEmpty()) {
            product.setDetails(getDetails(product, dto.getDetails()));
        }

        // Mapear imágenes BLOB si fueron cargadas
        if (dto.getImageFiles() != null && !dto.getImageFiles().isEmpty()) {
            product.setImageFiles(getImages(product, dto.getImageFiles()));
        }

        return product;
    }

    public ProductResponseDTO toResponse(Product product) {
        return ProductResponseDTO.builder()
            .id(product.getId())
            .name(product.getName())
            .description(product.getDescription())
            .price(product.getPrice())
            .stock(product.getStock())
            .categoryId(product.getCategory().getId())
            .sellerId(product.getSeller() != null ? product.getSeller().getId() : null)
            .createdAt(product.getCreatedAt())
            .updatedAt(product.getUpdatedAt())
            .imageUrls(
                product.getImageFiles().stream()
                    .map(img -> apiConfig.getApiHost() + "/api/v1/products/" + product.getId() + "/image/" + img.getId())
                    .collect(Collectors.toList())
            )
            .build();
    }   

    public ProductResponseDTO toResponse(Product product, Boolean includeDetails) {
        ProductResponseDTO productResponseDTO = toResponse(product);
        if (includeDetails) {
            productResponseDTO.setDetails(product.getDetails().stream()
                .map(detail -> new ProductDetailDTO(detail.getName(), detail.getDescription()))
                .collect(Collectors.toList()));
        }
        return productResponseDTO;
    }    
}
