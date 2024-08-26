package org.springbootdemo.ecommerceserver.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springbootdemo.ecommerceserver.Entity.Product;
import org.springbootdemo.ecommerceserver.dto.ProductResponse;
import org.springbootdemo.ecommerceserver.Repository.ProductRepo;
import org.springbootdemo.ecommerceserver.dto.ProductRequest;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    public final ProductRepo productRepo;

    public ProductResponse createProduct(ProductRequest productRequest){
        Product product =Product.builder()
                .name(productRequest.name())
                .description(productRequest.description())
                .price(productRequest.price()).
                build();
        productRepo.save(product);
        log.info("Product created successfully");
        return  new ProductResponse(product.getId(),product.getName(),product.getPrice(),product.getDescription());

    }
    public List<ProductResponse> getAllProduct(ProductResponse productResponse){
        return productRepo.findAll().stream()
                .map(product -> new ProductResponse(product.getId(),product.getName(),product.getPrice(),product.getDescription())).toList();
    }

}




