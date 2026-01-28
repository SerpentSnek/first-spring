import 'package:http/http.dart' as http;

class ApiClient {
  static const String baseUrl =
      'http://localhost:8080'; // Change for production

  static final ApiClient _instance = ApiClient._internal();

  factory ApiClient() {
    return _instance;
  }

  ApiClient._internal();

  Future<http.Response> get(String endpoint) {
    return http.get(Uri.parse('$baseUrl$endpoint'), headers: _getHeaders());
  }

  Future<http.Response> post(String endpoint, String body) {
    return http.post(
      Uri.parse('$baseUrl$endpoint'),
      headers: _getHeaders(),
      body: body,
    );
  }

  Future<http.Response> put(String endpoint, String body) {
    return http.put(
      Uri.parse('$baseUrl$endpoint'),
      headers: _getHeaders(),
      body: body,
    );
  }

  Future<http.Response> patch(String endpoint, String body) {
    return http.patch(
      Uri.parse('$baseUrl$endpoint'),
      headers: _getHeaders(),
      body: body,
    );
  }

  Future<http.Response> delete(String endpoint) {
    return http.delete(Uri.parse('$baseUrl$endpoint'), headers: _getHeaders());
  }

  Map<String, String> _getHeaders() {
    return {'Content-Type': 'application/json', 'Accept': 'application/json'};
  }
}
