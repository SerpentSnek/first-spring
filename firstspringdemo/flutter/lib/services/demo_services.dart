import 'dart:async';
import 'package:firstspringdemo/models/models.dart';
import 'package:firstspringdemo/services/product_service.dart';
import 'package:firstspringdemo/services/inventory_service.dart';

class DemoProductService implements ProductService {
  final List<ProductDto> _items = [
    ProductDto(
      productId: '1',
      name: 'Widget',
      description: 'A demo widget',
      price: 9.99,
      quantity: 10,
    ),
    ProductDto(
      productId: '2',
      name: 'Gadget',
      description: 'A demo gadget',
      price: 19.99,
      quantity: 5,
    ),
  ];

  @override
  Future<ProductDto> createProduct(ProductCreateDto createDto) async {
    final newItem = ProductDto(
      productId: DateTime.now().millisecondsSinceEpoch.toString(),
      name: createDto.name ?? 'New Product',
      description: createDto.description,
      price: createDto.price ?? 0.0,
      quantity: createDto.quantity ?? 0,
    );
    _items.add(newItem);
    return Future.delayed(const Duration(milliseconds: 100), () => newItem);
  }

  @override
  Future<void> deleteProduct(String id) async {
    _items.removeWhere((p) => p.productId == id);
    return Future.delayed(const Duration(milliseconds: 50));
  }

  @override
  Future<List<ProductDto>> getAllProducts() async {
    return Future.delayed(
      const Duration(milliseconds: 150),
      () => List<ProductDto>.from(_items),
    );
  }

  @override
  Future<ProductDto?> getProductById(String id) async {
    final found = _items.where((p) => p.productId == id).toList();
    return Future.delayed(
      const Duration(milliseconds: 50),
      () => found.isEmpty ? null : found.first,
    );
  }

  @override
  Future<ProductDto> updateProduct(
    String id,
    ProductUpdateDto updateDto,
  ) async {
    final idx = _items.indexWhere((p) => p.productId == id);
    if (idx == -1) throw Exception('Not found');
    final current = _items[idx];
    final updated = ProductDto(
      productId: current.productId,
      name: updateDto.name ?? current.name,
      description: updateDto.description ?? current.description,
      price: updateDto.price ?? current.price,
      quantity: updateDto.quantity ?? current.quantity,
      imgUrl: current.imgUrl,
      locationId: current.locationId,
    );
    _items[idx] = updated;
    return Future.delayed(const Duration(milliseconds: 80), () => updated);
  }
}

class DemoInventoryService implements InventoryService {
  final List<InventoryDto> _items = [
    InventoryDto(
      inventoryId: '1',
      productId: '1',
      locationId: 'L1',
      quantity: 10,
    ),
    InventoryDto(
      inventoryId: '2',
      productId: '2',
      locationId: 'L1',
      quantity: 5,
    ),
  ];

  @override
  Future<InventoryDto> createInventory(InventoryCreateDto createDto) async {
    final newItem = InventoryDto(
      inventoryId: DateTime.now().millisecondsSinceEpoch.toString(),
      productId: createDto.productId,
      locationId: createDto.locationId,
      quantity: createDto.quantity ?? 0,
      reorderPoint: createDto.reorderPoint,
      reorderQuantity: createDto.reorderQuantity,
    );
    _items.add(newItem);
    return Future.delayed(const Duration(milliseconds: 100), () => newItem);
  }

  @override
  Future<void> deleteInventory(String id) async {
    _items.removeWhere((i) => i.inventoryId == id);
    return Future.delayed(const Duration(milliseconds: 50));
  }

  @override
  Future<List<InventoryDto>> getAllInventory() async {
    return Future.delayed(
      const Duration(milliseconds: 150),
      () => List<InventoryDto>.from(_items),
    );
  }

  @override
  Future<InventoryDto?> getInventoryById(String id) async {
    final found = _items.where((i) => i.inventoryId == id).toList();
    return Future.delayed(
      const Duration(milliseconds: 50),
      () => found.isEmpty ? null : found.first,
    );
  }

  @override
  Future<InventoryDto> partialUpdateInventory(
    String id,
    InventoryUpdateDto updateDto,
  ) async {
    final idx = _items.indexWhere((i) => i.inventoryId == id);
    if (idx == -1) throw Exception('Not found');
    final current = _items[idx];
    final updated = InventoryDto(
      inventoryId: current.inventoryId,
      productId: updateDto.productId ?? current.productId,
      locationId: updateDto.locationId ?? current.locationId,
      quantity: updateDto.quantity ?? current.quantity,
      reorderPoint: updateDto.reorderPoint ?? current.reorderPoint,
      reorderQuantity: updateDto.reorderQuantity ?? current.reorderQuantity,
    );
    _items[idx] = updated;
    return Future.delayed(const Duration(milliseconds: 80), () => updated);
  }

  @override
  Future<InventoryDto> updateInventory(
    String id,
    InventoryUpdateDto updateDto,
  ) async {
    final idx = _items.indexWhere((i) => i.inventoryId == id);
    if (idx == -1) throw Exception('Not found');
    final current = _items[idx];
    final updated = InventoryDto(
      inventoryId: current.inventoryId,
      productId: updateDto.productId ?? current.productId,
      locationId: updateDto.locationId ?? current.locationId,
      quantity: updateDto.quantity ?? current.quantity,
      reorderPoint: updateDto.reorderPoint ?? current.reorderPoint,
      reorderQuantity: updateDto.reorderQuantity ?? current.reorderQuantity,
    );
    _items[idx] = updated;
    return Future.delayed(const Duration(milliseconds: 80), () => updated);
  }
}
