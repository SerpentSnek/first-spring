# API Reference

## Base URL

```
http://localhost:8080
```

## Authentication

Currently, the API does not require authentication. This will be implemented with Spring Security in a future update.

---

## Products API

### List All Products

```http
GET /products
```

**Response:** `200 OK`

```json
[
  {
    "id": "550e8400-e29b-41d4-a716-446655440000",
    "name": "Product Name",
    "description": "Product description",
    "sku": "SKU-001",
    "price": 29.99,
    "createdAt": "2026-01-27T10:00:00Z",
    "updatedAt": "2026-01-27T10:00:00Z"
  }
]
```

---

### Get Product by ID

```http
GET /products/{id}
```

**Path Parameters:**
- `id` (UUID) - Product identifier

**Response:** `200 OK`

```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "name": "Product Name",
  "description": "Product description",
  "sku": "SKU-001",
  "price": 29.99,
  "createdAt": "2026-01-27T10:00:00Z",
  "updatedAt": "2026-01-27T10:00:00Z"
}
```

**Error Responses:**
- `404 Not Found` - Product does not exist

---

### Create Product

```http
POST /products
Content-Type: application/json
```

**Request Body:**

```json
{
  "name": "New Product",
  "description": "Product description",
  "sku": "SKU-002",
  "price": 49.99
}
```

**Response:** `201 Created`

```json
{
  "id": "550e8400-e29b-41d4-a716-446655440001",
  "name": "New Product",
  "description": "Product description",
  "sku": "SKU-002",
  "price": 49.99,
  "createdAt": "2026-01-27T10:00:00Z",
  "updatedAt": "2026-01-27T10:00:00Z"
}
```

**Validation:**
- All fields are required
- Price must be positive
- SKU must be unique

---

### Update Product

```http
PUT /products/{id}
Content-Type: application/json
```

**Path Parameters:**
- `id` (UUID) - Product identifier

**Request Body:**

```json
{
  "name": "Updated Product Name",
  "description": "Updated description",
  "sku": "SKU-002",
  "price": 59.99
}
```

**Response:** `200 OK`

```json
{
  "id": "550e8400-e29b-41d4-a716-446655440001",
  "name": "Updated Product Name",
  "description": "Updated description",
  "sku": "SKU-002",
  "price": 59.99,
  "createdAt": "2026-01-27T10:00:00Z",
  "updatedAt": "2026-01-27T10:01:00Z"
}
```

**Error Responses:**
- `404 Not Found` - Product does not exist

---

### Delete Product

```http
DELETE /products/{id}
```

**Path Parameters:**
- `id` (UUID) - Product identifier

**Response:** `204 No Content`

**Error Responses:**
- `404 Not Found` - Product does not exist

---

## Locations API

### List All Locations

```http
GET /locations
```

**Response:** `200 OK`

```json
[
  {
    "id": "660e8400-e29b-41d4-a716-446655440000",
    "name": "Warehouse A",
    "address": "123 Main St",
    "city": "New York",
    "state": "NY",
    "zipCode": "10001",
    "createdAt": "2026-01-27T10:00:00Z",
    "updatedAt": "2026-01-27T10:00:00Z"
  }
]
```

---

### Get Location by ID

```http
GET /locations/{id}
```

**Path Parameters:**
- `id` (UUID) - Location identifier

**Response:** `200 OK`

```json
{
  "id": "660e8400-e29b-41d4-a716-446655440000",
  "name": "Warehouse A",
  "address": "123 Main St",
  "city": "New York",
  "state": "NY",
  "zipCode": "10001",
  "createdAt": "2026-01-27T10:00:00Z",
  "updatedAt": "2026-01-27T10:00:00Z"
}
```

**Error Responses:**
- `404 Not Found` - Location does not exist

---

### Create Location

```http
POST /locations
Content-Type: application/json
```

**Request Body:**

```json
{
  "name": "Warehouse B",
  "address": "456 Oak Ave",
  "city": "Los Angeles",
  "state": "CA",
  "zipCode": "90001"
}
```

**Response:** `201 Created`

```json
{
  "id": "660e8400-e29b-41d4-a716-446655440001",
  "name": "Warehouse B",
  "address": "456 Oak Ave",
  "city": "Los Angeles",
  "state": "CA",
  "zipCode": "90001",
  "createdAt": "2026-01-27T10:00:00Z",
  "updatedAt": "2026-01-27T10:00:00Z"
}
```

---

### Update Location

```http
PUT /locations/{id}
Content-Type: application/json
```

**Path Parameters:**
- `id` (UUID) - Location identifier

**Request Body:**

```json
{
  "name": "Warehouse B Updated",
  "address": "456 Oak Ave",
  "city": "Los Angeles",
  "state": "CA",
  "zipCode": "90001"
}
```

**Response:** `200 OK`

---

### Delete Location

```http
DELETE /locations/{id}
```

**Path Parameters:**
- `id` (UUID) - Location identifier

**Response:** `204 No Content`

---

## Inventory API

### List All Inventory Records

```http
GET /inventory
```

**Response:** `200 OK`

```json
[
  {
    "id": "770e8400-e29b-41d4-a716-446655440000",
    "productId": "550e8400-e29b-41d4-a716-446655440000",
    "locationId": "660e8400-e29b-41d4-a716-446655440000",
    "quantity": 100,
    "minimumQuantity": 10,
    "maximumQuantity": 500,
    "createdAt": "2026-01-27T10:00:00Z",
    "updatedAt": "2026-01-27T10:00:00Z"
  }
]
```

---

### Get Inventory Record by ID

```http
GET /inventory/{id}
```

**Path Parameters:**
- `id` (UUID) - Inventory record identifier

**Response:** `200 OK`

```json
{
  "id": "770e8400-e29b-41d4-a716-446655440000",
  "productId": "550e8400-e29b-41d4-a716-446655440000",
  "locationId": "660e8400-e29b-41d4-a716-446655440000",
  "quantity": 100,
  "minimumQuantity": 10,
  "maximumQuantity": 500,
  "createdAt": "2026-01-27T10:00:00Z",
  "updatedAt": "2026-01-27T10:00:00Z"
}
```

---

### Create Inventory Record

```http
POST /inventory
Content-Type: application/json
```

**Request Body:**

```json
{
  "productId": "550e8400-e29b-41d4-a716-446655440000",
  "locationId": "660e8400-e29b-41d4-a716-446655440000",
  "quantity": 150,
  "minimumQuantity": 10,
  "maximumQuantity": 500
}
```

**Response:** `201 Created`

```json
{
  "id": "770e8400-e29b-41d4-a716-446655440001",
  "productId": "550e8400-e29b-41d4-a716-446655440000",
  "locationId": "660e8400-e29b-41d4-a716-446655440000",
  "quantity": 150,
  "minimumQuantity": 10,
  "maximumQuantity": 500,
  "createdAt": "2026-01-27T10:00:00Z",
  "updatedAt": "2026-01-27T10:00:00Z"
}
```

**Validation:**
- productId and locationId must reference existing records
- quantity must be >= 0
- minimumQuantity must be <= quantity
- maximumQuantity must be >= quantity

---

### Update Inventory Record

```http
PUT /inventory/{id}
Content-Type: application/json
```

**Path Parameters:**
- `id` (UUID) - Inventory record identifier

**Request Body:**

```json
{
  "productId": "550e8400-e29b-41d4-a716-446655440000",
  "locationId": "660e8400-e29b-41d4-a716-446655440000",
  "quantity": 200,
  "minimumQuantity": 20,
  "maximumQuantity": 600
}
```

**Response:** `200 OK`

---

### Delete Inventory Record

```http
DELETE /inventory/{id}
```

**Path Parameters:**
- `id` (UUID) - Inventory record identifier

**Response:** `204 No Content`

---

## Health Check

```http
GET /actuator/health
```

**Response:** `200 OK`

```json
{
  "status": "UP",
  "components": {
    "db": {
      "status": "UP",
      "details": {
        "database": "PostgreSQL",
        "validationQuery": "isValid()"
      }
    }
  }
}
```

---

## Error Responses

All error responses follow this format:

```json
{
  "timestamp": "2026-01-27T10:00:00Z",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed for argument",
  "path": "/products"
}
```

**Common Status Codes:**
- `200 OK` - Successful GET request
- `201 Created` - Successful POST request
- `204 No Content` - Successful DELETE request
- `400 Bad Request` - Invalid request data
- `404 Not Found` - Resource not found
- `500 Internal Server Error` - Server error

---

## Rate Limiting

Currently, no rate limiting is implemented. This may be added in future versions.

---

## Pagination

Currently, list endpoints return all results. Pagination support may be added in future versions.

---

**Last Updated:** January 2026
