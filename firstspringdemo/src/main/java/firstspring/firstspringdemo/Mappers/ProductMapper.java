package firstspring.firstspringdemo.Mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import firstspring.firstspringdemo.DTOs.ProductCreateDto;
import firstspring.firstspringdemo.DTOs.ProductDto;
import firstspring.firstspringdemo.DTOs.ProductUpdateDto;
import firstspring.firstspringdemo.Entities.Product;

@Mapper(componentModel = "spring") //Important:  Makes the mapper a Spring component for dependency injection
public interface ProductMapper {
    // Define mapping methods here for Product and ProductDto
    @Mapping(target = "imgUrl", ignore = true) // Ignore location field in the DTO
    @Mapping(target = "locationId", ignore = true) // Ignore location field in the DTO
    @Mapping(target = "price", ignore = true) // Ignore location field in the DTO
    @Mapping(target = "quantity", ignore = true) // Ignore location field in the DTO
    ProductDto toDto(Product product);
    
    // dto to entity mapping
    // add mappings to ignore the fields that are not in the DTO
    @Mapping(target = "category", ignore = true) // Ignore location field in the DTO")
    @Mapping(target = "imageUrl", ignore = true)
    @Mapping(target = "manufacturer", ignore = true)
    @Mapping(target = "sku", ignore = true)
    @Mapping(target = "unitPrice", ignore = true)
    Product toEntity(ProductDto productDto);
    
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "imageUrl", ignore = true)
    @Mapping(target = "manufacturer", ignore = true)
    @Mapping(target = "sku", ignore = true)
    @Mapping(target = "unitPrice", ignore = true)
    Product createProductDtoToProduct(ProductCreateDto createProductDto);
    
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "imageUrl", ignore = true)
    @Mapping(target = "manufacturer", ignore = true)
    @Mapping(target = "productId", ignore = true)
    @Mapping(target = "sku", ignore = true)
    @Mapping(target = "unitPrice", ignore = true)
    void updateProductFromDto(ProductUpdateDto updateProductDto, @MappingTarget Product product); //For the patch
    // Add any additional mapping methods as needed
}
