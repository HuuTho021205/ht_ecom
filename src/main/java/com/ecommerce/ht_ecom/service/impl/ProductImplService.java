package com.ecommerce.ht_ecom.service.impl;

import com.ecommerce.ht_ecom.exceptions.APIException;
import com.ecommerce.ht_ecom.exceptions.ResourceNotFoundException;
import com.ecommerce.ht_ecom.model.Category;
import com.ecommerce.ht_ecom.model.Product;
import com.ecommerce.ht_ecom.payload.ProductDTO;
import com.ecommerce.ht_ecom.payload.ProductResponse;
import com.ecommerce.ht_ecom.repository.CategoryRepository;
import com.ecommerce.ht_ecom.repository.ProductRepository;
import com.ecommerce.ht_ecom.service.FileService;
import com.ecommerce.ht_ecom.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductImplService implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final FileService fileService;
    @Value("${project.image}")
    private String path;
    @Override
    public ProductDTO addProduct(Long categoryId, ProductDTO productDTO) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("Category","categoryId",categoryId));
        boolean isProductPresent = productRepository.existsByProductNameIgnoreCase(productDTO.getProductName());
        if(isProductPresent){
            throw new APIException("Product with product name " + productDTO.getProductName() + " is already exists");
        }
        Product product = modelMapper.map(productDTO,Product.class);
        product.setImage("default.png");
        product.setCategory(category);
        BigDecimal sellingPrice = product.getPrice();
        if (product.getDiscount() != null && product.getDiscount() > 0){
            BigDecimal discount = BigDecimal.valueOf(product.getDiscount()).movePointLeft(2);
            BigDecimal discountAmount = product.getPrice().multiply(discount);

            sellingPrice = product.getPrice().subtract(discountAmount);
        }
        product.setSellingPrice(sellingPrice);

        Product savedProduct = productRepository.save(product);
        return modelMapper.map(savedProduct,ProductDTO.class);
    }

    @Override
    public ProductResponse getAllProduct(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageDetails = PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> productPage = productRepository.findAll(pageDetails);
        List<Product> products = productPage.getContent();
        if (products.isEmpty()){
            throw new APIException("No product created till now");
        }
        List<ProductDTO> productDTOS = products.stream()
                .map(product-> modelMapper.map(product,ProductDTO.class))
                .toList();
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOS);
        productResponse.setPageNumber(productPage.getNumber());
        productResponse.setPageSize(productPage.getSize());
        productResponse.setTotalElements(productPage.getTotalElements());
        productResponse.setTotalPages(productPage.getTotalPages());
        productResponse.setLastPage(productPage.isLast());

        return productResponse;
    }

    @Override
    public ProductResponse searchProductByCategory(Long categoryId, Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("Category", "categoryId", categoryId));
        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageDetails = PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> productPage = productRepository.findByCategory(category,pageDetails);
        List<Product> products = productPage.getContent();
        if(products.isEmpty()){
            throw new APIException("No product created with categoryId:" +categoryId+ " till now");
        }
        List<ProductDTO> productDTOS = products.stream()
                .map(product -> modelMapper.map(product,ProductDTO.class))
                .toList();
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOS);
        productResponse.setPageNumber(productPage.getNumber());
        productResponse.setPageSize(productPage.getSize());
        productResponse.setTotalElements(productPage.getTotalElements());
        productResponse.setTotalPages(productPage.getTotalPages());
        productResponse.setLastPage(productPage.isLast());
        return productResponse;
    }

    @Override
    public ProductResponse searchProductByKeyword(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageDetails = PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> productPage = productRepository.findByProductNameContainingIgnoreCase(keyword,pageDetails);
        List<Product> products = productPage.getContent();
        if(products.isEmpty()){
            throw new APIException("No product created with productName like:" +keyword+ " till now");
        }

        List<ProductDTO> productDTOS = products.stream()
                .map(product -> modelMapper.map(product,ProductDTO.class))
                .toList();
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOS);
        productResponse.setPageNumber(productPage.getNumber());
        productResponse.setPageSize(productPage.getSize());
        productResponse.setTotalElements(productPage.getTotalElements());
        productResponse.setTotalPages(productPage.getTotalPages());
        productResponse.setLastPage(productPage.isLast());
        return productResponse;
    }

    @Override
    public ProductDTO updateProduct(Long productId, ProductDTO productDTO) {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "ProductId", productId));
        Product productWithSameName = productRepository.findByProductName(productDTO.getProductName());
        if(productWithSameName != null && !productWithSameName.getProductId().equals(productId)){
            throw new APIException("ProductName " + productDTO.getProductName() +" is already exits");
        }
        Product product = modelMapper.map(productDTO,Product.class);
        existingProduct.setProductName(product.getProductName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setQuantity(product.getQuantity());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setDiscount(product.getDiscount());

        BigDecimal sellingPrice = product.getPrice();
        if (product.getPrice() != null) {
            if (product.getDiscount() != null && product.getDiscount() > 0) {
                BigDecimal discountRate = BigDecimal.valueOf(product.getDiscount()).movePointLeft(2);
                BigDecimal discountAmount = product.getPrice().multiply(discountRate);
                sellingPrice = product.getPrice().subtract(discountAmount);
            }
            existingProduct.setSellingPrice(sellingPrice);
        }

        Product updatedProduct = productRepository.save(existingProduct);

        return modelMapper.map(updatedProduct,ProductDTO.class);
    }

    @Override
    public ProductDTO deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(()-> new ResourceNotFoundException("Product","productId", productId));
        ProductDTO productDTO = modelMapper.map(product,ProductDTO.class);
        productRepository.delete(product);
        return productDTO;
    }

    @Override
    public ProductDTO updateProductImage(Long productId, MultipartFile fileImage) throws IOException {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId",productId));
        String fileName = fileService.uploadImage(path, fileImage);
        product.setImage(fileName);
        Product savedProduct = productRepository.save(product);
        return modelMapper.map(savedProduct,ProductDTO.class);

    }
}
