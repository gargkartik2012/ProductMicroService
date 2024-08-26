package org.springbootdemo.ecommerceserver.Controller;


import lombok.RequiredArgsConstructor;
import org.springbootdemo.ecommerceserver.dto.ProductResponse;
import org.springbootdemo.ecommerceserver.Service.ProductService;
import org.springbootdemo.ecommerceserver.dto.ProductRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {


    private final ProductService productService;




    @PostMapping("/addProduct")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse createProduct(@RequestBody ProductRequest productRequest){
        return productService.createProduct(productRequest);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAllProduct(@RequestBody ProductResponse productResponse){
        return productService.getAllProduct(productResponse);
    }



}
