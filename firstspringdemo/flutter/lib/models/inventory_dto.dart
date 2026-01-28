class InventoryDto {
  final String? inventoryId;
  final String? productId;
  final String? locationId;
  final int? quantity;
  final int? reorderPoint;
  final int? reorderQuantity;

  InventoryDto({
    this.inventoryId,
    this.productId,
    this.locationId,
    this.quantity,
    this.reorderPoint,
    this.reorderQuantity,
  });

  factory InventoryDto.fromJson(Map<String, dynamic> json) {
    return InventoryDto(
      inventoryId: json['inventoryId'],
      productId: json['productId'],
      locationId: json['locationId'],
      quantity: json['quantity'],
      reorderPoint: json['reorderPoint'],
      reorderQuantity: json['reorderQuantity'],
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'inventoryId': inventoryId,
      'productId': productId,
      'locationId': locationId,
      'quantity': quantity,
      'reorderPoint': reorderPoint,
      'reorderQuantity': reorderQuantity,
    };
  }
}
