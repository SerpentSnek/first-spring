package firstspring.firstspringdemo.DAL;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import firstspring.firstspringdemo.DTOs.ProductCreateDto;
import firstspring.firstspringdemo.DTOs.ProductDto;
import firstspring.firstspringdemo.DTOs.ProductUpdateDto;
import firstspring.firstspringdemo.Entities.Product;
import firstspring.firstspringdemo.Mappers.ProductMapper;
import firstspring.firstspringdemo.Repositories.ProductRepository;

@Service
public class ProductService {
        @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<ProductDto> getProductById(UUID id) {
        return productRepository.findById(id)
                .map(productMapper::toDto);
    }

    public ProductDto createProduct(ProductCreateDto createProductDto) {
        Product product = productMapper.createProductDtoToProduct(createProductDto);
        Product savedProduct = productRepository.save(product);
        return productMapper.toDto(savedProduct);
    }

    public ProductDto updateProduct(UUID id, ProductUpdateDto updateProductDto) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            productMapper.updateProductFromDto(updateProductDto, product);
            Product updatedProduct = productRepository.save(product);
            return productMapper.toDto(updatedProduct);
        }
        return null;
    }

    public void deleteProduct(UUID id) {
        productRepository.deleteById(id);
    }
}
