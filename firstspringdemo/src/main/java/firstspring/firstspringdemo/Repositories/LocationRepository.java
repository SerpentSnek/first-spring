package firstspring.firstspringdemo.Repositories;

import firstspring.firstspringdemo.Entities.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface LocationRepository extends JpaRepository<Location, UUID> {
    // Custom query methods can be defined here if needed
    // For example, to find all products by a specific category:
    List<Location> findByCategory(String category);
    List<Location> findByName(String name);
}
