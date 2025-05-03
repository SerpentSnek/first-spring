package firstspring.firstspringdemo.Repositories;

import firstspring.firstspringdemo.Entities.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;
import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory, UUID> {
    // Custom query methods can be defined here if needed
    // For example, to find all inventory items for a specific product:
    List<Inventory> findByProductId(UUID productId);
    List<Inventory> findByLocationId(UUID locationId);

}
