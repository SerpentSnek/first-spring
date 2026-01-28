# Architecture and Development Guide

## Table of Contents

1. [Architecture Overview](#architecture-overview)
2. [Backend Architecture](#backend-architecture)
3. [Frontend Architecture](#frontend-architecture)
4. [Data Flow](#data-flow)
5. [Development Workflow](#development-workflow)
6. [Code Style and Conventions](#code-style-and-conventions)
7. [Testing Strategy](#testing-strategy)

---

## Architecture Overview

FirstSpringDemo follows a **layered architecture** pattern with clear separation of concerns:

```
┌─────────────────────────────────────────────────────────┐
│              Flutter Mobile Client                       │
├─────────────────────────────────────────────────────────┤
│              REST API / GraphQL Gateway                  │
├─────────────────────────────────────────────────────────┤
│  Controllers → Services (DAL) → Repositories → Database │
├─────────────────────────────────────────────────────────┤
│           PostgreSQL Inventory Management DB             │
└─────────────────────────────────────────────────────────┘
```

### Key Principles

- **Separation of Concerns:** Each layer has a specific responsibility
- **Dependency Injection:** Spring manages bean lifecycle and dependencies
- **Data Transfer Objects (DTOs):** API contracts separate from persistence models
- **Automatic Mapping:** MapStruct handles DTO ↔ Entity conversions

---

## Backend Architecture

### Layered Architecture

#### 1. Controller Layer (`Controllers/`)

**Responsibility:** Handle HTTP requests and route them to appropriate services

**Key Files:**
- `ProductController.java`
- `LocationController.java`
- `InventoryController.java`

**Example:**
```java
@RestController
@RequestMapping("/products")
public class ProductController {
    
    @Autowired
    private ProductService productService;
    
    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        // Handle HTTP GET request
    }
    
    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductCreateDto dto) {
        // Handle HTTP POST request with validation
    }
}
```

**Best Practices:**
- Keep controllers thin (delegate to services)
- Validate input using `@Valid` annotations
- Return appropriate HTTP status codes
- Handle exceptions gracefully

---

#### 2. Service Layer (DAL - Data Access Layer)

**Responsibility:** Business logic, data validation, and orchestration

**Key Files:**
- `ProductService.java`
- `LocationService.java`
- `InventoryService.java`

**Example:**
```java
@Service
public class ProductService {
    
    @Autowired
    private ProductRepository repository;
    
    @Autowired
    private ProductMapper mapper;
    
    public List<ProductDto> getAllProducts() {
        // Business logic here
        return repository.findAll()
            .stream()
            .map(mapper::toDto)
            .collect(Collectors.toList());
    }
}
```

**Best Practices:**
- Implement business logic here, not in controllers
- Use transactions (`@Transactional`) when needed
- Handle domain validations
- Delegate data access to repositories

---

#### 3. Repository Layer (`Repositories/`)

**Responsibility:** Database operations and queries

**Key Pattern:** Spring Data JPA repositories extending `JpaRepository<Entity, ID>`

**Example:**
```java
public interface ProductRepository extends JpaRepository<Product, UUID> {
    Optional<Product> findBySku(String sku);
    List<Product> findByNameContaining(String name);
}
```

**Features:**
- CRUD operations provided automatically
- Custom query methods defined by naming convention
- `@Query` annotation for complex queries

---

#### 4. Data Transfer Object Layer (`DTOs/`)

**Responsibility:** Define API contracts separate from database entities

**Pattern:** Separate classes for different operations

```
ProductDto          - For GET responses
ProductCreateDto    - For POST requests (create)
ProductUpdateDto    - For PUT requests (update)
```

**Benefits:**
- API contract independence from persistence model
- Input validation annotations
- Selective field exposure
- Version API independently

---

#### 5. Entity Layer (`Entities/`)

**Responsibility:** JPA entity classes mapping to database tables

**Example:**
```java
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @Column(nullable = false)
    private String name;
    
    // getters, setters
}
```

---

#### 6. Mapper Layer (`Mappers/`)

**Responsibility:** Automatic conversion between DTOs and Entities

**Tool:** MapStruct 1.6.3 (generates implementations at compile-time)

**Example:**
```java
@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDto toDto(Product entity);
    Product toEntity(ProductCreateDto dto);
    Product toEntity(ProductUpdateDto dto);
    List<ProductDto> toDtoList(List<Product> entities);
}
```

**Benefits:**
- Zero-runtime overhead (compile-time generation)
- Type-safe mapping
- Easy to maintain

---

### Database Schema

**Current Schema Name:** `inventory_management` (configurable in `application.properties`)

**Key Tables:**

```sql
-- Products table
CREATE TABLE products (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    sku VARCHAR(50) UNIQUE,
    price DECIMAL(10, 2),
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

-- Locations table
CREATE TABLE locations (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    address VARCHAR(255),
    city VARCHAR(100),
    state VARCHAR(50),
    zip_code VARCHAR(10),
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

-- Inventory table
CREATE TABLE inventory (
    id UUID PRIMARY KEY,
    product_id UUID REFERENCES products(id),
    location_id UUID REFERENCES locations(id),
    quantity INT,
    minimum_quantity INT,
    maximum_quantity INT,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);
```

---

## Frontend Architecture

### Flutter Project Structure

```
flutter/
├── lib/
│   ├── models/
│   │   ├── inventory_dto.dart
│   │   ├── location_dto.dart
│   │   ├── product_dto.dart
│   │   └── models.dart (exports all models)
│   ├── services/
│   │   ├── api_client.dart (HTTP client configuration)
│   │   ├── inventory_service.dart
│   │   ├── location_service.dart
│   │   ├── product_service.dart
│   │   └── services.dart (exports all services)
│   └── main.dart (app entry point)
├── pubspec.yaml (dependencies and metadata)
└── test/ (unit and widget tests)
```

### Service Architecture

#### API Client (`api_client.dart`)

Configures and provides HTTP client:

```dart
const String baseUrl = 'http://localhost:8080';

Future<T> _makeRequest<T>(
  String method,
  String endpoint,
  {required T Function(dynamic) decoder,
   Map<String, dynamic>? body,}
) async {
  // Handle HTTP request with error handling
  // Include retry logic for 429 (rate limit) responses
}
```

---

#### Service Classes

Each resource (Product, Location, Inventory) has a dedicated service:

**ProductService:**
```dart
class ProductService {
  Future<List<ProductDto>> getAllProducts() async { ... }
  Future<ProductDto?> getProductById(String id) async { ... }
  Future<ProductDto?> createProduct(ProductCreateDto dto) async { ... }
  Future<ProductDto?> updateProduct(String id, ProductUpdateDto dto) async { ... }
  Future<bool> deleteProduct(String id) async { ... }
}
```

**Benefits:**
- Single Responsibility: Each service handles one resource
- Reusability: Services can be used across widgets
- Testability: Services can be mocked for tests
- Maintainability: Changes isolated to service

---

## Data Flow

### Create Product Flow

#### Request Path:
```
Flutter Widget
    ↓ (user input)
ProductService.createProduct(dto)
    ↓ (HTTP POST /products)
ProductController.createProduct(dto)
    ↓ (validate, create)
ProductService.createProduct(dto)
    ↓ (business logic)
ProductRepository.save(entity)
    ↓ (SQL INSERT)
PostgreSQL Database
```

#### Response Path:
```
Database (returns generated ID)
    ↓
ProductRepository (returns saved entity)
    ↓
ProductService (maps to DTO)
    ↓
ProductMapper.toDto(entity)
    ↓
ProductController (wraps in response)
    ↓
HTTP 201 Created with ProductDto
    ↓
Flutter Widget (displays result)
```

### Error Handling Flow

```
API Request
    ↓ (error occurs)
Exception caught in service
    ↓
Log error (slf4j)
    ↓
Return appropriate HTTP status code
    ↓
Return error response body
    ↓
Flutter catches exception
    ↓
Display error to user
```

---

## Development Workflow

### Adding a New Feature

#### Step 1: Database Design
Define the database table and add to schema migration.

#### Step 2: Entity Class
Create JPA entity in `Entities/`:
```java
@Entity
@Table(name = "new_resource")
public class NewResource {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    // fields
}
```

#### Step 3: Create DTOs
Create three DTO classes in `DTOs/`:
- `NewResourceDto` (for responses)
- `NewResourceCreateDto` (for POST requests)
- `NewResourceUpdateDto` (for PUT requests)

#### Step 4: Create Repository
Create repository in `Repositories/`:
```java
public interface NewResourceRepository extends JpaRepository<NewResource, UUID> {
    // Custom query methods if needed
}
```

#### Step 5: Create Mapper
Create mapper in `Mappers/`:
```java
@Mapper(componentModel = "spring")
public interface NewResourceMapper {
    NewResourceDto toDto(NewResource entity);
    NewResource toEntity(NewResourceCreateDto dto);
    // ...
}
```

#### Step 6: Create Service
Create service in `DAL/`:
```java
@Service
public class NewResourceService {
    @Autowired
    private NewResourceRepository repository;
    @Autowired
    private NewResourceMapper mapper;
    
    public List<NewResourceDto> getAllResources() { ... }
    public Optional<NewResourceDto> getResourceById(UUID id) { ... }
    public NewResourceDto createResource(NewResourceCreateDto dto) { ... }
    // ...
}
```

#### Step 7: Create Controller
Create controller in `Controllers/`:
```java
@RestController
@RequestMapping("/resources")
public class NewResourceController {
    @Autowired
    private NewResourceService service;
    
    @GetMapping
    public ResponseEntity<List<NewResourceDto>> getAll() { ... }
    // ...
}
```

#### Step 8: Create Tests
Add tests in `src/test/java/`:
```java
@SpringBootTest
public class NewResourceControllerTest {
    @Autowired
    private MockMvc mockMvc;
    // test methods
}
```

#### Step 9: Create Flutter Models
Add model in `flutter/lib/models/`:
```dart
class NewResourceDto {
    final String id;
    final String name;
    // ...
    
    factory NewResourceDto.fromJson(Map<String, dynamic> json) { ... }
    Map<String, dynamic> toJson() { ... }
}
```

#### Step 10: Create Flutter Service
Add service in `flutter/lib/services/`:
```dart
class NewResourceService {
    Future<List<NewResourceDto>> getAll() async { ... }
    Future<NewResourceDto?> create(NewResourceCreateDto dto) async { ... }
    // ...
}
```

---

## Code Style and Conventions

### Java Conventions

**Naming:**
- Classes: `PascalCase` (e.g., `ProductController`, `ProductDto`)
- Methods: `camelCase` (e.g., `getAllProducts()`, `createProduct()`)
- Variables: `camelCase` (e.g., `productList`, `isActive`)
- Constants: `UPPER_SNAKE_CASE` (e.g., `DEFAULT_PAGE_SIZE`)

**Formatting:**
- Use 4 spaces for indentation
- Max line length: 120 characters
- Use meaningful variable names
- Add JavaDoc for public methods

**Annotations:**
- Use Spring annotations (`@Autowired`, `@Service`, `@Repository`)
- Use validation annotations (`@NotNull`, `@Valid`, `@Size`)
- Use JPA annotations (`@Entity`, `@Table`, `@Column`)

---

### Dart/Flutter Conventions

**Naming:**
- Classes: `PascalCase` (e.g., `ProductService`, `ProductDto`)
- Methods/functions: `camelCase` (e.g., `getAllProducts()`)
- Variables: `camelCase` (e.g., `productList`, `isLoading`)
- Constants: `camelCase` (e.g., `defaultPageSize`)

**Formatting:**
- Use 2 spaces for indentation
- Run `dart format` before committing
- Use meaningful variable names
- Add documentation comments for public APIs

**Best Practices:**
- Use `final` for variables that won't change
- Use null-safety (`?` for nullable types)
- Use async/await for asynchronous operations
- Handle exceptions appropriately

---

## Testing Strategy

### Backend Testing

#### Unit Tests
Test individual components in isolation:

```java
@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    
    @Mock
    private ProductRepository repository;
    
    @InjectMocks
    private ProductService service;
    
    @Test
    void testGetAllProducts() {
        // arrange
        List<Product> expected = List.of(new Product(...));
        when(repository.findAll()).thenReturn(expected);
        
        // act
        List<ProductDto> actual = service.getAllProducts();
        
        // assert
        assertEquals(1, actual.size());
    }
}
```

#### Integration Tests
Test Spring Boot context and database:

```java
@SpringBootTest
class ProductControllerIntegrationTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Test
    void testGetAllProducts() throws Exception {
        mockMvc.perform(get("/products"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].name").exists());
    }
}
```

#### Test Containers
Use Docker containers for database testing:

```java
@SpringBootTest
@Testcontainers
class PostgresIntegrationTest {
    
    @Container
    static PostgreSQLContainer<?> postgres = 
        new PostgreSQLContainer<>(DockerImageName.parse("postgres:15"));
    
    // tests using postgres container
}
```

### Frontend Testing

#### Widget Tests
Test UI components:

```dart
testWidgets('ProductList displays products', (WidgetTester tester) async {
    await tester.pumpWidget(const ProductList());
    
    expect(find.byType(ListTile), findsWidgets);
});
```

#### Service Tests
Mock API responses:

```dart
test('ProductService.getAll returns products', () async {
    final service = ProductService();
    // mock HTTP responses
    final products = await service.getAll();
    expect(products.length, equals(1));
});
```

---

## Best Practices Summary

1. **Controllers:** Keep thin, delegate to services
2. **Services:** Implement business logic, use transactions
3. **Repositories:** Use Spring Data JPA, minimal custom queries
4. **DTOs:** Separate API contracts from persistence
5. **Error Handling:** Use appropriate HTTP status codes, log errors
6. **Testing:** Aim for 80%+ code coverage
7. **Documentation:** Keep code comments and docs updated
8. **Version Control:** Make small, focused commits with clear messages
9. **Code Review:** Review PRs for style, logic, and tests
10. **Security:** Validate all inputs, plan for authentication/authorization

---

**Last Updated:** January 2026
