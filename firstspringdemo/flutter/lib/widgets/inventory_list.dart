import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:firstspringdemo/providers/inventory_provider.dart';
import 'package:firstspringdemo/models/models.dart';

class InventoryList extends ConsumerWidget {
  const InventoryList({Key? key}) : super(key: key);

  Future<void> _showEditDialog(
    BuildContext context,
    WidgetRef ref,
    InventoryDto item,
  ) async {
    final qtyController = TextEditingController(text: '${item.quantity ?? 0}');

    final result = await showDialog<bool>(
      context: context,
      builder: (ctx) => AlertDialog(
        title: const Text('Edit Inventory'),
        content: Column(
          mainAxisSize: MainAxisSize.min,
          children: [
            TextField(
              key: const Key('edit_qty_field'),
              controller: qtyController,
              decoration: const InputDecoration(labelText: 'Quantity'),
              keyboardType: TextInputType.number,
            ),
          ],
        ),
        actions: [
          TextButton(
            onPressed: () => Navigator.of(ctx).pop(false),
            child: const Text('Cancel'),
          ),
          TextButton(
            onPressed: () => Navigator.of(ctx).pop(true),
            child: const Text('Save'),
          ),
        ],
      ),
    );

    if (result == true) {
      final q = int.tryParse(qtyController.text) ?? 0;
      await ref
          .read(inventoryListProvider.notifier)
          .updateInventory(
            item.inventoryId ?? '',
            InventoryUpdateDto(quantity: q),
          );
    }
  }

  Future<void> _confirmDelete(
    BuildContext context,
    WidgetRef ref,
    InventoryDto item,
  ) async {
    final result = await showDialog<bool>(
      context: context,
      builder: (ctx) => AlertDialog(
        title: const Text('Delete Inventory'),
        content: Text('Delete inventory ${item.inventoryId ?? ''}?'),
        actions: [
          TextButton(
            onPressed: () => Navigator.of(ctx).pop(false),
            child: const Text('Cancel'),
          ),
          TextButton(
            onPressed: () => Navigator.of(ctx).pop(true),
            child: const Text('Delete'),
          ),
        ],
      ),
    );

    if (result == true) {
      await ref
          .read(inventoryListProvider.notifier)
          .delete(item.inventoryId ?? '');
    }
  }

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    final itemsAsync = ref.watch(inventoryListProvider);
    return itemsAsync.when(
      data: (items) {
        if (items.isEmpty) {
          return const Center(child: Text('No inventory'));
        }
        return ListView.separated(
          itemCount: items.length,
          separatorBuilder: (_, __) => const Divider(height: 1),
          itemBuilder: (context, i) {
            final it = items[i];
            return ListTile(
              key: Key('inventory_tile_${it.inventoryId}'),
              title: Text(it.productId ?? ''),
              subtitle: Text('qty: ${it.quantity ?? 0}'),
              onTap: () => _showEditDialog(context, ref, it),
              onLongPress: () => _confirmDelete(context, ref, it),
            );
          },
        );
      },
      loading: () => const Center(child: CircularProgressIndicator()),
      error: (e, _) => Center(child: Text('Error: $e')),
    );
  }
}
