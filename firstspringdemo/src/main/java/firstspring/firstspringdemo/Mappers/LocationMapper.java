package firstspring.firstspringdemo.Mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import firstspring.firstspringdemo.DTOs.LocationCreateDto;
import firstspring.firstspringdemo.DTOs.LocationDto;
import firstspring.firstspringdemo.DTOs.LocationUpdateDto;
import firstspring.firstspringdemo.Entities.Location;

@Mapper(componentModel = "spring") //Important:  Makes the mapper a Spring component for dependency injection
public interface LocationMapper {
    
    // Define mapping methods here if needed
    // For example, if you have a Location entity and a LocationDto, you can define methods to convert between them.
    // ignore id field in the DTO
    LocationDto toDto(Location location);
    // dto to entity mapping
    // add mappings to ignore the fields that are not in the DTO
    @Mapping(target = "contactPerson", ignore = true)
    @Mapping(target = "type", ignore = true)
    Location toEntity(LocationDto locationDto);

    // add mappings to ignore the fields that are not in the DTO
    @Mapping(target = "contactPerson", ignore = true)
    @Mapping(target = "type", ignore = true)
    Location createLocationDtoToLocation(LocationCreateDto createLocationDto);

    // add mappings to ignore the fields that are not in the DTO
    @Mapping(target = "contactPerson", ignore = true)
    @Mapping(target = "type", ignore = true)
    // map locationId to id
    void updateLocationFromDto(LocationUpdateDto updateLocationDto, @MappingTarget Location location); //For the patch
    // Add any additional mapping methods as needed

}
