package uade.api.tpo_p2_mn_grupo_03.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.time.Instant;

/**
 * Entity representing a product.
 */
@Entity
@Table(name = "products")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    /**
     * Unique identifier for the product.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Product name.
     */
    @Column(nullable = false)
    private String name;

    /**
     * Product description.
     */
    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    /**
     * Product price.
     */
    @Column(nullable = false)
    private Double price;

    /**
     * Available stock quantity.
     */
    @Column(nullable = false)
    private Integer stock;

    /**
     * Set of image URLs for the product.
     */
    @ElementCollection
    @CollectionTable(name = "product_images", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "image_url")
    private Set<String> images;

    /**
     * Category to which the product belongs.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    /**
     * Seller who owns the product.
     * Temporarily set to nullable for testing.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id", nullable = true) // Temporarily modified for testing
    private User seller;

    /*
     * Relationship with orders.
     * Each product can be associated with multiple order items.
     * Example: OrderProduct - productId = 1, quantity = 2
     */
    @OneToMany(mappedBy = "product", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<OrderProduct> orderProducts;

    /**
     * Timestamp of product creation.
     */
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    /**
     * Timestamp of the last update.
     */
    @UpdateTimestamp
    @Column(nullable = false)
    private Instant updatedAt;

    /**
     * List of binary image blobs associated with the product.
     */
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductImage> imageFiles = new ArrayList<>();

    /**
     * Lifecycle callback before persisting.
     */
    @PrePersist
    protected void onCreate() {
        createdAt = Instant.now();
        updatedAt = Instant.now();
    }

    /**
     * Lifecycle callback before updating.
     */
    @PreUpdate
    protected void onUpdate() {
        updatedAt = Instant.now();
    }

    /**
     * Custom constructor for creating new products.
     */
    public Product(String name, String description, Double price, Integer stock, Set<String> images, Category category, User seller) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.images = images;
        this.category = category;
        this.seller = seller;
        this.orderProducts = new HashSet<>();
    }

    /**
     * Returns a string representation of the product.
     */
    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                ", images=" + images +
                ", category=" + (category != null ? category.getId() : null) +
                ", seller=" + (seller != null ? seller.getId() : null) +
                '}';
    }
}
