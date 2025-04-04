# TPO_P2_MN_grupo_03 - Plataforma de E-commerce

## Estructura del Proyecto
```
src/
├── main/
│   ├── java/
│   │   └── uade/api/tpo_p2_mn_grupo_03/
│   │       ├── config/
│   │       ├── controller/
│   │       ├── dto/
│   │       │   ├── request/
│   │       │   └── response/
│   │       ├── exception/
│   │       ├── model/
│   │       │   ├── User.java
│   │       │   ├── Product.java
│   │       │   ├── Category.java
│   │       │   ├── Order.java
│   │       │   └── OrderProduct.java
│   │       ├── repository/
│   │       ├── security/
│   │       ├── service/
│   │       │   └── impl/
│   │       └── util/
│   └── resources/
│       ├── application.properties
│       ├── application-dev.properties
│       └── application-prod.properties
```

## Modelo de Datos

### User
- id: Long
- username: String
- email: String
- password: String
- firstName: String
- lastName: String
- role: Enum (BUYER, SELLER)
- products: List<Product> (OneToMany)
- orders: List<Order> (OneToMany)

### Product
- id: Long
- name: String
- description: String
- price: Double
- stock: Integer
- images: List<String>
- category: Category (ManyToOne)
- seller: User (ManyToOne)
- orderProducts: List<OrderProduct> (OneToMany)

### Category
- id: Long
- name: String
- products: List<Product> (OneToMany)

### Order
- id: Long
- user: User (ManyToOne)
- products: List<OrderProduct> (OneToMany)
- total: Double
- date: LocalDateTime
- status: Enum (CART, PENDING, COMPLETED, CANCELLED)

### OrderProduct
- id: Long
- order: Order (ManyToOne)
- product: Product (ManyToOne)
- quantity: Integer
- subtotal: Double
