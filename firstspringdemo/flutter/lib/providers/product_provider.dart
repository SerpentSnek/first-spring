import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:firstspringdemo/services/product_service.dart';
import 'package:firstspringdemo/models/models.dart';

final productServiceProvider = Provider<ProductService>(
  (ref) => ProductService(),
);

final productListProvider =
    AsyncNotifierProvider<ProductListNotifier, List<ProductDto>>(
      ProductListNotifier.new,
    );

class ProductListNotifier extends AsyncNotifier<List<ProductDto>> {
  @override
  Future<List<ProductDto>> build() => _load();

  Future<List<ProductDto>> _load() async {
    final svc = ref.read(productServiceProvider);
    return svc.getAllProducts();
  }

  Future<void> refresh() async {
    state = await AsyncValue.guard(() => _load());
  }

  Future<void> add(ProductCreateDto dto) async {
    state = await AsyncValue.guard(() async {
      final created = await ref.read(productServiceProvider).createProduct(dto);
      final current = state.value ?? [];
      return [...current, created];
    });
  }

  Future<void> updateProduct(String id, ProductUpdateDto dto) async {
    state = await AsyncValue.guard(() async {
      final updated = await ref
          .read(productServiceProvider)
          .updateProduct(id, dto);
      final current = state.value ?? [];
      return current.map((p) => p.productId == id ? updated : p).toList();
    });
  }

  Future<void> delete(String id) async {
    state = await AsyncValue.guard(() async {
      await ref.read(productServiceProvider).deleteProduct(id);
      final current = state.value ?? [];
      return current.where((p) => p.productId != id).toList();
    });
  }
}
