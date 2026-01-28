class ProductDto {
  final String? productId;
  final String? name;
  final String? description;
  final double? price;
  final int? quantity;
  final String? imgUrl;
  final String? locationId;

  ProductDto({
    this.productId,
    this.name,
    this.description,
    this.price,
    this.quantity,
    this.imgUrl,
    this.locationId,
  });

  factory ProductDto.fromJson(Map<String, dynamic> json) {
    return ProductDto(
      productId: json['productId'],
      name: json['name'],
      description: json['description'],
      price: json['price']?.toDouble(),
      quantity: json['quantity'],
      imgUrl: json['imgUrl'],
      locationId: json['locationId'],
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'productId': productId,
      'name': name,
      'description': description,
      'price': price,
      'quantity': quantity,
      'imgUrl': imgUrl,
      'locationId': locationId,
    };
  }
}
