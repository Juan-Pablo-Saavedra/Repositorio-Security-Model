# Módulo de Autenticación (auth)

## Descripción
Este módulo se encarga de la autenticación de usuarios en el sistema Inventory. Proporciona funcionalidades de login y gestión de sesiones alineadas con el backend KiloIA.

## Estructura
```
auth/
├── auth.module.ts           # Módulo principal
├── auth-routing.module.ts   # Rutas del módulo
├── login/                   # Componentes de login
│   └── login.component.ts   # Componente de login
└── README.md               # Este archivo
```

## Funcionalidades
- **LoginComponent**: Componente principal para autenticación de usuarios
- **Diseño moderno**: Interfaz con degradado y estilo profesional
- **Validación de formularios**: Validación en tiempo real
- **Gestión de estados**: Loading states para mejor UX
- **Alineación con Backend**: Compatible con endpoints KiloIA

## Endpoints Backend (KiloIA)
- `POST /api/users/login` - Autenticación de usuario
- `GET /api/users` - Obtener información del usuario
- `POST /api/users/register` - Registro de nuevo usuario

## Uso
```typescript
// Importar el módulo en app.module.ts
import { AuthModule } from './auth/auth.module';

@NgModule({
  imports: [
    // ... otros módulos
    AuthModule
  ]
})
export class AppModule { }
```

## Rutas
- `/auth/login` - Página de login
- `/auth` - Redirección a login (por defecto)

## Estilos
- **Colores**: Degradado azul-morado (#667eea a #764ba2)
- **Tipografía**: Moderna y legible
- **Responsive**: Adaptable a diferentes dispositivos
- **Animaciones**: Transiciones suaves y efectos hover

## Próximos Pasos
- Implementar servicio de autenticación real
- Agregar registro de usuarios
- Integrar con JWT del backend
- Agregar recuperación de contraseña
- Implementar guards de autenticación