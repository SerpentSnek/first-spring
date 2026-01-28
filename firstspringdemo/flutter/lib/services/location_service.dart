import 'dart:convert';
// import 'package:http/http.dart' as http;
import '../models/models.dart';
import 'api_client.dart';

class LocationService {
  final ApiClient _apiClient = ApiClient();
  static const String _endpoint = '/locations';

  /// Get all locations
  Future<List<LocationDto>> getAllLocations() async {
    try {
      final response = await _apiClient.get(_endpoint);

      if (response.statusCode == 200) {
        final List<dynamic> jsonList = jsonDecode(response.body);
        return jsonList
            .map((json) => LocationDto.fromJson(json as Map<String, dynamic>))
            .toList();
      } else {
        throw Exception('Failed to load locations: ${response.statusCode}');
      }
    } catch (e) {
      rethrow;
    }
  }

  /// Get location by ID
  Future<LocationDto?> getLocationById(String id) async {
    try {
      final response = await _apiClient.get('$_endpoint/$id');

      if (response.statusCode == 200) {
        return LocationDto.fromJson(jsonDecode(response.body));
      } else if (response.statusCode == 404) {
        return null;
      } else {
        throw Exception('Failed to load location: ${response.statusCode}');
      }
    } catch (e) {
      rethrow;
    }
  }

  /// Create a new location
  Future<LocationDto> createLocation(LocationCreateDto createDto) async {
    try {
      final response = await _apiClient.post(
        _endpoint,
        jsonEncode(createDto.toJson()),
      );

      if (response.statusCode == 201) {
        return LocationDto.fromJson(jsonDecode(response.body));
      } else {
        throw Exception('Failed to create location: ${response.statusCode}');
      }
    } catch (e) {
      rethrow;
    }
  }

  /// Update an existing location
  Future<LocationDto> updateLocation(
    String id,
    LocationUpdateDto updateDto,
  ) async {
    try {
      final response = await _apiClient.put(
        '$_endpoint/$id',
        jsonEncode(updateDto.toJson()),
      );

      if (response.statusCode == 200) {
        return LocationDto.fromJson(jsonDecode(response.body));
      } else {
        throw Exception('Failed to update location: ${response.statusCode}');
      }
    } catch (e) {
      rethrow;
    }
  }

  /// Delete a location
  Future<void> deleteLocation(String id) async {
    try {
      final response = await _apiClient.delete('$_endpoint/$id');

      if (response.statusCode != 204 && response.statusCode != 200) {
        throw Exception('Failed to delete location: ${response.statusCode}');
      }
    } catch (e) {
      rethrow;
    }
  }
}
