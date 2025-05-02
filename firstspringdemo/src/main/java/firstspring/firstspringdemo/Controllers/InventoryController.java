package firstspring.firstspringdemo.Controllers;

import org.springframework.web.bind.annotation.RestController;

import firstspring.firstspringdemo.Converters.InventoryConverter;
import firstspring.firstspringdemo.DTOs.InventoryDTO;
import firstspring.firstspringdemo.DTOs.InventoryUpdateDTO;
import firstspring.firstspringdemo.Entities.Inventory;
import firstspring.firstspringdemo.Services.InventoryService;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/inventory")
// This annotation maps HTTP requests to /inventory to this controller
public class InventoryController {
    // Establish the HTTP methods for the inventory controller
    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private InventoryConverter inventoryConverter;  // Inject the converter

    // GET all inventory records
    @GetMapping
    public ResponseEntity<List<InventoryDTO>> getAllInventory() {
        List<Inventory> inventoryList = inventoryService.getAllInventory();
        List<InventoryDTO> inventoryDTOList = inventoryList.stream()
                .map(inventoryConverter::convertToDTO) // Use the converter
                .collect(Collectors.toList());
        return ResponseEntity.ok(inventoryDTOList);
    }

    // GET an inventory record by ID
    @GetMapping("/{inventoryId}")
    public ResponseEntity<InventoryDTO> getInventoryById(@PathVariable UUID inventoryId) {
        Optional<Inventory> inventory = inventoryService.getInventoryById(inventoryId);
        if (inventory.isPresent()) {
            return ResponseEntity.ok(inventoryConverter.convertToDTO(inventory.get())); // Use the converter
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // POST a new inventory record
    @PostMapping
    public ResponseEntity<InventoryDTO> createInventory(@Valid @RequestBody InventoryDTO inventoryDTO) {
        Inventory inventory = inventoryConverter.convertToEntity(inventoryDTO); // Use the converter
        Inventory createdInventory = inventoryService.createInventory(inventory);
        return new ResponseEntity<>(inventoryConverter.convertToDTO(createdInventory), HttpStatus.CREATED); // Use the converter
    }

    // PUT (complete update) an inventory record
    @PutMapping("/{inventoryId}")
    public ResponseEntity<InventoryDTO> updateInventory(@PathVariable UUID inventoryId, @Valid @RequestBody InventoryDTO inventoryDTO) {
        if (!inventoryService.existsById(inventoryId)) {
            return ResponseEntity.notFound().build();
        }
        Inventory inventory = inventoryConverter.convertToEntity(inventoryDTO); // Use the converter
        inventory.setInventoryId(inventoryId); // Ensure the ID is set for updating
        Inventory updatedInventory = inventoryService.updateInventory(inventoryId, inventory);
        return ResponseEntity.ok(inventoryConverter.convertToDTO(updatedInventory)); // Use the converter
    }

    // PATCH (partial update) an inventory record
    @PatchMapping("/{inventoryId}")
    public ResponseEntity<?> patchInventory(@PathVariable UUID inventoryId, @RequestBody InventoryUpdateDTO inventoryUpdateDTO) {
        Optional<Inventory> existingInventory = inventoryService.getInventoryById(inventoryId);

        if (existingInventory.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Inventory inventoryToUpdate = existingInventory.get();

        // Apply updates from the DTO
        if (inventoryUpdateDTO.getQuantity() != null) {
            inventoryToUpdate.setQuantity(inventoryUpdateDTO.getQuantity());
        }
        if (inventoryUpdateDTO.getReorderPoint() != null) {
            inventoryToUpdate.setReorderPoint(inventoryUpdateDTO.getReorderPoint());
        }
        if (inventoryUpdateDTO.getReorderQuantity() != null) {
            inventoryToUpdate.setReorderQuantity(inventoryUpdateDTO.getReorderQuantity());
        }

        Inventory updatedInventory = inventoryService.updateInventory(inventoryId, inventoryToUpdate);
        return ResponseEntity.ok(inventoryConverter.convertToDTO(updatedInventory)); // Use the converter
    }

    // DELETE an inventory record
    @DeleteMapping("/{inventoryId}")
    public ResponseEntity<Void> deleteInventory(@PathVariable UUID inventoryId) {
        if (!inventoryService.existsById(inventoryId)) {
            return ResponseEntity.notFound().build();
        }
        inventoryService.deleteInventory(inventoryId);
        return ResponseEntity.noContent().build();
    }
}
