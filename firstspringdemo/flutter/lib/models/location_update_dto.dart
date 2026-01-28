class LocationUpdateDto {
  final String? name;
  final String? address;
  final String? city;
  final String? state;
  final String? country;
  final String? postalCode;
  final String? locationId;

  LocationUpdateDto({
    this.name,
    this.address,
    this.city,
    this.state,
    this.country,
    this.postalCode,
    this.locationId,
  });

  factory LocationUpdateDto.fromJson(Map<String, dynamic> json) {
    return LocationUpdateDto(
      name: json['name'],
      address: json['address'],
      city: json['city'],
      state: json['state'],
      country: json['country'],
      postalCode: json['postalCode'],
      locationId: json['locationId'],
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'name': name,
      'address': address,
      'city': city,
      'state': state,
      'country': country,
      'postalCode': postalCode,
      'locationId': locationId,
    };
  }
}
