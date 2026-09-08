package com.ecommerce.ht_ecom.controller;

import com.ecommerce.ht_ecom.config.AppConstants;
import com.ecommerce.ht_ecom.payload.ProductDTO;
import com.ecommerce.ht_ecom.payload.ProductResponse;
import com.ecommerce.ht_ecom.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping("/admin/category/{categoryId}/product")
    public  ResponseEntity<ProductDTO> addProduct(@PathVariable Long categoryId, @Valid @RequestBody ProductDTO productDTO){
        ProductDTO addedProduct = productService.addProduct(categoryId, productDTO);
        return new ResponseEntity<>(addedProduct, HttpStatus.CREATED);
    }

    @GetMapping("/public/products")
    public ResponseEntity<ProductResponse> getAllProduct(@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
                                                  @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
                                                  @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_PRODUCTS_BY, required = false) String sortBy,
                                                  @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_ORDER, required = false) String sortDir){
        ProductResponse productResponse = productService.getAllProduct(pageNumber,pageSize,sortBy,sortDir);
        return new ResponseEntity<>(productResponse,HttpStatus.OK);
    }

    @GetMapping("/public/categories/{categoryId}/products")
    public ResponseEntity<ProductResponse> getProductByCategory(@PathVariable Long categoryId,
                                                                @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
                                                                @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
                                                                @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_PRODUCTS_BY, required = false) String sortBy,
                                                                @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_ORDER, required = false) String sortDir){

        ProductResponse productResponse = productService.searchProductByCategory(categoryId, pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(productResponse,HttpStatus.OK);
    }

    @GetMapping("/public/products/keyword/{keyword}")
    public ResponseEntity<ProductResponse> getProductByKeyword(@PathVariable String keyword,
                                                                @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
                                                                @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
                                                                @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_PRODUCTS_BY, required = false) String sortBy,
                                                                @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_ORDER, required = false) String sortDir){

        ProductResponse productResponse = productService.searchProductByKeyword(keyword, pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(productResponse,HttpStatus.OK);
    }

    @PutMapping("/admin/products/{productId}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long productId,
           @Valid @RequestBody ProductDTO productDTO){
        ProductDTO updatedProduct = productService.updateProduct(productId,productDTO);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    @DeleteMapping("/admin/products/{productId}")
    public ResponseEntity<ProductDTO> deleteProduct(@PathVariable Long productId){
        ProductDTO updatedProduct = productService.deleteProduct(productId);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    @PutMapping("/products/{productId}/image")
    public ResponseEntity<ProductDTO> updateProductImage(@PathVariable Long productId,
                                                         @RequestParam("image") MultipartFile image) throws IOException {
        ProductDTO updatedProductImage = productService.updateProductImage(productId,image);
        return new ResponseEntity<>(updatedProductImage,HttpStatus.OK);
    }
}
