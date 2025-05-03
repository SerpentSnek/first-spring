// package firstspring.firstspringdemo.Tests.Services;


// import org.mockito.Mockito;  // For general Mockito methods
// import org.mockito.junit.jupiter.MockitoExtension; // For @ExtendWith
// import org.mockito.InjectMocks;
// import org.mockito.Mock;

// import java.util.ArrayList;
// import java.util.List;
// import java.util.Optional;
// import java.util.UUID;

// import firstspring.firstspringdemo.DAL.ProductService;
// import firstspring.firstspringdemo.DTOs.ProductCreateDto;
// import firstspring.firstspringdemo.DTOs.ProductDto;
// import firstspring.firstspringdemo.DTOs.ProductUpdateDto;
// import firstspring.firstspringdemo.Mappers.ProductMapper;
// import firstspring.firstspringdemo.Repositories.ProductRepository;
// // import entity classes
// import firstspring.firstspringdemo.Entities.Product;


// @ExtendWith(MockitoExtension.class)
// public class ProductServiceTests {

//     @Mock
//     private ProductRepository productRepository;

//     @Mock
//     private ProductMapper productMapper;

//     @InjectMocks
//     private ProductService productService;

//     @Test
//     void getAllProducts_ReturnsListOfProductDtos() {
//         // Arrange
//         List<Product> products = new ArrayList<>();
//         products.add(new Product("Product 1", "Description 1", "SKU1", "Category 1", "Manufacturer 1", null, null));
//         products.add(new Product("Product 2", "Description 2", "SKU2", "Category 2", "Manufacturer 2", null, null));

//         when(productRepository.findAll()).thenReturn(products);
//         when(productMapper.toDto(any(Product.class))).thenAnswer(i -> {
//             Product p = i.getArgument(0);
//             return new ProductDto(p.getProductId(), p.getName(), p.getDescription(), p.getSku(), p.getCategory(), p.getManufacturer());
//         });

//         // Act
//         List<ProductDto> productDtos = productService.getAllProducts();

//         // Assert
//         assertEquals(2, productDtos.size());
//         verify(productRepository, times(1)).findAll();
//         verify(productMapper, times(2)).toDto(any(Product.class));
//     }

//     @Test
//     void getProductById_ReturnsProductDto_WhenProductExists() {
//         // Arrange
//         UUID productId = UUID.randomUUID();
//         Product product = new Product("Product 1", "Description 1", "SKU1", "Category 1", "Manufacturer 1", null, null);

//         when(productRepository.findById(productId)).thenReturn(Optional.of(product));
//         when(productMapper.toDto(product)).thenReturn(new ProductDto(product.getProductId(), product.getName(), product.getDescription(), product.getSku(), product.getCategory(), product.getManufacturer()));

//         // Act
//         Optional<ProductDto> productDto = productService.getProductById(productId);

//         // Assert
//         assertNotNull(productDto.orElse(null));
//         assertEquals(productId, productDto.get().getProductId());
//         verify(productRepository, times(1)).findById(productId);
//         verify(productMapper, times(1)).toDto(product);
//     }

//     @Test
//     void createProduct_ReturnsCreatedProductDto() {
//         // Arrange
//         ProductCreateDto createProductDto = new ProductCreateDto("Product 1", "Description 1", 0.0, 0, UUID.randomUUID());
//         Product product = new Product("Product 1", "Description 1", "SKU1", "Category 1", "Manufacturer 1", null, null);

//         when(productMapper.createProductDtoToProduct(createProductDto)).thenReturn(product);
//         when(productRepository.save(product)).thenReturn(product);
//         when(productMapper.toDto(product)).thenReturn(new ProductDto(product.getProductId(), product.getName(), product.getDescription(), product.getSku(), product.getCategory(), product.getManufacturer()));

//         // Act
//         ProductDto productDto = productService.createProduct(createProductDto);

//         // Assert
//         assertNotNull(productDto);
//         verify(productRepository, times(1)).save(product);
//         verify(productMapper, times(1)).toDto(product);
//     }

//     @Test
//     void updateProduct_ReturnsUpdatedProductDto_WhenProductExists() {
//         // Arrange
//         UUID productId = UUID.randomUUID();
//         ProductUpdateDto updateProductDto = new ProductUpdateDto("Updated Product", "Updated Description", 0.0, 0, "locationid 1");
//         Product existingProduct = new Product("Product 1", "Description 1", "SKU1", "Category 1", "Manufacturer 1", null, null);
//         Product updatedProduct = new Product("Updated Product", "Updated Description", "SKU1", "Category 1", "Manufacturer 1", null, null);

//         when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
//         doAnswer(invocation -> {
//             Product p = invocation.getArgument(1);
//             p.setName(updateProductDto.getName());
//             p.setDescription(updateProductDto.getDescription());
//             return null;
//         }).when(productMapper).updateProductFromDto(eq(updateProductDto), eq(existingProduct));
//         when(productRepository.save(existingProduct)).thenReturn(updatedProduct);
//         when(productMapper.toDto(updatedProduct)).thenReturn(new ProductDto(productId, "Updated Product", "Updated Description", "SKU1", "Category 1", "Manufacturer 1"));

//         // Act
//         ProductDto productDto = productService.updateProduct(productId, updateProductDto);

//         // Assert
//         assertNotNull(productDto);
//         assertEquals("Updated Product", productDto.getName());
//         verify(productRepository, times(1)).findById(productId);
//         verify(productRepository, times(1)).save(existingProduct);
//         verify(productMapper, times(1)).toDto(existingProduct);
//     }

//     @Test
//     void deleteProduct_DeletesProduct_WhenProductExists() {
//         // Arrange
//         UUID productId = UUID.randomUUID();
//         when(productRepository.existsById(productId)).thenReturn(true);

//         // Act
//         productService.deleteProduct(productId);

//         // Assert
//         verify(productRepository, times(1)).deleteById(productId);
//     }
// }
