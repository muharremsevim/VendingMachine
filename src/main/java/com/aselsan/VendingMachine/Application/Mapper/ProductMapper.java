package com.aselsan.VendingMachine.Application.Mapper;

import com.aselsan.VendingMachine.Application.Dto.ProductDto;
import com.aselsan.VendingMachine.Domain.Model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    ProductDto toDto(Product product);

    Product toEntity(ProductDto productDto);

    List<ProductDto> toDtoList(List<Product> productList);

    List<Product> toEntityList(List<ProductDto> productDtoList);
}