import 'package:flutter_test/flutter_test.dart';
import 'package:mocktail/mocktail.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:firstspringdemo/providers/product_provider.dart';
import 'package:firstspringdemo/services/product_service.dart';
import 'package:firstspringdemo/models/models.dart';

class MockProductService extends Mock implements ProductService {}

class FakeProductCreateDto extends Fake implements ProductCreateDto {}

class FakeProductUpdateDto extends Fake implements ProductUpdateDto {}

void main() {
  setUpAll(() {
    registerFallbackValue(FakeProductCreateDto());
    registerFallbackValue(FakeProductUpdateDto());
  });

  late MockProductService mockService;

  setUp(() {
    mockService = MockProductService();
  });

  test('loads products successfully', () async {
    final products = [ProductDto(productId: '1', name: 'A')];
    when(() => mockService.getAllProducts()).thenAnswer((_) async => products);

    final container = ProviderContainer(
      overrides: [productServiceProvider.overrideWithValue(mockService)],
    );

    addTearDown(container.dispose);

    final notifier = container.read(productListProvider.notifier);
    await notifier.refresh();

    final state = container.read(productListProvider);
    expect(state.asData?.value, products);
  });

  test('handles errors', () async {
    when(() => mockService.getAllProducts()).thenThrow(Exception('fail'));

    final container = ProviderContainer(
      overrides: [productServiceProvider.overrideWithValue(mockService)],
    );
    addTearDown(container.dispose);

    final notifier = container.read(productListProvider.notifier);
    await notifier.refresh();

    final state = container.read(productListProvider);
    expect(state.hasError, isTrue);
  });

  test('add product updates state', () async {
    final initial = [ProductDto(productId: '1', name: 'A')];
    final created = ProductDto(productId: '2', name: 'B');

    when(() => mockService.getAllProducts()).thenAnswer((_) async => initial);
    when(
      () => mockService.createProduct(any()),
    ).thenAnswer((_) async => created);

    final container = ProviderContainer(
      overrides: [productServiceProvider.overrideWithValue(mockService)],
    );
    addTearDown(container.dispose);

    final notifier = container.read(productListProvider.notifier);
    await notifier.refresh();
    await notifier.add(ProductCreateDto(name: 'B'));

    final state = container.read(productListProvider);
    expect(state.asData?.value.length, 2);
    expect(state.asData?.value.last.name, 'B');
  });

  test('update product replaces item', () async {
    final initial = [ProductDto(productId: '1', name: 'A')];
    final updated = ProductDto(productId: '1', name: 'A+');

    when(() => mockService.getAllProducts()).thenAnswer((_) async => initial);
    when(
      () => mockService.updateProduct('1', any()),
    ).thenAnswer((_) async => updated);

    final container = ProviderContainer(
      overrides: [productServiceProvider.overrideWithValue(mockService)],
    );
    addTearDown(container.dispose);

    final notifier = container.read(productListProvider.notifier);
    await notifier.refresh();
    await notifier.updateProduct('1', ProductUpdateDto(name: 'A+'));

    final state = container.read(productListProvider);
    expect(state.asData?.value.first.name, 'A+');
  });

  test('delete product removes item', () async {
    final initial = [ProductDto(productId: '1', name: 'A')];

    when(() => mockService.getAllProducts()).thenAnswer((_) async => initial);
    when(
      () => mockService.deleteProduct('1'),
    ).thenAnswer((_) async => Future.value());

    final container = ProviderContainer(
      overrides: [productServiceProvider.overrideWithValue(mockService)],
    );
    addTearDown(container.dispose);

    final notifier = container.read(productListProvider.notifier);
    await notifier.refresh();
    await notifier.delete('1');

    final state = container.read(productListProvider);
    expect(state.asData?.value, isEmpty);
  });
}
