package com.ecommerce.ht_ecom.repository;

import com.ecommerce.ht_ecom.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
