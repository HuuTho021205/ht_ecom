package com.ecommerce.ht_ecom.service;

import com.ecommerce.ht_ecom.payload.CategoryDTO;
import com.ecommerce.ht_ecom.payload.CategoryResponse;

public interface CategoryService {
    CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
    CategoryDTO createCategory(CategoryDTO category);
    CategoryDTO updateCategory(Long categoryId, CategoryDTO category);
    CategoryDTO deleteCategory(Long categoryId);

}
