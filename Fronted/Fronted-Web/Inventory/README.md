# 📦 Inventory - Sistema de Gestión de Inventario

## 🏗️ **Arquitectura Modular Angular 17**

Este proyecto ha sido completamente refactorizado siguiendo una **arquitectura modular basada en features** con lazy loading para optimizar el rendimiento y mantener una estructura profesional y escalable.

### 📁 **Estructura del Proyecto**

```
src/app/
├── auth/                   # 🔐 MÓDULO DE AUTENTICACIÓN
│   ├── auth.module.ts         # Módulo principal
│   ├── auth-routing.module.ts # Rutas del módulo
│   ├── login/
│   │   └── login.component.ts # Login moderno con diseño degradado
│   ├── register/
│   │   └── register.component.ts # Registro de usuarios
│   └── README.md             # Documentación del módulo
│
├── dashboard/              # 📊 MÓDULO DE DASHBOARD
│   ├── dashboard.module.ts      # Módulo principal
│   ├── dashboard-routing.module.ts # Rutas del módulo
│   ├── components/
│   │   └── dashboard.component.ts # Panel con estadísticas
│   └── services/             # Servicios del dashboard
│
├── products/               # 🛍️ MÓDULO DE PRODUCTOS
│   ├── products.module.ts       # Módulo principal
│   ├── products-routing.module.ts # Rutas del módulo
│   ├── components/
│   │   ├── products-list.component.ts # Lista con filtros y paginación
│   │   └── product-form.component.ts  # Formulario de creación/edición
│   └── services/             # Servicios de productos
│
├── categories/             # 🏷️ MÓDULO DE CATEGORÍAS
│   ├── categories.module.ts      # Módulo principal
│   ├── categories-routing.module.ts # Rutas del módulo
│   ├── components/
│   │   ├── categories-list.component.ts # Lista de categorías
│   │   └── category-form.component.ts  # Formulario de categorías
│   └── services/             # Servicios de categorías
│
├── suppliers/              # 🏭 MÓDULO DE PROVEEDORES
│   ├── suppliers.module.ts      # Módulo principal
│   ├── suppliers-routing.module.ts # Rutas del módulo
│   ├── components/
│   │   ├── suppliers-list.component.ts # Lista de proveedores
│   │   └── supplier-form.component.ts  # Formulario de proveedores
│   └── services/             # Servicios de proveedores
│
├── inventory/              # 📦 MÓDULO DE INVENTARIO
│   ├── inventory.module.ts      # Módulo principal
│   ├── inventory-routing.module.ts # Rutas del módulo
│   ├── components/
│   │   ├── inventory-list.component.ts # Lista de inventario
│   │   └── inventory-form.component.ts  # Formulario de inventario
│   └── services/             # Servicios de inventario
│
├── shared/                 # 🔄 MÓDULO SHARED
│   ├── shared.module.ts         # Módulo con componentes reutilizables
│   ├── components/
│   │   ├── shared-button/      # Botón configurable
│   │   └── shared-card/        # Tarjeta reutilizable
│   ├── models/                 # Interfaces y tipos
│   │   ├── api-response.model.ts # Respuestas de API
│   │   ├── inventory.model.ts   # Modelos de inventario
│   │   └── user.model.ts        # Modelos de usuario
│   └── services/               # Servicios compartidos
│
├── app.component.ts        # Componente raíz
├── app.config.ts           # Configuración de la aplicación
└── app.routes.ts           # Rutas principales con lazy loading
```

## 🚀 **Características Principales**

### ✅ **Arquitectura Modular**
- **Lazy Loading**: Todos los módulos se cargan bajo demanda
- **Rutas Modulares**: Cada feature tiene sus propias rutas
- **Separación de Responsabilidades**: Componentes, servicios, modelos separados
- **TypeScript Strict Mode**: Máxima seguridad de tipos

### ✅ **Componentes Modernos**
- **Diseño Inspirado en Figma**: UI limpia y profesional
- **Formularios Reactivos**: Con validación en tiempo real
- **Diseño Responsivo**: Adaptable a todos los dispositivos
- **Material Design**: Componentes basados en Material Design

### ✅ **Optimización de Rendimiento**
- **Tree Shaking**: Eliminación de código no utilizado
- **Lazy Loading**: Carga bajo demanda de módulos
- **Minificación**: Optimización de bundle size
- **Compresión**: Gzip para transferencia eficiente

## 🛠️ **Tecnologías Utilizadas**

- **Angular 17** - Framework principal
- **TypeScript 5.3** - Tipado estricto
- **SCSS** - Preprocesador CSS
- **Angular Material** - Componentes UI
- **RxJS** - Programación reactiva
- **Angular Router** - Navegación
- **FormsModule** - Manejo de formularios

## 🔧 **Comandos de Desarrollo**

```bash
# Instalar dependencias
npm install

# Servidor de desarrollo
npm run start

# Compilar para producción
npm run build

# Ejecutar pruebas
npm run test

# Análisis de dependencias
npm run analyze
```

## 🔗 **Integración con Backend**

### Endpoints del Backend
- **Base URL**: `http://localhost:8081` (configurable en environment)
- **Autenticación**: `/api/auth/login`
- **Productos**: `/api/products`
- **Categorías**: `/api/categories`
- **Proveedores**: `/api/suppliers`
- **Usuarios**: `/api/users`

### Estructura de API
```typescript
// Respuesta estándar de API
interface ApiResponse<T> {
  success: boolean;
  message: string;
  data: T;
}

// Autenticación
interface AuthResponse {
  token: string;
  user: {
    id: number;
    username: string;
    email: string;
    firstName: string;
    lastName: string;
  };
}
```

## 📱 **Módulos y Funcionalidades**

### 🔐 **Módulo Auth**
- Login con diseño degradado moderno
- Registro de usuarios
- Gestión de sesiones
- Guards de autenticación

### 📊 **Módulo Dashboard**
- Panel de estadísticas
- Métricas en tiempo real
- Gráficos interactivos
- Resumen de inventarios

### 🛍️ **Módulo Products**
- Lista de productos con filtros
- Formulario de creación/edición
- Gestión de categorías
- Control de stock

### 🏷️ **Módulo Categories**
- Gestión de categorías
- Formularios intuitivos
- Vista de estadísticas
- Asignación a productos

### 🏭 **Módulo Suppliers**
- Gestión de proveedores
- Información de contacto
- Historial de transacciones
- Evaluación de rendimiento

### 📦 **Módulo Inventory**
- Control de stock
- Movimientos de inventario
- Alertas de stock bajo
- Reportes de inventario

### 🔄 **Módulo Shared**
- Componentes reutilizables
- Modelos de datos
- Servicios compartidos
- Utilidades comunes

## 🎨 **Sistema de Diseño**

### Colores Principales
- **Primary**: `#667eea` (azul suave)
- **Secondary**: `#764ba2` (morado)
- **Success**: `#10b981` (verde)
- **Warning**: `#f59e0b` (amarillo)
- **Error**: `#ef4444` (rojo)

### Tipografía
- **Font Family**: Roboto
- **Material Icons**: Para iconografía
- **Responsive Typography**: Escalable según dispositivo

### Espaciado
- **Sistema de 8px**: Grid consistente
- **Margins/Paddings**: Estandarizados
- **Breakpoints**: Móvil, tablet, desktop

## 🔒 **Seguridad**

### Autenticación
- JWT Tokens
- Guards de ruta
- Interceptors para headers
- Manejo seguro de tokens

### Validación
- Formularios reactivos
- Validación de frontend
- Validación de backend
- Sanitización de datos

## 📈 **Rendimiento**

### Métricas de Bundle
- **Bundle Size**: ~164KB (gzipped)
- **Lazy Chunks**: Todos los módulos se cargan bajo demanda
- **Tree Shaking**: Optimización automática
- **Compresión**: Gzip habilitado

### Optimizaciones
- **Lazy Loading**: Módulos bajo demanda
- **Change Detection**: OnPush strategy
- **TrackBy**: Optimización de ngFor
- **Async Pipe**: Gestión automática de suscripciones

## 🚀 **Despliegue**

### Build de Producción
```bash
# Compilar para producción
npm run build

# Servir build localmente
npx http-server dist/inventory
```

### Configuración de Ambiente
- `environment.ts` - Desarrollo
- `environment.prod.ts` - Producción
- Variables de entorno configurables

## 📚 **Documentación Adicional**

- [Guía de Módulos](./docs/modules.md)
- [Guía de Servicios](./docs/services.md)
- [Guía de Componentes](./docs/components.md)
- [Guía de Estilo](./docs/style-guide.md)

## 🤝 **Contribución**

1. Fork el proyecto
2. Crear rama feature (`git checkout -b feature/AmazingFeature`)
3. Commit cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a rama (`git push origin feature/AmazingFeature`)
5. Abrir Pull Request

## 📄 **Licencia**

Este proyecto está bajo la Licencia MIT - ver el archivo [LICENSE](LICENSE) para más detalles.

## 🎉 **Conclusión**

El sistema Inventory ha sido completamente refactorizado siguiendo las mejores prácticas de Angular 17, con una arquitectura modular profesional, diseño moderno y optimización de rendimiento. El proyecto está listo para desarrollo y escalamiento.

---

**¡Desarrollado con ❤️ usando Angular 17!**
