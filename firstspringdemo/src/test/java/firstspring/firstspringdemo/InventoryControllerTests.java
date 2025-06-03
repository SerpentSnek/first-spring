package firstspring.firstspringdemo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import firstspring.firstspringdemo.Controllers.InventoryController;
import firstspring.firstspringdemo.DAL.InventoryService;
import firstspring.firstspringdemo.DTOs.InventoryDto;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InventoryControllerTest {

    @Mock
    private InventoryService inventoryService;

    @InjectMocks
    private InventoryController inventoryController;

    private List<InventoryDto> inventoryList;

    @BeforeEach
    void setUp() {
        // Initialize common test data
        InventoryDto dto1 = new InventoryDto();
        dto1.setInventoryId(UUID.randomUUID());
        dto1.setProductId(UUID.randomUUID());
        dto1.setLocationId(UUID.randomUUID());
        dto1.setQuantity(100);

        InventoryDto dto2 = new InventoryDto();
        dto2.setInventoryId(UUID.randomUUID());
        dto2.setProductId(UUID.randomUUID());
        dto2.setLocationId(UUID.randomUUID());
        dto2.setQuantity(50);

        inventoryList = Arrays.asList(dto1, dto2);
    }

    @Test
    void testGetAllInventory_Success() {
        // Arrange
        when(inventoryService.getAllInventory()).thenReturn(inventoryList);

        // Act
        ResponseEntity<List<InventoryDto>> response = inventoryController.getAllInventory();

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals(inventoryList, response.getBody());

        // Verify that the service method was called
        verify(inventoryService, times(1)).getAllInventory();
    }

    @Test
    void testGetAllInventory_EmptyList() {
        // Arrange
        when(inventoryService.getAllInventory()).thenReturn(Collections.emptyList());

        // Act
        ResponseEntity<List<InventoryDto>> response = inventoryController.getAllInventory();

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());

        // Verify that the service method was called
        verify(inventoryService, times(1)).getAllInventory();
    }
}