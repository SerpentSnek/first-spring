import 'package:flutter_test/flutter_test.dart';
import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:mocktail/mocktail.dart';
import 'package:firstspringdemo/widgets/product_list.dart';
import 'package:firstspringdemo/providers/product_provider.dart';
import 'package:firstspringdemo/services/product_service.dart';
import 'package:firstspringdemo/models/models.dart';

class MockProductService extends Mock implements ProductService {}

void main() {
  testWidgets('shows list of products', (tester) async {
    final mockService = MockProductService();
    final products = [ProductDto(productId: '1', name: 'A', description: 'd')];
    when(() => mockService.getAllProducts()).thenAnswer((_) async => products);

    await tester.pumpWidget(
      ProviderScope(
        overrides: [productServiceProvider.overrideWithValue(mockService)],
        child: const MaterialApp(home: Scaffold(body: ProductList())),
      ),
    );

    // allow async provider build
    await tester.pump(); // first frame
    await tester.pump(const Duration(milliseconds: 100)); // wait for async

    expect(find.text('A'), findsOneWidget);
  });
}
