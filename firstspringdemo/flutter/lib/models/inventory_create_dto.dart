class InventoryCreateDto {
  final String? productId;
  final String? locationId;
  final int? quantity;
  final int? reorderPoint;
  final int? reorderQuantity;

  InventoryCreateDto({
    this.productId,
    this.locationId,
    this.quantity,
    this.reorderPoint,
    this.reorderQuantity,
  });

  factory InventoryCreateDto.fromJson(Map<String, dynamic> json) {
    return InventoryCreateDto(
      productId: json['productId'],
      locationId: json['locationId'],
      quantity: json['quantity'],
      reorderPoint: json['reorderPoint'],
      reorderQuantity: json['reorderQuantity'],
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'productId': productId,
      'locationId': locationId,
      'quantity': quantity,
      'reorderPoint': reorderPoint,
      'reorderQuantity': reorderQuantity,
    };
  }
}
