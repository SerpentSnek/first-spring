class LocationDto {
  final String? locationId;
  final String? name;
  final String? address;

  LocationDto({this.locationId, this.name, this.address});

  factory LocationDto.fromJson(Map<String, dynamic> json) {
    return LocationDto(
      locationId: json['locationId'],
      name: json['name'],
      address: json['address'],
    );
  }

  Map<String, dynamic> toJson() {
    return {'locationId': locationId, 'name': name, 'address': address};
  }
}
