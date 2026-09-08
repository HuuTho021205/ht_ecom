package com.ecommerce.ht_ecom.repository;

import com.ecommerce.ht_ecom.model.Category;
import com.ecommerce.ht_ecom.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findByCategory(Category category, Pageable pageable);
    Product findByProductName(String productName);

    Page<Product> findByProductNameContainingIgnoreCase(String keyword, Pageable pageDetails);

    boolean existsByProductNameIgnoreCase(String productName);
}
