package firstspring.firstspringdemo.Mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import firstspring.firstspringdemo.DTOs.InventoryDto;
import firstspring.firstspringdemo.Entities.Inventory;

@Mapper(componentModel = "spring") //Important:  Makes the mapper a Spring component for dependency injection
public interface InventoryMapper {

    // Map product.productId to dto.productId
    @Mapping(source = "product.productId", target = "productId")
    // Map location.locationId to dto.locationId
    @Mapping(source = "location.locationId", target = "locationId")
    InventoryDto toDto(Inventory inventory);

    // add mappings to ignore the fields that are not in the DTO
    @Mapping(target = "lastStockUpdate", ignore = true)
    @Mapping(target = "location", ignore = true)
    @Mapping(target = "product", ignore = true)
    Inventory toEntity(InventoryDto inventoryDto);
}