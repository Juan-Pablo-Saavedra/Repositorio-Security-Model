import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatTableModule } from '@angular/material/table';
import { MatChipsModule } from '@angular/material/chips';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { ProductService } from '../../shared/services/product.service';
import { CategoryService } from '../../shared/services/category.service';
import { SupplierService } from '../../shared/services/supplier.service';
import { Product } from '../../shared/models/inventory.model';
import { Category } from '../../shared/models/inventory.model';
import { Supplier } from '../../shared/models/inventory.model';

interface InventoryStats {
  totalProducts: number;
  totalCategories: number;
  totalSuppliers: number;
  lowStockProducts: number;
  recentProducts: Product[];
  categories: Category[];
  suppliers: Supplier[];
}

@Component({
  selector: 'app-inventory',
  standalone: true,
  imports: [
    CommonModule,
    RouterModule,
    MatCardModule,
    MatButtonModule,
    MatIconModule,
    MatGridListModule,
    MatTableModule,
    MatChipsModule,
    MatProgressSpinnerModule,
    MatSnackBarModule
  ],
  template: `
    <div class="inventory-container">
      <!-- Header -->
      <div class="page-header">
        <div>
          <h1 class="page-title">Vista General del Inventario</h1>
          <p class="page-subtitle">Resumen completo de tu catálogo de productos</p>
        </div>
        <div class="header-actions">
          <button mat-raised-button color="primary" routerLink="/products/create">
            <mat-icon>add</mat-icon>
            Nuevo Producto
          </button>
          <button mat-stroked-button routerLink="/categories/create">
            <mat-icon>add</mat-icon>
            Nueva Categoría
          </button>
        </div>
      </div>

      <!-- Loading Spinner -->
      <div *ngIf="isLoading" class="loading-container">
        <mat-spinner diameter="50"></mat-spinner>
        <p>Cargando inventario...</p>
      </div>

      <!-- Stats Cards -->
      <div *ngIf="!isLoading" class="stats-grid">
        <mat-card class="stat-card products-card">
          <mat-card-content>
            <div class="stat-content">
              <div class="stat-icon">
                <mat-icon>inventory_2</mat-icon>
              </div>
              <div class="stat-info">
                <h3>{{ stats.totalProducts }}</h3>
                <p>Productos Totales</p>
              </div>
            </div>
          </mat-card-content>
        </mat-card>

        <mat-card class="stat-card categories-card">
          <mat-card-content>
            <div class="stat-content">
              <div class="stat-icon">
                <mat-icon>label</mat-icon>
              </div>
              <div class="stat-info">
                <h3>{{ stats.totalCategories }}</h3>
                <p>Categorías</p>
              </div>
            </div>
          </mat-card-content>
        </mat-card>

        <mat-card class="stat-card suppliers-card">
          <mat-card-content>
            <div class="stat-content">
              <div class="stat-icon">
                <mat-icon>local_shipping</mat-icon>
              </div>
              <div class="stat-info">
                <h3>{{ stats.totalSuppliers }}</h3>
                <p>Proveedores</p>
              </div>
            </div>
          </mat-card-content>
        </mat-card>

        <mat-card class="stat-card low-stock-card" [class.warning]="stats.lowStockProducts > 0">
          <mat-card-content>
            <div class="stat-content">
              <div class="stat-icon">
                <mat-icon>{{ stats.lowStockProducts > 0 ? 'warning' : 'check_circle' }}</mat-icon>
              </div>
              <div class="stat-info">
                <h3>{{ stats.lowStockProducts }}</h3>
                <p>Productos con Stock Bajo</p>
              </div>
            </div>
          </mat-card-content>
        </mat-card>
      </div>

      <!-- Content Grid -->
      <div *ngIf="!isLoading" class="content-grid">
        <!-- Recent Products -->
        <mat-card class="content-card">
          <mat-card-header>
            <mat-card-title>Productos Recientes</mat-card-title>
            <mat-card-subtitle>Últimos productos agregados al inventario</mat-card-subtitle>
          </mat-card-header>
          <mat-card-content>
            <div class="table-container" *ngIf="stats.recentProducts.length > 0">
              <table mat-table [dataSource]="stats.recentProducts.slice(0, 5)" class="products-table">
                <ng-container matColumnDef="name">
                  <th mat-header-cell *matHeaderCellDef>Producto</th>
                  <td mat-cell *matCellDef="let product">
                    <div class="product-cell">
                      <div class="product-avatar">{{ product.name.charAt(0) }}</div>
                      <span>{{ product.name }}</span>
                    </div>
                  </td>
                </ng-container>

                <ng-container matColumnDef="sku">
                  <th mat-header-cell *matHeaderCellDef>SKU</th>
                  <td mat-cell *matCellDef="let product">
                    <span class="sku-chip">{{ product.sku }}</span>
                  </td>
                </ng-container>

                <ng-container matColumnDef="price">
                  <th mat-header-cell *matHeaderCellDef>Precio</th>
                  <td mat-cell *matCellDef="let product">
                    {{ product.price | currency:'USD':'symbol':'1.2-2' }}
                  </td>
                </ng-container>

                <ng-container matColumnDef="actions">
                  <th mat-header-cell *matHeaderCellDef>Acciones</th>
                  <td mat-cell *matCellDef="let product">
                    <button mat-icon-button [routerLink]="['/products/edit', product.id]" matTooltip="Editar">
                      <mat-icon>edit</mat-icon>
                    </button>
                  </td>
                </ng-container>

                <tr mat-header-row *matHeaderRowDef="recentProductsColumns"></tr>
                <tr mat-row *matRowDef="let row; columns: recentProductsColumns;"></tr>
              </table>
            </div>
            <div *ngIf="stats.recentProducts.length === 0" class="empty-state">
              <mat-icon>inventory_2</mat-icon>
              <p>No hay productos registrados</p>
              <button mat-stroked-button routerLink="/products/create">
                <mat-icon>add</mat-icon>
                Crear Primer Producto
              </button>
            </div>
          </mat-card-content>
          <mat-card-actions *ngIf="stats.recentProducts.length > 0">
            <button mat-button routerLink="/products">Ver Todos los Productos</button>
          </mat-card-actions>
        </mat-card>

        <!-- Categories Overview -->
        <mat-card class="content-card">
          <mat-card-header>
            <mat-card-title>Categorías</mat-card-title>
            <mat-card-subtitle>Organización de productos por categorías</mat-card-subtitle>
          </mat-card-header>
          <mat-card-content>
            <div class="categories-grid" *ngIf="stats.categories.length > 0">
              <mat-chip-listbox>
                <mat-chip-option
                  *ngFor="let category of stats.categories.slice(0, 8)"
                  [routerLink]="['/categories']"
                  class="category-chip">
                  <mat-icon>label</mat-icon>
                  {{ category.name }}
                </mat-chip-option>
              </mat-chip-listbox>
            </div>
            <div *ngIf="stats.categories.length === 0" class="empty-state">
              <mat-icon>label</mat-icon>
              <p>No hay categorías registradas</p>
              <button mat-stroked-button routerLink="/categories/create">
                <mat-icon>add</mat-icon>
                Crear Primera Categoría
              </button>
            </div>
          </mat-card-content>
          <mat-card-actions *ngIf="stats.categories.length > 0">
            <button mat-button routerLink="/categories">Ver Todas las Categorías</button>
          </mat-card-actions>
        </mat-card>

        <!-- Suppliers Overview -->
        <mat-card class="content-card">
          <mat-card-header>
            <mat-card-title>Proveedores</mat-card-title>
            <mat-card-subtitle>Gestión de proveedores y contactos</mat-card-subtitle>
          </mat-card-header>
          <mat-card-content>
            <div class="suppliers-list" *ngIf="stats.suppliers.length > 0">
              <div class="supplier-item" *ngFor="let supplier of stats.suppliers.slice(0, 5)">
                <div class="supplier-info">
                  <div class="supplier-avatar">{{ supplier.name.charAt(0) }}</div>
                  <div>
                    <h4>{{ supplier.name }}</h4>
                    <p>{{ supplier.contactEmail }}</p>
                  </div>
                </div>
                <button mat-icon-button [routerLink]="['/suppliers/edit', supplier.id]" matTooltip="Editar">
                  <mat-icon>edit</mat-icon>
                </button>
              </div>
            </div>
            <div *ngIf="stats.suppliers.length === 0" class="empty-state">
              <mat-icon>local_shipping</mat-icon>
              <p>No hay proveedores registrados</p>
              <button mat-stroked-button routerLink="/suppliers/create">
                <mat-icon>add</mat-icon>
                Crear Primer Proveedor
              </button>
            </div>
          </mat-card-content>
          <mat-card-actions *ngIf="stats.suppliers.length > 0">
            <button mat-button routerLink="/suppliers">Ver Todos los Proveedores</button>
          </mat-card-actions>
        </mat-card>
      </div>
    </div>
  `,
  styles: [`
    .inventory-container {
      padding: 0;
      animation: fadeIn 0.3s ease-out;
    }

    @keyframes fadeIn {
      from { opacity: 0; transform: translateY(20px); }
      to { opacity: 1; transform: translateY(0); }
    }

    .page-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-bottom: 2rem;
      flex-wrap: wrap;
      gap: 1rem;
    }

    .page-title {
      font-size: 2rem;
      font-weight: 700;
      margin: 0;
      color: var(--text-primary);
    }

    .page-subtitle {
      font-size: 0.875rem;
      color: var(--text-secondary);
      margin: 0.5rem 0 0 0;
    }

    .header-actions {
      display: flex;
      gap: 1rem;
      flex-wrap: wrap;
    }

    .loading-container {
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: center;
      padding: 4rem 2rem;
      text-align: center;
    }

    .loading-container p {
      margin-top: 1rem;
      color: var(--text-secondary);
    }

    .stats-grid {
      display: grid;
      grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
      gap: 1.5rem;
      margin-bottom: 2rem;
    }

    .stat-card {
      border-radius: 12px !important;
      transition: transform 0.2s ease, box-shadow 0.2s ease;
    }

    .stat-card:hover {
      transform: translateY(-2px);
      box-shadow: var(--shadow-lg) !important;
    }

    .stat-content {
      display: flex;
      align-items: center;
      gap: 1rem;
      padding: 1rem 0 !important;
    }

    .stat-icon {
      width: 60px;
      height: 60px;
      border-radius: 12px;
      display: flex;
      align-items: center;
      justify-content: center;
      font-size: 24px;
    }

    .products-card .stat-icon {
      background: linear-gradient(135deg, #667eea, #764ba2);
      color: white;
    }

    .categories-card .stat-icon {
      background: linear-gradient(135deg, #f093fb, #f5576c);
      color: white;
    }

    .suppliers-card .stat-icon {
      background: linear-gradient(135deg, #4facfe, #00f2fe);
      color: white;
    }

    .low-stock-card .stat-icon {
      background: linear-gradient(135deg, #ffecd2, #fcb69f);
      color: #e65100;
    }

    .low-stock-card.warning .stat-icon {
      background: linear-gradient(135deg, #ff9a9e, #fecfef);
      color: #d32f2f;
    }

    .stat-info h3 {
      font-size: 2rem;
      font-weight: 700;
      margin: 0;
      color: var(--text-primary);
    }

    .stat-info p {
      font-size: 0.875rem;
      color: var(--text-secondary);
      margin: 0.25rem 0 0 0;
    }

    .content-grid {
      display: grid;
      grid-template-columns: repeat(auto-fit, minmax(400px, 1fr));
      gap: 1.5rem;
    }

    .content-card {
      border-radius: 12px !important;
      box-shadow: var(--shadow-sm) !important;
    }

    .table-container {
      overflow-x: auto;
    }

    .products-table {
      width: 100%;
      background: var(--surface-primary);
    }

    .product-cell {
      display: flex;
      align-items: center;
      gap: 0.75rem;
    }

    .product-avatar {
      width: 32px;
      height: 32px;
      border-radius: 6px;
      background: linear-gradient(135deg, #667eea, #764ba2);
      color: white;
      display: flex;
      align-items: center;
      justify-content: center;
      font-weight: 600;
      font-size: 0.75rem;
    }

    .sku-chip {
      background: var(--surface-secondary);
      padding: 0.25rem 0.5rem;
      border-radius: 4px;
      font-family: var(--font-mono);
      font-size: 0.75rem;
      color: var(--text-secondary);
    }

    .categories-grid {
      display: flex;
      flex-wrap: wrap;
      gap: 0.5rem;
    }

    .category-chip {
      background: var(--color-primary-100) !important;
      color: var(--color-primary-700) !important;
      border-radius: 20px !important;
      transition: all 0.2s ease;
    }

    .category-chip:hover {
      background: var(--color-primary-200) !important;
      transform: translateY(-1px);
    }

    .suppliers-list {
      display: flex;
      flex-direction: column;
      gap: 1rem;
    }

    .supplier-item {
      display: flex;
      align-items: center;
      justify-content: space-between;
      padding: 1rem;
      border: 1px solid var(--border-color);
      border-radius: 8px;
      transition: all 0.2s ease;
    }

    .supplier-item:hover {
      border-color: var(--color-primary);
      background: var(--surface-hover);
    }

    .supplier-info {
      display: flex;
      align-items: center;
      gap: 1rem;
    }

    .supplier-avatar {
      width: 40px;
      height: 40px;
      border-radius: 8px;
      background: linear-gradient(135deg, #4facfe, #00f2fe);
      color: white;
      display: flex;
      align-items: center;
      justify-content: center;
      font-weight: 600;
    }

    .supplier-info h4 {
      margin: 0;
      font-size: 1rem;
      font-weight: 600;
      color: var(--text-primary);
    }

    .supplier-info p {
      margin: 0.25rem 0 0 0;
      font-size: 0.875rem;
      color: var(--text-secondary);
    }

    .empty-state {
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: center;
      padding: 3rem 2rem;
      text-align: center;
    }

    .empty-state mat-icon {
      font-size: 48px;
      width: 48px;
      height: 48px;
      color: var(--text-placeholder);
      margin-bottom: 1rem;
    }

    .empty-state p {
      color: var(--text-secondary);
      margin: 0 0 1.5rem 0;
    }

    mat-card-actions {
      padding: 1rem !important;
      margin: 0 !important;
    }

    @media (max-width: 768px) {
      .page-header {
        flex-direction: column;
        align-items: flex-start;
      }

      .header-actions {
        width: 100%;
        justify-content: center;
      }

      .stats-grid {
        grid-template-columns: 1fr;
      }

      .content-grid {
        grid-template-columns: 1fr;
      }

      .supplier-item {
        flex-direction: column;
        align-items: flex-start;
        gap: 0.75rem;
      }
    }
  `]
})
export class InventoryComponent implements OnInit {
  stats: InventoryStats = {
    totalProducts: 0,
    totalCategories: 0,
    totalSuppliers: 0,
    lowStockProducts: 0,
    recentProducts: [],
    categories: [],
    suppliers: []
  };

  isLoading = true;
  recentProductsColumns: string[] = ['name', 'sku', 'price', 'actions'];

  constructor(
    private productService: ProductService,
    private categoryService: CategoryService,
    private supplierService: SupplierService,
    private route: ActivatedRoute,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.loadInventoryData();

    // Handle search query parameter
    this.route.queryParams.subscribe(params => {
      if (params['search']) {
        this.performSearch(params['search']);
      }
    });
  }

  private loadInventoryData(): void {
    this.isLoading = true;

    // Load all data in parallel
    Promise.all([
      this.loadProducts(),
      this.loadCategories(),
      this.loadSuppliers()
    ]).then(() => {
      this.isLoading = false;
    }).catch(error => {
      console.error('Error loading inventory data:', error);
      this.snackBar.open('Error al cargar los datos del inventario', 'Cerrar', { duration: 3000 });
      this.isLoading = false;
      this.loadMockData();
    });
  }

  private loadProducts(): Promise<void> {
    return new Promise((resolve) => {
      this.productService.getAll().subscribe({
        next: (response: any) => {
          const products = response.data || response || [];
          this.stats.totalProducts = products.length;
          this.stats.recentProducts = products.slice(0, 10);
          // Simulate low stock check (in a real app, this would be based on stock levels)
          this.stats.lowStockProducts = Math.floor(products.length * 0.1);
          resolve();
        },
        error: () => {
          // Load mock data if API fails
          this.stats.totalProducts = 22;
          this.stats.recentProducts = this.getMockProducts();
          this.stats.lowStockProducts = 2;
          resolve();
        }
      });
    });
  }

  private loadCategories(): Promise<void> {
    return new Promise((resolve) => {
      this.categoryService.getAll().subscribe({
        next: (response: any) => {
          const categories = response.data || response || [];
          this.stats.totalCategories = categories.length;
          this.stats.categories = categories;
          resolve();
        },
        error: () => {
          this.stats.totalCategories = 10;
          this.stats.categories = this.getMockCategories();
          resolve();
        }
      });
    });
  }

  private loadSuppliers(): Promise<void> {
    return new Promise((resolve) => {
      this.supplierService.getAll().subscribe({
        next: (response: any) => {
          const suppliers = response.data || response || [];
          this.stats.totalSuppliers = suppliers.length;
          this.stats.suppliers = suppliers;
          resolve();
        },
        error: () => {
          this.stats.totalSuppliers = 10;
          this.stats.suppliers = this.getMockSuppliers();
          resolve();
        }
      });
    });
  }

  private performSearch(query: string): void {
    // In a real implementation, this would filter the data or navigate to search results
    this.snackBar.open(`Buscando: "${query}"`, 'Cerrar', { duration: 2000 });
  }

  private loadMockData(): void {
    this.stats = {
      totalProducts: 22,
      totalCategories: 10,
      totalSuppliers: 10,
      lowStockProducts: 2,
      recentProducts: this.getMockProducts(),
      categories: this.getMockCategories(),
      suppliers: this.getMockSuppliers()
    };
  }

  private getMockProducts(): Product[] {
    return [
      {
        id: 1,
        name: 'Laptop Dell Inspiron 15',
        sku: 'LAP-DELL-001',
        description: 'Laptop con procesador Intel i5, 8GB RAM',
        price: 899.99,
        categoryId: 1,
        supplierId: 1,
        createdAt: new Date(),
        updatedAt: new Date()
      },
      {
        id: 2,
        name: 'Mouse Logitech MX Master 3',
        sku: 'MOU-LOG-002',
        description: 'Mouse inalámbrico ergonómico',
        price: 99.99,
        categoryId: 2,
        supplierId: 2,
        createdAt: new Date(),
        updatedAt: new Date()
      },
      {
        id: 3,
        name: 'Teclado Mecánico Keychron K2',
        sku: 'KEY-KEY-003',
        description: 'Teclado mecánico RGB con switches rojos',
        price: 149.99,
        categoryId: 2,
        supplierId: 1,
        createdAt: new Date(),
        updatedAt: new Date()
      }
    ];
  }

  private getMockCategories(): Category[] {
    return [
      { id: 1, name: 'Computadoras', description: 'Laptops, desktops y accesorios' },
      { id: 2, name: 'Periféricos', description: 'Mouse, teclados y otros dispositivos' },
      { id: 3, name: 'Almacenamiento', description: 'Discos duros, SSD y memorias' },
      { id: 4, name: 'Monitores', description: 'Pantallas y displays' },
      { id: 5, name: 'Redes', description: 'Routers, switches y accesorios de red' }
    ];
  }

  private getMockSuppliers(): Supplier[] {
    return [
      { id: 1, name: 'TechCorp', contactEmail: 'ventas@techcorp.com', contactPhone: '+1234567890', address: '123 Tech St' },
      { id: 2, name: 'ElectroWorld', contactEmail: 'info@electroworld.com', contactPhone: '+0987654321', address: '456 Electro Ave' },
      { id: 3, name: 'GadgetHub', contactEmail: 'support@gadgethub.com', contactPhone: '+1122334455', address: '789 Gadget Blvd' }
    ];
  }
}