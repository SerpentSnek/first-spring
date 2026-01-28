class ProductUpdateDto {
  final String? name;
  final String? description;
  final double? price;
  final int? quantity;
  final String? locationId;

  ProductUpdateDto({
    this.name,
    this.description,
    this.price,
    this.quantity,
    this.locationId,
  });

  factory ProductUpdateDto.fromJson(Map<String, dynamic> json) {
    return ProductUpdateDto(
      name: json['name'],
      description: json['description'],
      price: json['price']?.toDouble(),
      quantity: json['quantity'],
      locationId: json['locationId'],
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'name': name,
      'description': description,
      'price': price,
      'quantity': quantity,
      'locationId': locationId,
    };
  }
}
