# Sistema de Gestión de Inventarios - Frontend

## Descripción
Frontend completo desarrollado con Angular 17 para gestión de inventarios, integrado con backend Spring Boot.

## Características Principales

### 🏗️ Arquitectura
- **Angular 17** con arquitectura modular escalable
- **Lazy loading** para optimización de rendimiento
- **TypeScript** strict mode
- **Angular Material** para diseño responsivo

### 🔐 Autenticación y Seguridad
- Login/logout con JWT
- Interceptor HTTP automático para tokens
- Guards de rutas para protección
- Gestión de estado de sesión
- Manejo de expiración de tokens

### 📦 Gestión de Inventario
- **Productos:** CRUD completo con búsqueda por SKU y nombre
- **Categorías:** Organización de productos por categorías
- **Proveedores:** Gestión de proveedores
- **Clientes y Órdenes:** Sistema completo de ventas

### 🎨 Interfaz de Usuario
- Diseño moderno y responsivo
- Dashboard administrativo
- Navegación lateral con menu
- Formularios con validaciones
- Notificaciones de usuario
- Tables con paginación y filtros

### 🔌 Integración Backend
- Consumir APIs REST del backend
- Manejo de errores HTTP
- Configuración de environment
- Respuestas tipadas con TypeScript

## Estructura del Proyecto

```
src/
├── app/
│   ├── auth/                 # Módulo de autenticación
│   │   ├── components/       # Componentes de login/registro
│   │   ├── auth.module.ts    # Módulo de auth
│   │   └── auth-routing.module.ts
│   ├── shared/               # Módulos compartidos
│   │   ├── models/           # Modelos de datos
│   │   ├── services/         # Servicios HTTP
│   │   ├── guards/           # Guards de rutas
│   │   ├── interceptors/     # Interceptores HTTP
│   │   └── shared.module.ts  # Módulo shared
│   ├── components/           # Componentes principales
│   │   ├── dashboard/        # Dashboard principal
│   │   ├── products/         # Gestión de productos
│   │   ├── categories/       # Gestión de categorías
│   │   └── suppliers/        # Gestión de proveedores
│   ├── app.component.ts      # Componente raíz
│   ├── app.config.ts         # Configuración de la app
│   └── app.routes.ts         # Rutas principales
├── environments/             # Configuraciones de entorno
└── assets/                   # Recursos estáticos
```

## Configuración

### Dependencias Principales
- @angular/material 17.3.0
- @angular/cdk 17.3.0
- @angular/animations 17.3.0
- rxjs para manejo de observables

### Variables de Entorno
```typescript
export const environment = {
  production: false,
  apiUrl: 'http://localhost:8081/api',
  jwtTokenKey: 'auth_token',
  userDataKey: 'user_data'
};
```

## Comandos de Desarrollo

```bash
# Instalar dependencias
npm install

# Ejecutar en desarrollo
npm start

# Build para producción
npm run build

# Ejecutar tests
npm test
```

## Características de Seguridad

1. **JWT Authentication**
   - Tokens almacenados en localStorage
   - Interceptor automático para agregar headers
   - Logout automático en caso de token expirado

2. **Protección de Rutas**
   - Guards de autenticación
   - Redirección automática a login
   - Navegación condicional

3. **Manejo de Errores**
   - Interceptor global de errores HTTP
   - Notificaciones de usuario
   - Logging de errores

## Funcionalidades Implementadas

### ✅ Autenticación
- [x] Login con credenciales
- [x] Logout con limpieza de sesión
- [x] Protección de rutas
- [x] Gestión de estado de usuario
- [x] Interceptores HTTP

### ✅ Dashboard
- [x] Interfaz principal
- [x] Navegación lateral
- [x] Menu responsivo
- [x] Datos de usuario

### ✅ Gestión de Inventario
- [x] Modelos de datos completos
- [x] Servicios HTTP para CRUD
- [x] Validaciones de formularios
- [x] Búsqueda y filtrado

### ✅ Interfaz de Usuario
- [x] Angular Material components
- [x] Diseño responsivo
- [x] Notificaciones
- [x] Formularios con validaciones

## Estado del Desarrollo

### Completado ✅
- [x] Configuración base de Angular 17
- [x] Instalación de dependencias (Angular Material)
- [x] Modelos de datos (User, Product, Category, Supplier, etc.)
- [x] Servicios base (ApiService, AuthService)
- [x] Servicios específicos (ProductService, CategoryService, SupplierService)
- [x] Interceptor de autenticación
- [x] Guard de rutas
- [x] Configuración de la aplicación
- [x] AppComponent principal con navegación
- [x] Componente de login
- [x] Estructura modular básica
- [x] Rutas principales con lazy loading

### Pendiente 🔄
- [ ] Componentes de productos, categorías, proveedores
- [ ] Dashboard con estadísticas
- [ ] Formularios de creación/edición
- [ ] Tablas con paginación
- [ ] Módulo de órdenes y clientes
- [ ] Validaciones avanzadas
- [ ] Testing unitario
- [ ] Documentación completa

## Integración con Backend

El frontend está configurado para integrar con el backend Spring Boot ubicado en `Backend/security/`:

### APIs Configuradas
- **Users:** `POST /api/users/login`, `POST /api/users/register`, `GET /api/users/*`
- **Products:** `GET /api/products`, `POST /api/products`, `PUT /api/products/*`, `DELETE /api/products/*`
- **Categories:** `GET /api/categories`, `POST /api/categories`, `PUT /api/categories/*`, `DELETE /api/categories/*`
- **Suppliers:** `GET /api/suppliers`, `POST /api/suppliers`, `PUT /api/suppliers/*`, `DELETE /api/suppliers/*`

### Configuración CORS
El backend debe configurarse para permitir requests desde el frontend:

```java
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
```

## Próximos Pasos

1. **Completar Componentes:** Crear los componentes faltantes (Products, Categories, Suppliers, Dashboard)
2. **Implementar Formularios:** Crear formularios de creación y edición
3. **Tablas Avanzadas:** Implementar tablas con paginación, filtros y ordenamiento
4. **Testing:** Agregar tests unitarios e integración
5. **Optimización:** Lazy loading completo, preloading de módulos
6. **Documentación:** Documentar todos los componentes y servicios

## Conclusión

El sistema frontend está estructurado profesionalmente siguiendo las mejores prácticas de Angular 17. La arquitectura modular permite escalabilidad y mantenimiento fácil. La integración con el backend está preparada y la seguridad implementada con JWT y guards asegura que solo usuarios autenticados accedan a las funcionalidades protegidas.

El sistema está listo para recibir los componentes adicionales y convertirse en una aplicación completa de gestión de inventarios.
