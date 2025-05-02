package firstspring.firstspringdemo.Mappers;

import org.mapstruct.MappingTarget;

import firstspring.firstspringdemo.DTOs.ProductCreateDto;
import firstspring.firstspringdemo.DTOs.ProductDto;
import firstspring.firstspringdemo.DTOs.ProductUpdateDto;
import firstspring.firstspringdemo.Entities.Product;

public interface ProductMapper {
    // Define mapping methods here for Product and ProductDto
    ProductDto toDto(Product product);
    // dto to entity mapping
    // add mappings to ignore the fields that are not in the DTO
    Product toEntity(ProductDto productDto);
    Product createProductDtoToProduct(ProductCreateDto createProductDto);
    void updateProductFromDto(ProductUpdateDto updateProductDto, @MappingTarget Product product); //For the patch
    // Add any additional mapping methods as needed
}
