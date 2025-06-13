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

## Pasos para el Setup del Proyecto

1. **Levantar la base de datos con Docker Compose**
   - Abrí una terminal y navega a la carpeta `dev-db`:
     ```
     cd dev-db
     ```
   - Ejecutá el siguiente comando para levantar los contenedores de la base de datos:
     ```
     docker compose up
     ```

2. **Levantar el backend con Spring Boot**
   - Abrí la extensión de Spring Boot en tu IDE (por ejemplo, VS Code o IntelliJ).
   - Seleccioná la opción **Run with profile** y elegí el perfil `dev` para correr la aplicación en modo desarrollo.

