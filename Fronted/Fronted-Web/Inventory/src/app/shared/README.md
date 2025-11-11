# Módulo Shared

## Descripción
Este módulo contiene componentes, directivas, pipes y servicios reutilizables que se comparten entre todos los módulos del sistema Inventory.

## Estructura
```
shared/
├── components/              # Componentes reutilizables
│   ├── shared-button/       # Botón configurable
│   └── shared-card/         # Tarjeta configurable
├── directives/              # Directivas personalizadas
├── pipes/                   # Pipes personalizados
├── models/                  # Interfaces y tipos
│   ├── user.model.ts
│   ├── inventory.model.ts
│   └── api-response.model.ts
├── services/                # Servicios compartidos
└── README.md               # Este archivo
```

## Componentes Incluidos

### 🟦 SharedButtonComponent
**Descripción**: Botón reutilizable con múltiples configuraciones

**Propiedades**:
- `label?: string` - Texto del botón
- `icon?: string` - Nombre del icono Material
- `color: ButtonType` - primary, secondary, success, danger, warning, info
- `size: ButtonSize` - small, medium, large
- `disabled: boolean` - Estado deshabilitado

**Eventos**:
- `clicked: EventEmitter` - Emite evento al hacer clic

**Uso**:
```html
<shared-button 
  label="Guardar" 
  icon="save" 
  color="primary" 
  size="medium"
  (clicked)="onSave()">
</shared-button>
```

### 🟩 SharedCardComponent
**Descripción**: Tarjeta reutilizable con diferentes variantes

**Propiedades**:
- `title?: string` - Título de la tarjeta
- `subtitle?: string` - Subtítulo
- `variant: CardVariant` - primary, success, warning, danger, info
- `clickable: boolean` - Si la tarjeta es clickeable
- `hasActions: boolean` - Si contiene acciones

**Características**:
- Animaciones hover suaves
- Bordes colorados por variante
- Contenido transcludable
- Header y acciones opcionales

**Uso**:
```html
<shared-card 
  title="Producto" 
  subtitle="Detalles del producto"
  variant="primary"
  [clickable]="true"
  [hasActions]="true">
  
  <p>Contenido de la tarjeta</p>
  
  <div card-actions>
    <button>Editar</button>
    <button>Eliminar</button>
  </div>
</shared-card>
```

## Módulos Angular Material Incluidos

### Formularios
- `MatFormFieldModule` - Campos de formulario
- `MatInputModule` - Entradas de texto
- `MatSelectModule` - Selectores
- `MatCheckboxModule` - Checkboxes
- `MatDatepickerModule` - Selector de fechas

### Navegación
- `MatToolbarModule` - Barra de herramientas
- `MatSidenavModule` - Panel lateral
- `MatListModule` - Listas
- `MatMenuModule` - Menús
- `MatTabsModule` - Pestañas

### Layout
- `MatCardModule` - Tarjetas
- `MatExpansionModule` - Expansión
- `MatStepperModule` - Pasos
- `MatGridListModule` - Grillas

### Tablas
- `MatTableModule` - Tablas
- `MatPaginatorModule` - Paginación
- `MatSortModule` - Ordenamiento

### Feedback
- `MatButtonModule` - Botones
- `MatIconModule` - Iconos
- `MatProgressSpinnerModule` - Spinners
- `MatSnackBarModule` - Notificaciones

### Interacción
- `MatDialogModule` - Diálogos
- `MatChipsModule` - Chips
- `MatAutocompleteModule` - Autocompletado

## Servicios Incluidos

### Modelos
- `User` - Modelo de usuario
- `Product` - Modelo de producto
- `Category` - Modelo de categoría
- `Supplier` - Modelo de proveedor
- `ApiResponse` - Respuesta estándar de API

## Estilo y Diseño
- **Colores**: Sistema basado en Material Design
- **Tipografía**: Roboto y Material Icons
- **Espaciado**: Sistema de 8px grid
- **Responsive**: Diseño adaptativo
- **Accesibilidad**: Cumple estándares WCAG

## Convenciones
- **Componentes**: Siempre con `shared-` prefix
- **Colores**: Usar constantes de colores definidas
- **Iconos**: Material Icons como estándar
- **Responsive**: Mobile-first approach
- **A11y**: Atributos ARIA apropiados

## Extensibilidad
Para agregar nuevos componentes:
1. Crear en `shared/components/nuevo-componente/`
2. Implementar como standalone component
3. Documentar en este README
4. Exportar desde shared.module.ts si es necesario

## Testing
```bash
# Ejecutar tests del módulo shared
ng test shared
```

## Performance
- **Lazy Loading**: Componentes standalone
- **Tree Shaking**: Solo lo usado se incluye en bundle
- **OnPush**: Estrategia de detección de cambios
- **Reutilización**: Componentes altamente configurables