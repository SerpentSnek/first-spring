class InventoryUpdateDto {
  final int? quantity;
  final String? productId;
  final String? locationId;
  final int? reorderPoint;
  final int? reorderQuantity;

  InventoryUpdateDto({
    this.quantity,
    this.productId,
    this.locationId,
    this.reorderPoint,
    this.reorderQuantity,
  });

  factory InventoryUpdateDto.fromJson(Map<String, dynamic> json) {
    return InventoryUpdateDto(
      quantity: json['quantity'],
      productId: json['productId'],
      locationId: json['locationId'],
      reorderPoint: json['reorderPoint'],
      reorderQuantity: json['reorderQuantity'],
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'quantity': quantity,
      'productId': productId,
      'locationId': locationId,
      'reorderPoint': reorderPoint,
      'reorderQuantity': reorderQuantity,
    };
  }
}
