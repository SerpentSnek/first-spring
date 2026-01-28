import 'dart:convert';
// import 'package:http/http.dart' as http;
import '../models/models.dart';
import 'api_client.dart';

class InventoryService {
  final ApiClient _apiClient = ApiClient();
  static const String _endpoint = '/inventory';

  /// Get all inventory records
  Future<List<InventoryDto>> getAllInventory() async {
    try {
      final response = await _apiClient.get(_endpoint);

      if (response.statusCode == 200) {
        final List<dynamic> jsonList = jsonDecode(response.body);
        return jsonList
            .map((json) => InventoryDto.fromJson(json as Map<String, dynamic>))
            .toList();
      } else {
        throw Exception('Failed to load inventory: ${response.statusCode}');
      }
    } catch (e) {
      rethrow;
    }
  }

  /// Get inventory record by ID
  Future<InventoryDto?> getInventoryById(String id) async {
    try {
      final response = await _apiClient.get('$_endpoint/$id');

      if (response.statusCode == 200) {
        return InventoryDto.fromJson(jsonDecode(response.body));
      } else if (response.statusCode == 404) {
        return null;
      } else {
        throw Exception('Failed to load inventory: ${response.statusCode}');
      }
    } catch (e) {
      rethrow;
    }
  }

  /// Create a new inventory record
  Future<InventoryDto> createInventory(InventoryCreateDto createDto) async {
    try {
      final response = await _apiClient.post(
        _endpoint,
        jsonEncode(createDto.toJson()),
      );

      if (response.statusCode == 201) {
        return InventoryDto.fromJson(jsonDecode(response.body));
      } else {
        throw Exception('Failed to create inventory: ${response.statusCode}');
      }
    } catch (e) {
      rethrow;
    }
  }

  /// Update an existing inventory record (full replacement)
  Future<InventoryDto> updateInventory(
    String id,
    InventoryUpdateDto updateDto,
  ) async {
    try {
      final response = await _apiClient.put(
        '$_endpoint/$id',
        jsonEncode(updateDto.toJson()),
      );

      if (response.statusCode == 200) {
        return InventoryDto.fromJson(jsonDecode(response.body));
      } else {
        throw Exception('Failed to update inventory: ${response.statusCode}');
      }
    } catch (e) {
      rethrow;
    }
  }

  /// Partially update an inventory record
  Future<InventoryDto> partialUpdateInventory(
    String id,
    InventoryUpdateDto updateDto,
  ) async {
    try {
      final response = await _apiClient.patch(
        '$_endpoint/$id',
        jsonEncode(updateDto.toJson()),
      );

      if (response.statusCode == 200) {
        return InventoryDto.fromJson(jsonDecode(response.body));
      } else {
        throw Exception(
          'Failed to partially update inventory: ${response.statusCode}',
        );
      }
    } catch (e) {
      rethrow;
    }
  }

  /// Delete an inventory record
  Future<void> deleteInventory(String id) async {
    try {
      final response = await _apiClient.delete('$_endpoint/$id');

      if (response.statusCode != 204 && response.statusCode != 200) {
        throw Exception('Failed to delete inventory: ${response.statusCode}');
      }
    } catch (e) {
      rethrow;
    }
  }
}
