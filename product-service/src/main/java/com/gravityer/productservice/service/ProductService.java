package com.gravityer.productservice.service;

import com.gravityer.productservice.controller.BaseResponse;
import com.gravityer.productservice.dto.ProductDto;
import com.gravityer.productservice.exceptions.InternalErrorException;
import com.gravityer.productservice.exceptions.NotFoundException;
import com.gravityer.productservice.mapper.ProductMapper;
import com.gravityer.productservice.model.Product;
import com.gravityer.productservice.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    public BaseResponse<List<Product>> getAllProducts() {
        try {
            var result = productRepository.findAll();
            if (result.isEmpty()) {
                return new BaseResponse<>(true, "Products fetched successfully", result);
            }
            return new BaseResponse<>(true, "Products fetched successfully", result);
        } catch (Exception e) {
            log.error("Error fetching products: {}", e.getMessage());
            throw new InternalErrorException("Something went wrong while fetching products");
        }
    }

    public BaseResponse<ProductDto> getProductById(Long id) {
        try {
            var result = productRepository.findById(id).orElseThrow(
                    () -> new NotFoundException("Product with id: " + id + " not found")
            );
            ProductDto productDto = productMapper.toDto(result);
            return new BaseResponse<>(true, "Product fetched successfully", productDto);
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error fetching product by id: {}", e.getMessage());
            throw new InternalErrorException("Something went wrong while fetching product by id");
        }
    }

    @Transactional
    public BaseResponse<Product> createProduct( ProductDto productDto) {
        try {
            Product product = productMapper.toEntity(productDto);
            var result = productRepository.save(product);
            return new BaseResponse<>(true, "Product created successfully", result);
        } catch (Exception e) {
            log.error("Error creating product: {}", e.getMessage());
            throw new InternalErrorException("Something went wrong while creating product");
        }
    }

    public BaseResponse<Product> updateProduct(Long id, ProductDto productDto) {
        try {
            var existingProduct = productRepository.findById(id).orElseThrow(
                    () -> new NotFoundException("Product with id: " + id + " not found")
            );
            existingProduct.setName(productDto.getName());
            existingProduct.setDescription(productDto.getDescription());
            existingProduct.setPrice(productDto.getPrice());
            var result = productRepository.save(existingProduct);
            return new BaseResponse<>(true, "Product updated successfully", result);
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error updating product: {}", e.getMessage());
            throw new InternalErrorException("Something went wrong while updating product");
        }
    }

    public BaseResponse<Product> deleteProduct(Long id) {
        try {
            var existingProduct = productRepository.findById(id).orElseThrow(
                    () -> new NotFoundException("Product with id: " + id + " not found")
            );
            productRepository.deleteById(id);
            return new BaseResponse<>(true, "Product deleted successfully", existingProduct);
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error deleting product: {}", e.getMessage());
            throw new InternalErrorException("Something went wrong while deleting product");
        }
    }
}
