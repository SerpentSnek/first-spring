import 'package:flutter_test/flutter_test.dart';
import 'package:mocktail/mocktail.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:firstspringdemo/providers/inventory_provider.dart';
import 'package:firstspringdemo/services/inventory_service.dart';
import 'package:firstspringdemo/models/models.dart';

class MockInventoryService extends Mock implements InventoryService {}

class FakeInventoryCreateDto extends Fake implements InventoryCreateDto {}

class FakeInventoryUpdateDto extends Fake implements InventoryUpdateDto {}

void main() {
  setUpAll(() {
    registerFallbackValue(FakeInventoryCreateDto());
    registerFallbackValue(FakeInventoryUpdateDto());
  });

  late MockInventoryService mockService;

  setUp(() {
    mockService = MockInventoryService();
  });

  test('loads inventory successfully', () async {
    final items = [InventoryDto(inventoryId: '1', productId: 'A', quantity: 1)];
    when(() => mockService.getAllInventory()).thenAnswer((_) async => items);

    final container = ProviderContainer(
      overrides: [inventoryServiceProvider.overrideWithValue(mockService)],
    );
    addTearDown(container.dispose);

    final notifier = container.read(inventoryListProvider.notifier);
    await notifier.refresh();

    final state = container.read(inventoryListProvider);
    expect(state.asData?.value, items);
  });

  test('add inventory updates state', () async {
    final initial = [
      InventoryDto(inventoryId: '1', productId: 'A', quantity: 1),
    ];
    final created = InventoryDto(inventoryId: '2', productId: 'B', quantity: 5);

    when(() => mockService.getAllInventory()).thenAnswer((_) async => initial);
    when(
      () => mockService.createInventory(any()),
    ).thenAnswer((_) async => created);

    final container = ProviderContainer(
      overrides: [inventoryServiceProvider.overrideWithValue(mockService)],
    );
    addTearDown(container.dispose);

    final notifier = container.read(inventoryListProvider.notifier);
    await notifier.refresh();
    await notifier.add(InventoryCreateDto(productId: 'B', quantity: 5));

    final state = container.read(inventoryListProvider);
    expect(state.asData?.value.length, 2);
    expect(state.asData?.value.last.productId, 'B');
  });

  test('update inventory replaces item', () async {
    final initial = [
      InventoryDto(inventoryId: '1', productId: 'A', quantity: 1),
    ];
    final updated = InventoryDto(
      inventoryId: '1',
      productId: 'A',
      quantity: 10,
    );

    when(() => mockService.getAllInventory()).thenAnswer((_) async => initial);
    when(
      () => mockService.updateInventory('1', any()),
    ).thenAnswer((_) async => updated);

    final container = ProviderContainer(
      overrides: [inventoryServiceProvider.overrideWithValue(mockService)],
    );
    addTearDown(container.dispose);

    final notifier = container.read(inventoryListProvider.notifier);
    await notifier.refresh();
    await notifier.updateInventory('1', InventoryUpdateDto(quantity: 10));

    final state = container.read(inventoryListProvider);
    expect(state.asData?.value.first.quantity, 10);
  });

  test('delete inventory removes item', () async {
    final initial = [
      InventoryDto(inventoryId: '1', productId: 'A', quantity: 1),
    ];

    when(() => mockService.getAllInventory()).thenAnswer((_) async => initial);
    when(
      () => mockService.deleteInventory('1'),
    ).thenAnswer((_) async => Future.value());

    final container = ProviderContainer(
      overrides: [inventoryServiceProvider.overrideWithValue(mockService)],
    );
    addTearDown(container.dispose);

    final notifier = container.read(inventoryListProvider.notifier);
    await notifier.refresh();
    await notifier.delete('1');

    final state = container.read(inventoryListProvider);
    expect(state.asData?.value, isEmpty);
  });
}
