package firstspring.firstspringdemo.DAL;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import firstspring.firstspringdemo.DTOs.InventoryCreateDto;
import firstspring.firstspringdemo.DTOs.InventoryDto;
import firstspring.firstspringdemo.DTOs.InventoryUpdateDto;
import firstspring.firstspringdemo.DTOs.LocationDto;
import firstspring.firstspringdemo.DTOs.ProductDto;
import firstspring.firstspringdemo.Entities.Inventory;
import firstspring.firstspringdemo.Entities.Product;
import firstspring.firstspringdemo.Entities.Location;
import firstspring.firstspringdemo.Mappers.InventoryMapper;
import firstspring.firstspringdemo.Mappers.LocationMapper;
import firstspring.firstspringdemo.Mappers.ProductMapper;
import firstspring.firstspringdemo.Repositories.InventoryRepository;

@Service
public class InventoryService {
    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private InventoryMapper inventoryMapper;

    @Autowired
    private ProductMapper productMapper;  // Depend on ProductMapper
    @Autowired
    private LocationMapper locationMapper;  // Depend on LocationMapper

    @Autowired
    private ProductService productService;  // Depend on ProductService

    @Autowired
    private LocationService locationService;  // Depend on LocationService

    
    public List<InventoryDto> getAllInventory() {
        return inventoryRepository.findAll().stream()
                .map(inventoryMapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<InventoryDto> getInventoryById(UUID id) {
        return inventoryRepository.findById(id)
                .map(inventoryMapper::toDto);
    }

    public InventoryDto createInventory(InventoryCreateDto createInventoryDto) {
        // 1.  Fetch the Product and Location entities using the ProductService and LocationService
        ProductDto productDto = productService.getProductById(createInventoryDto.getProductId()).orElse(null); //change
        LocationDto locationDto = locationService.getLocationById(createInventoryDto.getLocationId()).orElse(null); //change

        if (productDto == null || locationDto == null) {
            // Handle the case where the Product or Location doesn't exist
            throw new IllegalArgumentException("Invalid Product or Location ID");
        }

        // 2. Create the Inventory entity
        Inventory inventory = new Inventory();
        inventory.setQuantity(createInventoryDto.getQuantity());

        // 3. Map the productDto and locationDto to actual entity and save it in the inventory
        Product product = productMapper.toEntity(productDto);
        Location location = locationMapper.toEntity(locationDto);
        inventory.setProduct(product);
        inventory.setLocation(location);

        Inventory savedInventory = inventoryRepository.save(inventory);
        return inventoryMapper.toDto(savedInventory);
    }

    public InventoryDto updateInventory(UUID id, InventoryUpdateDto updateInventoryDto) {
        Optional<Inventory> existingInventory = inventoryRepository.findById(id);
        if (existingInventory.isPresent()) {
            Inventory inventory = existingInventory.get();
            // Update properties from updateInventoryDto to inventory
            inventory.setQuantity(updateInventoryDto.getQuantity());
            // Again, you'll need to handle Product and Location updates here
            Inventory updatedInventory = inventoryRepository.save(inventory);
            return inventoryMapper.toDto(updatedInventory);
        } else {
            return null; // Or throw an exception
        }
    }

   public InventoryDto patchInventory(UUID id, InventoryUpdateDto updateInventoryDto) {
        Optional<Inventory> existingInventory = inventoryRepository.findById(id);
        if (existingInventory.isPresent()) {
            Inventory inventory = existingInventory.get();

            // Update only the fields that are not null in the DTO
            if (updateInventoryDto.getProductId() != null) {
                // VERY IMPORTANT! Fetch the Product, Use the ProductService
                ProductDto productDto = productService.getProductById(updateInventoryDto.getProductId()).orElse(null);
                if (productDto == null) {
                    throw new IllegalArgumentException("Invalid Product ID");
                }
                Product product = productMapper.toEntity(productDto);
                inventory.setProduct(product);
            }
            if (updateInventoryDto.getLocationId() != null) {
                // VERY IMPORTANT! Fetch the Location, Use the LocationService
                LocationDto locationDto = locationService.getLocationById(updateInventoryDto.getLocationId()).orElse(null);
                if (locationDto == null) {
                    throw new IllegalArgumentException("Invalid Location ID");
                }
                Location location = locationMapper.toEntity(locationDto);
                inventory.setLocation(location);
            }
            if (updateInventoryDto.getQuantity() != null) {
                inventory.setQuantity(updateInventoryDto.getQuantity());
            }

            Inventory updatedInventory = inventoryRepository.save(inventory);
            return inventoryMapper.toDto(updatedInventory);
        } else {
            return null; // Or throw an exception: Inventory not found
        }
    }

    public void deleteInventory(UUID id) {
        inventoryRepository.deleteById(id);
    }
}
