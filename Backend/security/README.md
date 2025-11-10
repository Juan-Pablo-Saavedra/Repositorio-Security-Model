# 📦 Sistema de Inventario - WorkSena

## 📋 Descripción General

**WorkSena Inventory System** es una aplicación completa de gestión de inventario desarrollada con Spring Boot que permite administrar productos, categorías, proveedores, clientes, pedidos y usuarios. El sistema soporta múltiples bases de datos (MySQL, PostgreSQL y SQL Server) y cuenta con autenticación JWT, documentación Swagger y una arquitectura modular siguiendo las mejores prácticas de desarrollo.

### 🎯 Características Principales

- ✅ **Gestión completa de inventario**: Productos, categorías, proveedores
- ✅ **Sistema de pedidos**: Clientes y órdenes de compra
- ✅ **Autenticación y autorización**: JWT con Spring Security
- ✅ **Multi-base de datos**: Soporte para MySQL, PostgreSQL y SQL Server
- ✅ **API RESTful**: Endpoints documentados con Swagger/OpenAPI
- ✅ **Validaciones robustas**: Validaciones en entidades y DTOs
- ✅ **Manejo de excepciones**: Sistema global de manejo de errores
- ✅ **Migraciones de base de datos**: Flyway para control de versiones
- ✅ **Arquitectura modular**: Patrón Repository, Service, Factory

---

## 🏗️ Arquitectura del Sistema - N-Capas

### Arquitectura N-Capas (Layered Architecture)

El sistema sigue una **arquitectura N-capas** clara y bien definida, donde cada capa tiene responsabilidades específicas y se comunica únicamente con las capas adyacentes:

```
┌─────────────────────────────────────────────────────────────┐
│                    Capa de Presentación                     │
│                    (Presentation Layer)                     │
├─────────────────────────────────────────────────────────────┤
│  • Controllers REST                                        │
│  • DTOs (Data Transfer Objects)                            │
│  • Validaciones de entrada                                 │
│  • Manejo de respuestas HTTP                               │
│  • Documentación Swagger                                   │
└─────────────────────┬───────────────────────────────────────┘
                      │
                      ▼
┌─────────────────────────────────────────────────────────────┐
│                 Capa de Lógica de Negocio                   │
│                 (Business Logic Layer)                      │
├─────────────────────────────────────────────────────────────┤
│  • Servicios de aplicación                                 │
│  • Lógica de negocio                                       │
│  • Validaciones de negocio                                 │
│  • Gestión de transacciones                                │
│  • Manejo de excepciones                                   │
└─────────────────────┬───────────────────────────────────────┘
                      │
                      ▼
┌─────────────────────────────────────────────────────────────┐
│                 Capa de Acceso a Datos                      │
│                 (Data Access Layer)                         │
├─────────────────────────────────────────────────────────────┤
│  • Repositories (JPA)                                      │
│  • Entities (JPA)                                          │
│  • Queries personalizadas                                  │
│  • Mapeo objeto-relacional                                 │
└─────────────────────┬───────────────────────────────────────┘
                      │
                      ▼
┌─────────────────────────────────────────────────────────────┐
│                 Capa de Infraestructura                     │
│                 (Infrastructure Layer)                      │
├─────────────────────────────────────────────────────────────┤
│  • Configuraciones (Security, JWT, Swagger)               │
│  • Manejo global de excepciones                            │
│  • Factories para creación de objetos                      │
│  • Utilidades y helpers                                    │
└─────────────────────┬───────────────────────────────────────┘
                      │
                      ▼
┌─────────────────────────────────────────────────────────────┐
│                 Capa de Persistencia                        │
│                 (Persistence Layer)                         │
├─────────────────────────────────────────────────────────────┤
│  • Base de Datos (MySQL/PostgreSQL/SQL Server)             │
│  • Migraciones Flyway                                      │
│  • Conexiones y pools                                      │
│  • ORM (Hibernate/JPA)                                     │
└─────────────────────────────────────────────────────────────┘
```

### Separación de Responsabilidades por Capa

#### 🖥️ **Capa de Presentación** (Controllers)
- **Responsabilidad**: Manejar requests HTTP y responses
- **Componentes**: `@RestController`, DTOs, Validaciones
- **Principio**: Solo conoce la interfaz HTTP, delega lógica a servicios

#### 🧠 **Capa de Lógica de Negocio** (Services)
- **Responsabilidad**: Implementar reglas de negocio
- **Componentes**: `@Service`, Lógica de aplicación, Transacciones
- **Principio**: No conoce HTTP ni persistencia directa

#### 💾 **Capa de Acceso a Datos** (Repositories)
- **Responsabilidad**: Abstraer acceso a datos
- **Componentes**: `@Repository`, JPA, Queries
- **Principio**: Solo conoce entidades y queries

#### 🔧 **Capa de Infraestructura** (Infrastructure)
- **Responsabilidad**: Configuraciones y utilidades transversales
- **Componentes**: Config classes, Exception handlers, Factories
- **Principio**: Soporte técnico para otras capas

#### 🗄️ **Capa de Persistencia** (Persistence)
- **Responsabilidad**: Almacenamiento físico de datos
- **Componentes**: Base de datos, Flyway, Conexiones
- **Principio**: Independiente de la lógica de aplicación

### Estructura por Capas y Módulos

El sistema combina **arquitectura N-capas** con **módulos funcionales** organizados por dominio:

```
src/main/java/com/sena/inventorysystem/
├── Infrastructure/           # 🏗️ CAPA DE INFRAESTRUCTURA
│   ├── config/              # Configuraciones (JWT, Security, Swagger)
│   ├── DTO/                 # ApiResponse (comunicación entre capas)
│   └── exceptions/          # GlobalExceptionHandler, Custom Exceptions
├── UserManagement/          # 👥 MÓDULO DE GESTIÓN DE USUARIOS
│   ├── Controller/          # 🌐 CAPA DE PRESENTACIÓN - UserController
│   ├── Service/            # 🧠 CAPA DE NEGOCIO - UserService, IUserService
│   ├── Repository/         # 💾 CAPA DE DATOS - UserRepository
│   ├── Factory/            # 🔧 CAPA DE INFRAESTRUCTURA - UserFactory
│   ├── Entity/             # 💾 CAPA DE DATOS - User
│   └── DTO/                # 🌐 CAPA DE PRESENTACIÓN - UserDto, AuthRequest, AuthResponse
├── ProductManagement/       # 📦 MÓDULO DE GESTIÓN DE PRODUCTOS
│   ├── Controller/          # 🌐 CAPA DE PRESENTACIÓN - ProductController, CategoryController, SupplierController
│   ├── Service/            # 🧠 CAPA DE NEGOCIO - ProductService, CategoryService, SupplierService
│   ├── Repository/         # 💾 CAPA DE DATOS - ProductRepository, CategoryRepository, SupplierRepository
│   ├── Factory/            # 🔧 CAPA DE INFRAESTRUCTURA - ProductFactory, CategoryFactory, SupplierFactory
│   ├── Entity/             # 💾 CAPA DE DATOS - Product, Category, Supplier, ProductCategory, SupplierProduct
│   └── DTO/                # 🌐 CAPA DE PRESENTACIÓN - ProductDto, CategoryDto, SupplierDto
└── OrderManagement/         # 📋 MÓDULO DE GESTIÓN DE PEDIDOS
    ├── Controller/          # 🌐 CAPA DE PRESENTACIÓN - OrderController, ClientController
    ├── Service/            # 🧠 CAPA DE NEGOCIO - OrderService, ClientService
    ├── Repository/         # 💾 CAPA DE DATOS - OrderRepository, ClientRepository
    ├── Factory/            # 🔧 CAPA DE INFRAESTRUCTURA - OrderFactory, ClientFactory
    ├── Entity/             # 💾 CAPA DE DATOS - Order, Client
    └── DTO/                # 🌐 CAPA DE PRESENTACIÓN - OrderDto, ClientDto
```

### Flujo de Datos entre Capas

```
🌐 PRESENTACIÓN → 🧠 NEGOCIO → 💾 DATOS → 🔧 INFRAESTRUCTURA → 🗄️ PERSISTENCIA

1. 🌐 Controller recibe HTTP Request
2. 🌐 Controller valida input con DTOs
3. 🌐 Controller delega a Service
4. 🧠 Service ejecuta lógica de negocio
5. 🧠 Service valida reglas de negocio
6. 🧠 Service delega a Repository
7. 💾 Repository ejecuta queries JPA
8. 💾 Repository mapea resultados a Entities
9. 🧠 Service procesa datos de negocio
10. 🧠 Service usa Factory para crear DTOs
11. 🌐 Controller retorna HTTP Response
12. 🔧 GlobalExceptionHandler maneja errores
13. 🗄️ Flyway maneja migraciones de BD
```

### Patrón de Diseño Implementado

- **Repository Pattern**: Abstracción de acceso a datos
- **Service Layer**: Lógica de negocio y validaciones
- **Factory Pattern**: Creación de objetos complejos
- **DTO Pattern**: Transferencia de datos
- **Exception Handling**: Manejo global de errores

---

## 🛠️ Tecnologías Utilizadas

### Backend
- **Java 17**
- **Spring Boot 3.5.7**
- **Spring Security** - Autenticación y autorización
- **Spring Data JPA** - ORM y repositorios
- **JWT** - Tokens de autenticación
- **Flyway** - Migraciones de base de datos
- **H2 Database** - Base de datos en memoria para pruebas
- **Swagger/OpenAPI** - Documentación de API

### Base de Datos
- **MySQL 8.0+**
- **PostgreSQL 13+**
- **SQL Server 2019+**
- **Docker** - Contenedores de base de datos

### Herramientas de Desarrollo
- **Maven** - Gestión de dependencias
- **Lombok** - Reducción de código boilerplate
- **Validation API** - Validaciones de datos
- **Docker Compose** - Orquestación de contenedores

---

## 🚀 Instalación y Configuración

### Prerrequisitos

- **Java 17** o superior
- **Maven 3.6+**
- **Docker** y **Docker Compose**
- **Git**

### 1. Clonar el Repositorio

```bash
git clone <url-del-repositorio>
cd inventory-system
```

### 2. Configurar Bases de Datos

#### Opción A: Usar Docker Compose (Recomendado)

```bash
# Iniciar todas las bases de datos
docker-compose up -d mysql postgresql sqlserver

# Verificar estado
docker ps
```

#### Opción B: Instalación Manual

Instalar y configurar cada base de datos según la documentación oficial.

### 3. Configurar Perfiles de Spring

Los perfiles disponibles son:
- `mysql` (puerto 8081)
- `postgresql` (puerto 8082)
- `sqlserver` (puerto 8083)

### 4. Ejecutar la Aplicación

```bash
# Para MySQL
mvnw.cmd spring-boot:run -Dspring.profiles.active=mysql

# Para PostgreSQL
mvnw.cmd spring-boot:run -Dspring.profiles.active=postgresql

# Para SQL Server
mvnw.cmd spring-boot:run -Dspring.profiles.active=sqlserver
```

### 5. Verificar Instalación

- **Swagger UI**: http://localhost:{puerto}/swagger-ui.html
- **API Docs**: http://localhost:{puerto}/v3/api-docs
- **H2 Console**: http://localhost:{puerto}/h2-console (solo desarrollo)

---

## 📖 Uso del Sistema

### Autenticación

#### Login de Usuario

```bash
POST /api/users/login
Content-Type: application/json

{
  "username": "admin",
  "password": "password123"
}
```

**Respuesta exitosa:**
```json
{
  "success": true,
  "message": "Login exitoso",
  "data": {
    "token": "eyJhbGciOiJIUzI1NiJ9...",
    "tokenType": "Bearer",
    "userId": 1,
    "username": "admin",
    "email": "admin@example.com",
    "firstName": "Admin",
    "lastName": "User"
  }
}
```

### Gestión de Productos

#### Crear Producto

```bash
POST /api/products
Authorization: Bearer {token}
Content-Type: application/json

{
  "name": "iPhone 15 Pro",
  "description": "Smartphone de última generación",
  "price": 999.99,
  "sku": "IPH15PRO"
}
```

#### Listar Productos

```bash
GET /api/products
Authorization: Bearer {token}
```

### Gestión de Categorías

#### Crear Categoría

```bash
POST /api/categories
Authorization: Bearer {token}
Content-Type: application/json

{
  "name": "Electrónicos",
  "description": "Productos electrónicos y gadgets"
}
```

### Gestión de Pedidos

#### Crear Pedido

```bash
POST /api/orders
Authorization: Bearer {token}
Content-Type: application/json

{
  "clientId": 1,
  "total": 999.99
}
```

---

## 🔗 API Endpoints

### Usuarios (`/api/users`)
- `POST /register` - Registrar nuevo usuario
- `POST /login` - Autenticación
- `GET /{id}` - Obtener usuario por ID
- `GET /` - Listar todos los usuarios
- `PUT /{id}` - Actualizar usuario
- `DELETE /{id}` - Eliminar usuario

### Productos (`/api/products`)
- `POST /` - Crear producto
- `GET /{id}` - Obtener producto por ID
- `GET /` - Listar productos
- `PUT /{id}` - Actualizar producto
- `DELETE /{id}` - Eliminar producto
- `GET /sku/{sku}` - Buscar por SKU
- `GET /search?name={name}` - Buscar por nombre
- `GET /price-range?minPrice={min}&maxPrice={max}` - Buscar por rango de precio

### Categorías (`/api/categories`)
- `POST /` - Crear categoría
- `GET /{id}` - Obtener categoría por ID
- `GET /` - Listar categorías
- `PUT /{id}` - Actualizar categoría
- `DELETE /{id}` - Eliminar categoría
- `GET /name/{name}` - Buscar por nombre

### Proveedores (`/api/suppliers`)
- `POST /` - Crear proveedor
- `GET /{id}` - Obtener proveedor por ID
- `GET /` - Listar proveedores
- `PUT /{id}` - Actualizar proveedor
- `DELETE /{id}` - Eliminar proveedor
- `GET /name/{name}` - Buscar por nombre
- `GET /email/{email}` - Buscar por email

### Clientes (`/api/clients`)
- `POST /` - Crear cliente
- `GET /{id}` - Obtener cliente por ID
- `GET /` - Listar clientes
- `PUT /{id}` - Actualizar cliente
- `DELETE /{id}` - Eliminar cliente
- `GET /email/{email}` - Buscar por email

### Pedidos (`/api/orders`)
- `POST /` - Crear pedido
- `GET /{id}` - Obtener pedido por ID
- `GET /` - Listar pedidos
- `PUT /{id}` - Actualizar pedido
- `DELETE /{id}` - Eliminar pedido
- `GET /client/{clientId}` - Pedidos por cliente
- `GET /status/{status}` - Pedidos por estado
- `GET /date-range?startDate={start}&endDate={end}` - Pedidos por rango de fecha
- `GET /total-range?minTotal={min}&maxTotal={max}` - Pedidos por rango de total

---

## 🗄️ Estructura de Base de Datos

### Diagrama ER

```
┌─────────────┐     ┌─────────────┐     ┌─────────────┐
│   clients   │     │   orders    │     │   products  │
│-------------│     │-------------│     │-------------│
│ id (PK)     │◄────┤ id (PK)     │     │ id (PK)     │
│ name        │     │ client_id   │     │ name        │
│ email       │     │ order_date  │     │ description │
│ phone       │     │ total       │     │ price       │
│ address     │     │ status      │     │ sku         │
└─────────────┘     └─────────────┘     └─────────────┘
                                                │
┌─────────────┐     ┌─────────────┐             │
│ categories  │     │ suppliers   │             │
│-------------│     │-------------│             │
│ id (PK)     │     │ id (PK)     │             │
│ name        │     │ name        │             │
│ description │     │ contact_email│             │
└─────────────┘     │ contact_phone│             │
                    │ address     │             │
                    └─────────────┘             │
                                               │
                    ┌─────────────┐             │
                    │  users      │             │
                    │-------------│             │
                    │ id (PK)     │             │
                    │ username    │             │
                    │ email       │             │
                    │ password    │             │
                    │ first_name  │             │
                    │ last_name   │             │
                    │ phone       │             │
                    │ address     │             │
                    └─────────────┘             │
                                               ▼
                    ┌─────────────────────────────┐
                    │ product_category (N:M)     │
                    │ supplier_product (N:M)     │
                    └─────────────────────────────┘
```

### Migraciones Flyway

Las migraciones están organizadas por base de datos:

```
src/main/resources/db/migration/
├── mysql/
│   ├── V1__Initial_schema_mysql.sql
│   └── V2__Insert_data.sql
├── postgresql/
│   ├── V1__Initial_schema_postgresql.sql
│   └── V2__Insert_data.sql
└── sqlserver/
    ├── V1__Initial_schema_sqlserver.sql
    └── V2__Insert_data.sql
```

---

## 🔐 Seguridad

### Autenticación JWT

- **Token-based authentication** con JWT
- **Password encryption** usando BCrypt
- **Role-based access control** (preparado para futuras implementaciones)
- **Token expiration** configurable

### Validaciones

- **Bean Validation** en todas las entidades y DTOs
- **Custom validators** para lógica de negocio específica
- **Input sanitization** para prevenir ataques XSS
- **SQL Injection prevention** usando JPA/Hibernate

### Manejo de Excepciones

- **Global exception handler** para respuestas consistentes
- **Custom exceptions** para diferentes tipos de error
- **Structured error responses** con códigos y mensajes
- **Logging** de errores para debugging

---

## 🧪 Pruebas

### Ejecutar Pruebas

```bash
# Ejecutar todas las pruebas
mvnw.cmd test

# Ejecutar con cobertura
mvnw.cmd test jacoco:report
```

### Tipos de Pruebas

- **Unit Tests**: Servicios y utilidades
- **Integration Tests**: Repositorios y controladores
- **API Tests**: Endpoints REST

---

## 📊 Monitoreo y Logs

### Configuración de Logs

```yaml
logging:
  level:
    com.sena.inventorysystem: DEBUG
    org.springframework.security: DEBUG
    org.flywaydb: DEBUG
```

### Métricas Disponibles

- **Health checks**: `/actuator/health`
- **Application info**: `/actuator/info`
- **Metrics**: `/actuator/metrics`
- **Environment**: `/actuator/env`

---

## 🚀 Despliegue

### Variables de Entorno

```bash
# Base de datos
DB_URL=jdbc:mysql://localhost:3307/inventory_db
DB_USER=inventory_user
DB_PASSWORD=inventory_pass

# JWT
JWT_SECRET=your-secret-key-here
JWT_EXPIRATION=86400000

# Aplicación
SPRING_PROFILES_ACTIVE=mysql
SERVER_PORT=8081
```

### Docker Deployment

```bash
# Construir imagen
docker build -t inventory-system .

# Ejecutar contenedor
docker run -p 8081:8081 \
  -e SPRING_PROFILES_ACTIVE=mysql \
  -e DB_URL=jdbc:mysql://host.docker.internal:3307/inventory_db \
  inventory-system
```

---

## 🤝 Contribución

### Estándares de Código

1. **Java Code Conventions**
2. **SOLID Principles**
3. **Clean Architecture**
4. **TDD/BDD** approach
5. **Git Flow** workflow

### Pull Request Process

1. Fork el proyecto
2. Crear rama feature (`git checkout -b feature/AmazingFeature`)
3. Commit cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abrir Pull Request

---

## 📝 Licencia

Este proyecto está bajo la Licencia MIT - ver el archivo [LICENSE](LICENSE) para más detalles.

---

## 📞 Soporte

Para soporte técnico o preguntas:

- **Email**: soporte@worksena.com
- **Issues**: [GitHub Issues](https://github.com/worksena/inventory-system/issues)
- **Wiki**: [Documentación Completa](https://github.com/worksena/inventory-system/wiki)

---

## 🎉 Conclusión

WorkSena Inventory System es una solución completa y robusta para la gestión de inventario que combina las mejores prácticas de desarrollo con una arquitectura escalable y mantenible. El sistema está diseñado para crecer con las necesidades del negocio, soportando múltiples bases de datos y proporcionando una API RESTful completa con documentación automática.

**¡Gracias por usar WorkSena Inventory System!** 🚀