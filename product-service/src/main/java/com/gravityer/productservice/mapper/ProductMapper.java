package com.gravityer.productservice.mapper;

import com.gravityer.productservice.dto.ProductDto;
import com.gravityer.productservice.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    public ProductDto toDto(Product product);

    @Mapping(target = "id", ignore = true)
    public Product toEntity(ProductDto productDto);
}
