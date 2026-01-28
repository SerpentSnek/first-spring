class ProductCreateDto {
  final String? name;
  final String? description;
  final double? price;
  final int? quantity;
  final String? productId;

  ProductCreateDto({
    this.name,
    this.description,
    this.price,
    this.quantity,
    this.productId,
  });

  factory ProductCreateDto.fromJson(Map<String, dynamic> json) {
    return ProductCreateDto(
      name: json['name'],
      description: json['description'],
      price: json['price']?.toDouble(),
      quantity: json['quantity'],
      productId: json['productId'],
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'name': name,
      'description': description,
      'price': price,
      'quantity': quantity,
      'productId': productId,
    };
  }
}
