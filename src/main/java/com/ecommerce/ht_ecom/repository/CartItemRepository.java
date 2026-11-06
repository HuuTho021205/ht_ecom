package com.ecommerce.ht_ecom.repository;

import com.ecommerce.ht_ecom.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
