package com.ecommerce.ht_ecom.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    @NotBlank
    @Size(min = 3, message = "Product name must contain at least 3 characters")
    private String productName;
    private String image;
    @NotBlank
    @Size(min = 6, message = "Product name must contain at least 6 characters")
    private String description;
    private Integer quantity;
    private BigDecimal price;
    private Integer discount;
    private BigDecimal sellingPrice;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
