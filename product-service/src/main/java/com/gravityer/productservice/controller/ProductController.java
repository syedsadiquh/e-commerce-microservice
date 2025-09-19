package com.gravityer.productservice.controller;

import com.gravityer.productservice.dto.ProductDto;
import com.gravityer.productservice.model.Product;
import com.gravityer.productservice.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<BaseResponse<List<Product>>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<BaseResponse<ProductDto>> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<BaseResponse<Product>> createProduct(@Valid @RequestBody ProductDto productDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(productDto));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<BaseResponse<Product>> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductDto productDto) {
        return ResponseEntity.ok(productService.updateProduct(id, productDto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<BaseResponse<Product>> deleteProduct(@PathVariable Long id) {
        return ResponseEntity.ok(productService.deleteProduct(id));
    }
}
