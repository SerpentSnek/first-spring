import 'package:flutter/material.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:firstspringdemo/main.dart';
import 'package:firstspringdemo/services/demo_services.dart';
import 'package:firstspringdemo/providers/product_provider.dart';
import 'package:firstspringdemo/providers/inventory_provider.dart';

void main() {
  testWidgets('product add/edit/delete flow', (tester) async {
    await tester.pumpWidget(
      ProviderScope(
        overrides: [
          productServiceProvider.overrideWithValue(DemoProductService()),
          inventoryServiceProvider.overrideWithValue(DemoInventoryService()),
        ],
        child: const MaterialApp(home: HomeScreen()),
      ),
    );

    // wait for initial async loads
    await tester.pumpAndSettle();

    final tileFinder = find.byType(ListTile);
    final initialCount = tester.widgetList(tileFinder).length;
    expect(initialCount, greaterThanOrEqualTo(1));

    // Add via FAB
    await tester.tap(find.byIcon(Icons.add));
    await tester.pumpAndSettle();

    final afterAddCount = tester.widgetList(tileFinder).length;
    expect(afterAddCount, initialCount + 1);

    // Edit first product (id '1')
    final productTile = find.byKey(const Key('product_tile_1'));
    expect(productTile, findsOneWidget);
    await tester.tap(productTile);
    await tester.pumpAndSettle();

    // Enter new name
    await tester.enterText(find.byKey(const Key('edit_name_field')), 'WidgetX');
    await tester.tap(find.text('Save'));
    await tester.pumpAndSettle();

    expect(find.text('WidgetX'), findsOneWidget);

    // Delete product
    await tester.longPress(productTile);
    await tester.pumpAndSettle();
    await tester.tap(find.text('Delete'));
    await tester.pumpAndSettle();

    expect(find.text('WidgetX'), findsNothing);
  });
}
