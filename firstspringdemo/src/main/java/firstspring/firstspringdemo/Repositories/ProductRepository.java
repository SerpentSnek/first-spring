package firstspring.firstspringdemo.Repositories;

import firstspring.firstspringdemo.Entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    // Custom query methods can be defined here if needed
    // For example, to find all products by a specific category:
    List<Product> findByCategory(String category);
    List<Product> findByName(String name);
}
