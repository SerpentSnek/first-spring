import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:firstspringdemo/providers/product_provider.dart';
import 'package:firstspringdemo/providers/inventory_provider.dart';
import 'package:firstspringdemo/widgets/product_list.dart';
import 'package:firstspringdemo/widgets/inventory_list.dart';
import 'package:firstspringdemo/services/demo_services.dart';
import 'package:firstspringdemo/models/models.dart';

void main() {
  runApp(
    ProviderScope(
      overrides: [
        productServiceProvider.overrideWithValue(DemoProductService()),
        inventoryServiceProvider.overrideWithValue(DemoInventoryService()),
      ],
      child: const MyApp(),
    ),
  );
}

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'First Spring Demo (Riverpod)',
      theme: ThemeData(primarySwatch: Colors.blue),
      home: const HomeScreen(),
    );
  }
}

class HomeScreen extends StatelessWidget {
  const HomeScreen({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return DefaultTabController(
      length: 2,
      child: Builder(
        builder: (context) {
          return Scaffold(
            appBar: AppBar(
              title: const Text('First Spring Demo'),
              bottom: const TabBar(
                tabs: [
                  Tab(text: 'Products'),
                  Tab(text: 'Inventory'),
                ],
              ),
            ),
            body: const TabBarView(children: [ProductList(), InventoryList()]),
            floatingActionButton: FloatingActionButton(
              onPressed: () {
                final idx = DefaultTabController.of(context).index;
                final container = ProviderScope.containerOf(context);
                if (idx == 0) {
                  container
                      .read(productListProvider.notifier)
                      .add(
                        ProductCreateDto(
                          name: 'Added ${DateTime.now().second}',
                        ),
                      );
                } else {
                  container
                      .read(inventoryListProvider.notifier)
                      .add(
                        InventoryCreateDto(
                          productId: 'New-${DateTime.now().second}',
                          quantity: 1,
                        ),
                      );
                }
              },
              child: const Icon(Icons.add),
            ),
          );
        },
      ),
    );
  }
}
