import 'dart:convert';
// import 'package:http/http.dart' as http;
import '../models/models.dart';
import 'api_client.dart';

class ProductService {
  final ApiClient _apiClient = ApiClient();
  static const String _endpoint = '/products';

  /// Get all products
  Future<List<ProductDto>> getAllProducts() async {
    try {
      final response = await _apiClient.get(_endpoint);

      if (response.statusCode == 200) {
        final List<dynamic> jsonList = jsonDecode(response.body);
        return jsonList
            .map((json) => ProductDto.fromJson(json as Map<String, dynamic>))
            .toList();
      } else {
        throw Exception('Failed to load products: ${response.statusCode}');
      }
    } catch (e) {
      rethrow;
    }
  }

  /// Get product by ID
  Future<ProductDto?> getProductById(String id) async {
    try {
      final response = await _apiClient.get('$_endpoint/$id');

      if (response.statusCode == 200) {
        return ProductDto.fromJson(jsonDecode(response.body));
      } else if (response.statusCode == 404) {
        return null;
      } else {
        throw Exception('Failed to load product: ${response.statusCode}');
      }
    } catch (e) {
      rethrow;
    }
  }

  /// Create a new product
  Future<ProductDto> createProduct(ProductCreateDto createDto) async {
    try {
      final response = await _apiClient.post(
        _endpoint,
        jsonEncode(createDto.toJson()),
      );

      if (response.statusCode == 201) {
        return ProductDto.fromJson(jsonDecode(response.body));
      } else {
        throw Exception('Failed to create product: ${response.statusCode}');
      }
    } catch (e) {
      rethrow;
    }
  }

  /// Update an existing product
  Future<ProductDto> updateProduct(
    String id,
    ProductUpdateDto updateDto,
  ) async {
    try {
      final response = await _apiClient.put(
        '$_endpoint/$id',
        jsonEncode(updateDto.toJson()),
      );

      if (response.statusCode == 200) {
        return ProductDto.fromJson(jsonDecode(response.body));
      } else {
        throw Exception('Failed to update product: ${response.statusCode}');
      }
    } catch (e) {
      rethrow;
    }
  }

  /// Delete a product
  Future<void> deleteProduct(String id) async {
    try {
      final response = await _apiClient.delete('$_endpoint/$id');

      if (response.statusCode != 204 && response.statusCode != 200) {
        throw Exception('Failed to delete product: ${response.statusCode}');
      }
    } catch (e) {
      rethrow;
    }
  }
}
