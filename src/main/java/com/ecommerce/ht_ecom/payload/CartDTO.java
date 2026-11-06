package com.ecommerce.ht_ecom.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CartDTO {
    private Long cartId;
    private List<ProductDTO> products = new ArrayList<>();
    private BigDecimal totalPrice = BigDecimal.ZERO;

}
