package firstspring.firstspringdemo.Controllers;

import firstspring.firstspringdemo.DTOs.LocationCreateDto;
import firstspring.firstspringdemo.DTOs.LocationDto;
import firstspring.firstspringdemo.DTOs.LocationUpdateDto;
import firstspring.firstspringdemo.DAL.LocationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/locations")
public class LocationController {

    @Autowired
    private LocationService locationService;

    // GET /locations: Get all locations
    @GetMapping
    public ResponseEntity<List<LocationDto>> getAllLocations() {
        List<LocationDto> locationList = locationService.getAllLocations();
        return new ResponseEntity<>(locationList, HttpStatus.OK);
    }

    // GET /locations/{id}: Get a specific location by ID
    @GetMapping("/{id}")
    public ResponseEntity<LocationDto> getLocationById(@PathVariable UUID id) {
        Optional<LocationDto> location = locationService.getLocationById(id);
        if (location.isPresent()) {
            return new ResponseEntity<>(location.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // POST /locations: Create a new location
    @PostMapping
    public ResponseEntity<LocationDto> createLocation(@Valid @RequestBody LocationCreateDto createLocationDto) {
        LocationDto createdLocation = locationService.createLocation(createLocationDto);
        return new ResponseEntity<>(createdLocation, HttpStatus.CREATED);
    }

    // PUT /locations/{id}: Update an existing location (full replacement)
    @PutMapping("/{id}")
    public ResponseEntity<LocationDto> updateLocation(@PathVariable UUID id, @Valid @RequestBody LocationUpdateDto updateLocationDto) {
        LocationDto updatedLocation = locationService.updateLocation(id, updateLocationDto);
        if (updatedLocation != null) {
            return new ResponseEntity<>(updatedLocation, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // DELETE /locations/{id}: Delete a location
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLocation(@PathVariable UUID id) {
        locationService.deleteLocation(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}