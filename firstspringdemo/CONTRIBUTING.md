# Contributing Guidelines

## Welcome! 👋

Thank you for your interest in contributing to FirstSpringDemo. This guide will help you understand our development process and how to contribute effectively.

---

## Table of Contents

1. [Code of Conduct](#code-of-conduct)
2. [Getting Started](#getting-started)
3. [Development Setup](#development-setup)
4. [Making Changes](#making-changes)
5. [Testing](#testing)
6. [Submitting Changes](#submitting-changes)
7. [Code Review Process](#code-review-process)
8. [Coding Standards](#coding-standards)

---

## Code of Conduct

We are committed to providing a welcoming and inclusive environment for all contributors. Please:

- Treat all contributors with respect
- Be open to feedback and constructive criticism
- Focus on what's best for the project
- Report any violations to project maintainers

---

## Getting Started

### Prerequisites

Ensure you have all system requirements installed:

- **Backend:** Java 21 JDK, Maven 3.6+
- **Database:** PostgreSQL 12+
- **Frontend:** Flutter 3.10+

See [SETUP_GUIDE.md](SETUP_GUIDE.md) for detailed installation instructions.

### Fork and Clone

1. Fork the repository on GitHub
2. Clone your fork:
   ```bash
   git clone https://github.com/YOUR_USERNAME/first-spring.git
   cd first-spring
   ```
3. Add upstream remote:
   ```bash
   git remote add upstream https://github.com/ORIGINAL_OWNER/first-spring.git
   ```

---

## Development Setup

### 1. Create a Feature Branch

```bash
git checkout -b feature/your-feature-name
```

**Branch naming conventions:**
- `feature/short-description` - New features
- `bugfix/short-description` - Bug fixes
- `docs/short-description` - Documentation updates
- `refactor/short-description` - Code refactoring
- `test/short-description` - Test additions

### 2. Set Up Development Environment

```bash
cd firstspringdemo

# Backend setup
./mvnw clean install

# Frontend setup
cd flutter
flutter pub get
cd ..
```

### 3. Start Backend and Database

See [SETUP_GUIDE.md](SETUP_GUIDE.md) for detailed instructions.

---

## Making Changes

### 1. Code Style

Follow the existing code style in the project:

**Java:**
- Use 4 spaces for indentation
- Follow PascalCase for class names
- Follow camelCase for method names
- Add JavaDoc comments for public methods
- Max line length: 120 characters

**Dart/Flutter:**
- Use 2 spaces for indentation
- Run `dart format` before committing
- Follow Dart naming conventions
- Add documentation comments

### 2. Commit Messages

Write clear, descriptive commit messages:

```
feat: Add product search functionality

- Implement search by product name
- Add search parameter validation
- Update ProductService with new method
- Add unit tests for search feature

Closes #123
```

**Format:**
- Type: `feat`, `fix`, `docs`, `test`, `refactor`, `chore`
- Subject: Imperative, present tense ("add" not "adds")
- Body: Detailed explanation of changes
- Footer: Reference issues with `Closes #123`

### 3. File Structure

When adding new files:

**Backend:**
```
Controllers/         - REST endpoints
DAL/                 - Services with business logic
DTOs/                - Data transfer objects
Entities/            - JPA entity classes
Mappers/             - MapStruct mappers
Repositories/        - JPA repositories
```

**Frontend:**
```
flutter/lib/
  ├── models/        - Dart model/DTO classes
  ├── services/      - API service classes
  ├── widgets/       - Widget components (if added)
  └── screens/       - Screen/page components (if added)
```

---

## Testing

### Backend Tests

**Unit Tests:**
```bash
./mvnw test
```

**Integration Tests:**
```bash
./mvnw test -Dgroups=integration
```

**Coverage:**
```bash
./mvnw jacoco:report
# Results in target/site/jacoco/index.html
```

### Frontend Tests

**Run all tests:**
```bash
cd flutter
flutter test
```

**Generate coverage:**
```bash
flutter test --coverage
```

### Testing Requirements

- All new features must have tests
- Aim for at least 80% code coverage
- Tests should be clear and descriptive
- Use meaningful test names: `test_<scenario>_<expected_result>()`

### Example Test

**Java (Unit Test):**
```java
@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    
    @Mock
    private ProductRepository repository;
    
    @InjectMocks
    private ProductService service;
    
    @Test
    void test_createProduct_createsValidProduct() {
        // Arrange
        ProductCreateDto dto = new ProductCreateDto("Test", "Desc", "SKU");
        Product entity = new Product(...);
        when(repository.save(any())).thenReturn(entity);
        
        // Act
        ProductDto result = service.createProduct(dto);
        
        // Assert
        assertNotNull(result.getId());
        assertEquals("Test", result.getName());
        verify(repository).save(any());
    }
}
```

**Dart (Widget Test):**
```dart
void main() {
  testWidgets('ProductList displays products correctly', 
    (WidgetTester tester) async {
      await tester.pumpWidget(const MaterialApp(
        home: ProductList(),
      ));
      
      expect(find.byType(ListView), findsOneWidget);
      expect(find.byType(ListTile), findsWidgets);
    });
}
```

---

## Submitting Changes

### 1. Prepare Your Changes

```bash
# Make sure you have latest upstream changes
git fetch upstream
git rebase upstream/main

# Run tests
./mvnw clean test
cd flutter && flutter test

# Check code style
./mvnw spotless:check  # if configured
dart format --set-exit-if-changed lib/
```

### 2. Push to Your Fork

```bash
git push origin feature/your-feature-name
```

### 3. Create a Pull Request

1. Go to the original repository
2. Click "New Pull Request"
3. Select your fork and branch
4. Fill in the PR template:

```markdown
## Description
Brief description of the changes

## Type of Change
- [ ] Bug fix
- [ ] New feature
- [ ] Breaking change
- [ ] Documentation update

## Related Issues
Closes #123

## Testing
Describe how you tested the changes

## Checklist
- [ ] Tests pass locally
- [ ] Code follows style guidelines
- [ ] Documentation updated
- [ ] Commit messages are clear
- [ ] No unnecessary dependencies added
```

---

## Code Review Process

### Review Criteria

All PRs will be reviewed for:

1. **Code Quality**
   - Follows project style conventions
   - No code smells or obvious bugs
   - Proper error handling
   - Appropriate logging

2. **Testing**
   - Adequate test coverage
   - Tests pass locally and in CI
   - Edge cases considered

3. **Documentation**
   - Code is self-documenting where possible
   - Comments explain "why" not "what"
   - Public APIs have documentation
   - README/docs updated if needed

4. **Performance**
   - No obvious performance regressions
   - Efficient database queries
   - Proper use of caching/indexing

5. **Security**
   - Input validation
   - No hardcoded secrets
   - Proper error messages (no info leakage)

### Addressing Feedback

- Respond to all comments
- Make requested changes
- Commit changes with clear messages
- Re-request review after changes

### Approval and Merge

- Requires at least 1 approval from maintainers
- All conversations resolved
- All tests passing
- Branch up-to-date with main

---

## Coding Standards

### Java

**Style Guide:**
- Use 4 spaces for indentation (not tabs)
- Max line length: 120 characters
- Use `var` for obvious types
- Prefer enhanced for-loops

**Naming:**
- Classes: `ProductController`, `ProductService`
- Methods: `getAllProducts()`, `createProduct()`
- Constants: `DEFAULT_PAGE_SIZE`, `MAX_RETRIES`
- Private fields: `_productRepository` (optional)

**Annotations:**
```java
@Service
@Transactional
public class ProductService {
    
    @Autowired
    private ProductRepository repository;
    
    @Override
    public List<ProductDto> getAllProducts() {
        // implementation
    }
}
```

**Comments:**
```java
/**
 * Retrieves all products from the database.
 * 
 * @return a list of all products
 * @throws DataAccessException if database error occurs
 */
public List<ProductDto> getAllProducts() {
    // implementation
}
```

### Dart/Flutter

**Style Guide:**
- Use 2 spaces for indentation
- Run `dart format` before committing
- Prefer `const` constructors
- Use meaningful variable names

**Naming:**
- Classes: `ProductService`, `ProductDto`
- Methods: `getAllProducts()`, `createProduct()`
- Variables: `productList`, `isLoading`
- Enums: `ProductStatus`, `LocationType`

**Code Example:**
```dart
class ProductService {
  final http.Client _httpClient;
  
  const ProductService({required http.Client httpClient})
    : _httpClient = httpClient;
  
  /// Retrieves all products from the API.
  ///
  /// Throws [HttpException] if the request fails.
  Future<List<ProductDto>> getAllProducts() async {
    try {
      final response = await _httpClient.get(Uri.parse('$baseUrl/products'));
      return _decodeProducts(response);
    } catch (e) {
      throw HttpException('Failed to fetch products: $e');
    }
  }
}
```

### Database

**Schema:**
- Use snake_case for table and column names
- Use UUID for primary keys
- Include created_at and updated_at timestamps
- Add meaningful indexes for query performance
- Add foreign key constraints

**Example:**
```sql
CREATE TABLE products (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255) NOT NULL,
    description TEXT,
    sku VARCHAR(50) UNIQUE NOT NULL,
    price DECIMAL(10, 2) NOT NULL CHECK (price >= 0),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_products_sku ON products(sku);
```

---

## Quick Reference

### Common Tasks

**Run backend tests:**
```bash
./mvnw test
```

**Run frontend tests:**
```bash
cd flutter && flutter test
```

**Format code:**
```bash
# Java
./mvnw spotless:apply

# Dart
cd flutter && dart format .
```

**Start development servers:**
```bash
# Terminal 1: Backend
./mvnw spring-boot:run

# Terminal 2: Frontend
cd flutter && flutter run
```

**Clean and rebuild:**
```bash
# Backend
./mvnw clean install

# Frontend
cd flutter && flutter clean && flutter pub get
```

---

## Getting Help

- Check [DOCUMENTATION.md](DOCUMENTATION.md) for project overview
- Check [ARCHITECTURE.md](ARCHITECTURE.md) for architecture details
- Read [SETUP_GUIDE.md](SETUP_GUIDE.md) for environment setup
- Open an issue on GitHub
- Contact project maintainers

---

## Thank You! 🎉

We appreciate your contributions and effort to improve FirstSpringDemo. Your code and ideas help make this project better for everyone!

---

**Last Updated:** January 2026
