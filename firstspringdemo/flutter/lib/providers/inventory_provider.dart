import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:firstspringdemo/services/inventory_service.dart';
import 'package:firstspringdemo/models/models.dart';

final inventoryServiceProvider = Provider<InventoryService>(
  (ref) => InventoryService(),
);

final inventoryListProvider =
    AsyncNotifierProvider<InventoryListNotifier, List<InventoryDto>>(
      InventoryListNotifier.new,
    );

class InventoryListNotifier extends AsyncNotifier<List<InventoryDto>> {
  @override
  Future<List<InventoryDto>> build() => _load();

  Future<List<InventoryDto>> _load() async {
    final svc = ref.read(inventoryServiceProvider);
    return svc.getAllInventory();
  }

  Future<void> refresh() async {
    state = await AsyncValue.guard(() => _load());
  }

  Future<void> add(InventoryCreateDto dto) async {
    state = await AsyncValue.guard(() async {
      final created = await ref
          .read(inventoryServiceProvider)
          .createInventory(dto);
      final current = state.value ?? [];
      return [...current, created];
    });
  }

  Future<void> updateInventory(String id, InventoryUpdateDto dto) async {
    state = await AsyncValue.guard(() async {
      final updated = await ref
          .read(inventoryServiceProvider)
          .updateInventory(id, dto);
      final current = state.value ?? [];
      return current.map((p) => p.inventoryId == id ? updated : p).toList();
    });
  }

  Future<void> delete(String id) async {
    state = await AsyncValue.guard(() async {
      await ref.read(inventoryServiceProvider).deleteInventory(id);
      final current = state.value ?? [];
      return current.where((p) => p.inventoryId != id).toList();
    });
  }
}
