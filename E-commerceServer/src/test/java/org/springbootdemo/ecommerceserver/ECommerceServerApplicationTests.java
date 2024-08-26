package org.springbootdemo.ecommerceserver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springbootdemo.ecommerceserver.Controller.ProductController;
import org.springbootdemo.ecommerceserver.Service.ProductService;
import org.springbootdemo.ecommerceserver.dto.ProductRequest;
import org.springbootdemo.ecommerceserver.dto.ProductResponse;
import org.springbootdemo.ecommerceserver.Entity.Product;
import org.springbootdemo.ecommerceserver.Repository.ProductRepo;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class ECommerceServerApplicationTests {

    private MockMvc mockMvc;

    @Mock
    private ProductRepo productRepo;

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @Test
    public void contextLoads() {
        // This test ensures that the Spring application context is loaded successfully.
    }

    @Test
    public void testCreateProduct() throws Exception {
        ProductRequest productRequest = new ProductRequest(1, "Test Product", 100, "Test Description");
        ProductResponse productResponse = new ProductResponse(1, "Test Product", 100, "Test Description");

        when(productService.createProduct(any(ProductRequest.class))).thenReturn(productResponse);

        mockMvc.perform(post("/api/product/addProduct")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"name\":\"Test Product\",\"description\":\"Test Description\",\"price\":100}"))
                .andExpect(status().isCreated())
                .andExpect(content().json("{\"id\":1,\"name\":\"Test Product\",\"price\":100,\"description\":\"Test Description\"}"));
    }

    @Test
    public void testGetAllProduct() throws Exception {
        ProductResponse productResponse = new ProductResponse(1, "Test Product", 100, "Test Description");
        when(productService.getAllProduct(any(ProductResponse.class))).thenReturn(Collections.singletonList(productResponse));

        mockMvc.perform(get("/api/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":1,\"name\":\"Test Product\",\"price\":100,\"description\":\"Test Description\"}]"));
    }

    @Test
    public void testCreateProductService() {
        ProductRequest productRequest = new ProductRequest(1, "Test Product", 100, "Test Description");
        Product product = Product.builder()
                .name(productRequest.name())
                .description(productRequest.description())
                .price(productRequest.price())
                .build();
        when(productRepo.save(any(Product.class))).thenReturn(product);

        ProductResponse productResponse = productService.createProduct(productRequest);

        assertEquals(product.getName(), productResponse.name());
        assertEquals(product.getDescription(), productResponse.description());
        assertEquals(product.getPrice(), productResponse.price());
    }

    @Test
    public void testGetAllProductService() {
        Product product = Product.builder()
                .name("Test Product")
                .description("Test Description")
                .price(100)
                .build();
        when(productRepo.findAll()).thenReturn(Collections.singletonList(product));

        List<ProductResponse> productResponses = productService.getAllProduct(new ProductResponse(1, "Test Product", 100, "Test Description"));

        assertEquals(1, productResponses.size());
        assertEquals(product.getName(), productResponses.get(0).name());
        assertEquals(product.getDescription(), productResponses.get(0).description());
        assertEquals(product.getPrice(), productResponses.get(0).price());
    }
}
