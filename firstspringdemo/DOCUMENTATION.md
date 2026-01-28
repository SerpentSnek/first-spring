# FirstSpringDemo - Inventory Management System

## Project Overview

**FirstSpringDemo** is a full-stack inventory management application built with Spring Boot backend and a Flutter mobile frontend. The system provides APIs for managing products, locations, and inventory levels across a PostgreSQL database.

### Tech Stack

**Backend:**
- Spring Boot 3.5.0-RC1
- Java 21
- PostgreSQL
- MapStruct (for DTO mapping)
- GraphQL (with Spring Boot GraphQL starter)
- Actuator (for monitoring)

**Frontend:**
- Flutter 3.10+
- Dart
- HTTP package for API communication

## Project Structure

```
firstspringdemo/
├── src/
│   ├── main/
│   │   ├── java/firstspring/firstspringdemo/
│   │   │   ├── FirstspringdemoApplication.java       # Spring Boot entry point
│   │   │   ├── Controllers/                          # REST API endpoints
│   │   │   │   ├── ProductController.java
│   │   │   │   ├── LocationController.java
│   │   │   │   └── InventoryController.java
│   │   │   ├── DAL/                                  # Data Access Layer (Services)
│   │   │   │   ├── ProductService.java
│   │   │   │   ├── LocationService.java
│   │   │   │   └── InventoryService.java
│   │   │   ├── DTOs/                                 # Data Transfer Objects
│   │   │   ├── Entities/                             # JPA entities
│   │   │   ├── Mappers/                              # MapStruct mappers
│   │   │   └── Repositories/                         # Spring Data JPA repositories
│   │   └── resources/
│   │       ├── application.properties
│   │       └── graphql/
│   └── test/
│       └── java/firstspring/firstspringdemo/         # Unit and integration tests
├── flutter/
│   ├── lib/
│   │   ├── models/                                   # Dart model classes
│   │   └── services/                                 # API service classes
│   └── pubspec.yaml
├── pom.xml                                           # Maven configuration
└── mvnw / mvnw.cmd                                   # Maven wrapper
```

## Core Features

### Product Management
- Create, read, update, and delete products
- Track product details and metadata

### Location Management  
- Manage inventory locations/warehouses
- Organize inventory by location

### Inventory Management
- Track inventory levels across products and locations
- Update inventory quantities
- Monitor inventory status

## Getting Started

### Prerequisites

- Java 21 JDK
- Maven 3.6+
- PostgreSQL 12+
- Flutter 3.10+ (for mobile development)

### Backend Setup

1. **Configure Database:**
   - Create PostgreSQL database: `firstspringdemodb`
   - Update connection details in [application.properties](src/main/resources/application.properties)
   - Configure username/password as needed

2. **Build and Run:**
   ```bash
   # Using Maven wrapper
   ./mvnw clean install
   ./mvnw spring-boot:run
   ```
   
   Or with Maven:
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

3. **Access the Application:**
   - REST API: `http://localhost:8080`
   - Health Check: `http://localhost:8080/actuator/health`
   - GraphQL: `http://localhost:8080/graphql`

### Flutter Frontend Setup

1. **Install Dependencies:**
   ```bash
   cd flutter
   flutter pub get
   ```

2. **Run on Device/Emulator:**
   ```bash
   flutter run
   ```

3. **Update API Endpoint:**
   - Edit [lib/services/api_client.dart](flutter/lib/services/api_client.dart)
   - Set the backend URL (default: `http://localhost:8080`)

## API Documentation

### Product Endpoints

**GET `/products`**
- Get all products
- Response: List of ProductDto

**GET `/products/{id}`**
- Get a specific product by ID
- Parameters: `id` (UUID)
- Response: ProductDto or 404

**POST `/products`**
- Create a new product
- Body: ProductCreateDto
- Response: ProductDto (HTTP 201)

**PUT `/products/{id}`**
- Update an existing product
- Parameters: `id` (UUID)
- Body: ProductUpdateDto
- Response: ProductDto

**DELETE `/products/{id}`**
- Delete a product
- Parameters: `id` (UUID)
- Response: HTTP 204

### Location Endpoints

**GET `/locations`**
- Get all locations

**GET `/locations/{id}`**
- Get a specific location by ID

**POST `/locations`**
- Create a new location

**PUT `/locations/{id}`**
- Update an existing location

**DELETE `/locations/{id}`**
- Delete a location

### Inventory Endpoints

**GET `/inventory`**
- Get all inventory records

**GET `/inventory/{id}`**
- Get a specific inventory record by ID

**POST `/inventory`**
- Create a new inventory record

**PUT `/inventory/{id}`**
- Update an existing inventory record

**DELETE `/inventory/{id}`**
- Delete an inventory record

## Data Models

### ProductDto / ProductCreateDto / ProductUpdateDto
Represents a product in the system with details like name, description, and pricing.

### LocationDto / LocationCreateDto / LocationUpdateDto
Represents a warehouse or storage location for inventory.

### InventoryDto / InventoryCreateDto / InventoryUpdateDto
Represents inventory levels for a product at a specific location.

## Testing

Run tests using Maven:

```bash
./mvnw test
```

Test files are located in `src/test/java/` and include:
- Unit tests for controllers
- Integration tests for services
- Test configuration with Testcontainers

## Development Guidelines

### Code Organization
- **Controllers**: Handle HTTP requests and route to services
- **Services (DAL)**: Business logic and database operations
- **DTOs**: Data transfer objects for API contracts
- **Entities**: JPA entity classes mapping to database tables
- **Mappers**: MapStruct configuration for DTO-Entity conversion
- **Repositories**: Spring Data JPA repository interfaces

### Adding a New Feature
1. Create Entity in `Entities/`
2. Create Repository extending JpaRepository
3. Create DTOs in `DTOs/`
4. Create Mapper in `Mappers/`
5. Create Service in `DAL/`
6. Create Controller in `Controllers/`
7. Add tests in `src/test/`

### MapStruct Configuration
The project uses MapStruct 1.6.3 for automatic DTO-Entity mapping. Mappers are auto-generated at compile time.

## Configuration

### Application Properties
Key configurations in [application.properties](src/main/resources/application.properties):
- Spring application name
- PostgreSQL connection details
- Database schema name: `inventory_management`

### GraphQL
GraphQL schema files are stored in `src/main/resources/graphql/`. Configure GraphQL endpoints and schemas as needed.

## Monitoring and Actuator

The application includes Spring Boot Actuator for monitoring:
- Health endpoint: `http://localhost:8080/actuator/health`
- Other actuator endpoints available at `http://localhost:8080/actuator/`

## Troubleshooting

### Database Connection Issues
- Verify PostgreSQL is running
- Check credentials in `application.properties`
- Ensure database `firstspringdemodb` exists
- Verify network connectivity to database host

### Build Failures
- Clear cache: `./mvnw clean`
- Check Java version: `java -version` (should be 21)
- Verify Maven wrapper: `./mvnw -v`

### Flutter Connection Issues
- Ensure backend is running on configured URL
- Check network connectivity (especially on emulator)
- Verify API endpoint in `api_client.dart`

## Next Steps

- Add Spring Security for authentication
- Implement comprehensive API documentation (Swagger/OpenAPI)
- Add comprehensive error handling and logging
- Expand GraphQL schema coverage
- Add database migration scripts
- Deploy to production infrastructure

## Contributing

When making changes:
1. Follow existing code style conventions
2. Add tests for new features
3. Update this documentation if adding new endpoints or features
4. Ensure all tests pass before submitting changes

## License

See LICENSE file for details.

---

**Last Updated:** January 2026
**Spring Boot Version:** 3.5.0-RC1
**Java Version:** 21
