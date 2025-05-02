package firstspring.firstspringdemo.Controllers;

import org.springframework.web.bind.annotation.RestController;

import firstspring.firstspringdemo.DAL.InventoryService;
import firstspring.firstspringdemo.DTOs.InventoryCreateDto;
import firstspring.firstspringdemo.DTOs.InventoryDto;
import firstspring.firstspringdemo.DTOs.InventoryUpdateDto;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    // GET /inventory: Get all inventory records
    @GetMapping
    public ResponseEntity<List<InventoryDto>> getAllInventory() {
        List<InventoryDto> inventoryList = inventoryService.getAllInventory();
        return new ResponseEntity<>(inventoryList, HttpStatus.OK);
    }

    // GET /inventory/{id}: Get a specific inventory record by ID
    @GetMapping("/{id}")
    public ResponseEntity<InventoryDto> getInventoryById(@PathVariable UUID id) {
        Optional<InventoryDto> inventory = inventoryService.getInventoryById(id);
        if (inventory.isPresent()) {
            return new ResponseEntity<>(inventory.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // POST /inventory: Create a new inventory record
    @PostMapping
    public ResponseEntity<InventoryDto> createInventory(@Valid @RequestBody InventoryCreateDto createInventoryDto) {
        InventoryDto createdInventory = inventoryService.createInventory(createInventoryDto);
        return new ResponseEntity<>(createdInventory, HttpStatus.CREATED);
    }

    // PUT /inventory/{id}: Update an existing inventory record
    @PutMapping("/{id}")
    public ResponseEntity<InventoryDto> updateInventory(@PathVariable UUID id, @Valid @RequestBody InventoryUpdateDto updateInventoryDto) {
        InventoryDto updatedInventory = inventoryService.updateInventory(id, updateInventoryDto);
        if (updatedInventory != null) {
            return new ResponseEntity<>(updatedInventory, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // PATCH /inventory/{id}: Partially update an existing inventory record
    // NOTE: this reuses the same DTO as the PUT method, but you could create a separate DTO if you want to allow partial updates of different fields
    @PatchMapping("/{id}")
    public ResponseEntity<InventoryDto> patchInventory(@PathVariable UUID id, @RequestBody InventoryUpdateDto updateInventoryDto) {
        InventoryDto patchedInventory = inventoryService.patchInventory(id, updateInventoryDto);
        if (patchedInventory != null) {
            return new ResponseEntity<>(patchedInventory, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // DELETE /inventory/{id}: Delete an inventory record
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInventory(@PathVariable UUID id) {
        inventoryService.deleteInventory(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
