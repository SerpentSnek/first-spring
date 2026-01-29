import 'package:flutter_test/flutter_test.dart';
import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:mocktail/mocktail.dart';
import 'package:firstspringdemo/widgets/inventory_list.dart';
import 'package:firstspringdemo/providers/inventory_provider.dart';
import 'package:firstspringdemo/services/inventory_service.dart';
import 'package:firstspringdemo/models/models.dart';

class MockInventoryService extends Mock implements InventoryService {}

void main() {
  testWidgets('shows list of inventory items', (tester) async {
    final mockService = MockInventoryService();
    final items = [InventoryDto(inventoryId: '1', productId: 'A', quantity: 1)];
    when(() => mockService.getAllInventory()).thenAnswer((_) async => items);

    await tester.pumpWidget(
      ProviderScope(
        overrides: [inventoryServiceProvider.overrideWithValue(mockService)],
        child: const MaterialApp(home: Scaffold(body: InventoryList())),
      ),
    );

    await tester.pump();
    await tester.pump(const Duration(milliseconds: 100));

    expect(find.text('A'), findsOneWidget);
  });
}
