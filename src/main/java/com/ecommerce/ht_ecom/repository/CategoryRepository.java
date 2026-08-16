package com.ecommerce.ht_ecom.repository;

import com.ecommerce.ht_ecom.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CategoryRepository extends JpaRepository<Category,Long> {
    Category findByCategoryName(String categoryName);
}
