package firstspring.firstspringdemo.DAL;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import firstspring.firstspringdemo.DTOs.LocationCreateDto;
import firstspring.firstspringdemo.DTOs.LocationDto;
import firstspring.firstspringdemo.DTOs.LocationUpdateDto;
import firstspring.firstspringdemo.Entities.Location;
import firstspring.firstspringdemo.Mappers.LocationMapper;
import firstspring.firstspringdemo.Repositories.LocationRepository;

@Service
public class LocationService {
    
    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private LocationMapper locationMapper;

    public List<LocationDto> getAllLocations() {
        List<Location> locations = locationRepository.findAll();
        return locations.stream()
                .map(locationMapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<LocationDto> getLocationById(UUID id) {
        return locationRepository.findById(id)
                .map(locationMapper::toDto);
    }

    public LocationDto createLocation(LocationCreateDto createLocationDto) {
        Location location = locationMapper.createLocationDtoToLocation(createLocationDto);
        Location savedLocation = locationRepository.save(location);
        return locationMapper.toDto(savedLocation);
    }

    public LocationDto updateLocation(UUID id, LocationUpdateDto updateLocationDto) {
        Optional<Location> optionalLocation = locationRepository.findById(id);
        if (optionalLocation.isPresent()) {
            Location location = optionalLocation.get();
            locationMapper.updateLocationFromDto(updateLocationDto, location);
            Location updatedLocation = locationRepository.save(location);
            return locationMapper.toDto(updatedLocation);
        }
        return null;
    }

    public void deleteLocation(UUID id) {
        locationRepository.deleteById(id);
    }
}
