import 'package:flutter/material.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:firstspringdemo/main.dart';
import 'package:firstspringdemo/services/demo_services.dart';
import 'package:firstspringdemo/providers/product_provider.dart';
import 'package:firstspringdemo/providers/inventory_provider.dart';

void main() {
  testWidgets('inventory add/edit/delete flow', (tester) async {
    await tester.pumpWidget(
      ProviderScope(
        overrides: [
          productServiceProvider.overrideWithValue(DemoProductService()),
          inventoryServiceProvider.overrideWithValue(DemoInventoryService()),
        ],
        child: const MaterialApp(home: HomeScreen()),
      ),
    );

    await tester.pumpAndSettle();

    // Switch to Inventory tab
    await tester.tap(find.text('Inventory'));
    await tester.pumpAndSettle();

    final tileFinder = find.byType(ListTile);
    final initialCount = tester.widgetList(tileFinder).length;
    expect(initialCount, greaterThanOrEqualTo(1));

    // Add via FAB (when inventory tab active)
    await tester.tap(find.byIcon(Icons.add));
    await tester.pumpAndSettle();

    final afterAddCount = tester.widgetList(tileFinder).length;
    expect(afterAddCount, initialCount + 1);

    // Edit first inventory (id '1')
    final inventoryTile = find.byKey(const Key('inventory_tile_1'));
    expect(inventoryTile, findsOneWidget);
    await tester.tap(inventoryTile);
    await tester.pumpAndSettle();

    await tester.enterText(find.byKey(const Key('edit_qty_field')), '99');
    await tester.tap(find.text('Save'));
    await tester.pumpAndSettle();

    expect(find.textContaining('qty: 99'), findsOneWidget);

    // Delete
    await tester.longPress(inventoryTile);
    await tester.pumpAndSettle();
    await tester.tap(find.text('Delete'));
    await tester.pumpAndSettle();

    expect(find.byKey(const Key('inventory_tile_1')), findsNothing);
  });
}
