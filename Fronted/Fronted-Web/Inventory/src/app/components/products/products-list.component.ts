import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatCardModule } from '@angular/material/card';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { ProductService } from '../../shared/services/product.service';
import { Product } from '../../shared/models/inventory.model';

@Component({
  selector: 'app-products-list',
  standalone: true,
  imports: [
    CommonModule,
    RouterModule,
    MatTableModule,
    MatButtonModule,
    MatIconModule,
    MatCardModule,
    MatTooltipModule,
    MatSnackBarModule
  ],
  template: `
    <div class="products-container">
      <!-- Header -->
      <div class="page-header">
        <div>
          <h1 class="page-title">Gestión de Productos</h1>
          <p class="page-subtitle">Administra tu catálogo de productos</p>
        </div>
        <button mat-raised-button color="primary" routerLink="/products/create">
          <mat-icon>add</mat-icon>
          Nuevo Producto
        </button>
      </div>

      <!-- Tabla de Productos -->
      <mat-card class="table-card">
        <div class="table-container">
          <table mat-table [dataSource]="products" class="full-width">
            
            <!-- Columna ID -->
            <ng-container matColumnDef="id">
              <th mat-header-cell *matHeaderCellDef> 
                <span class="header-text">ID</span>
              </th>
              <td mat-cell *matCellDef="let product">
                <span class="id-badge">{{product.id}}</span>
              </td>
            </ng-container>

            <!-- Columna Nombre -->
            <ng-container matColumnDef="name">
              <th mat-header-cell *matHeaderCellDef> 
                <span class="header-text">Nombre</span>
              </th>
              <td mat-cell *matCellDef="let product">
                <div class="product-name-cell">
                  <div class="product-avatar">
                    {{product.name.charAt(0)}}
                  </div>
                  <span class="product-name">{{product.name}}</span>
                </div>
              </td>
            </ng-container>

            <!-- Columna SKU -->
            <ng-container matColumnDef="sku">
              <th mat-header-cell *matHeaderCellDef> 
                <span class="header-text">SKU</span>
              </th>
              <td mat-cell *matCellDef="let product">
                <span class="sku-text">{{product.sku}}</span>
              </td>
            </ng-container>

            <!-- Columna Precio -->
            <ng-container matColumnDef="price">
              <th mat-header-cell *matHeaderCellDef> 
                <span class="header-text">Precio</span>
              </th>
              <td mat-cell *matCellDef="let product">
                <span class="price-text">{{product.price | currency:'USD':'symbol':'1.2-2'}}</span>
              </td>
            </ng-container>

            <!-- Columna Descripción -->
            <ng-container matColumnDef="description">
              <th mat-header-cell *matHeaderCellDef> 
                <span class="header-text">Descripción</span>
              </th>
              <td mat-cell *matCellDef="let product">
                <span class="description-text">{{product.description || 'Sin descripción'}}</span>
              </td>
            </ng-container>

            <!-- Columna Acciones -->
            <ng-container matColumnDef="actions">
              <th mat-header-cell *matHeaderCellDef> 
                <span class="header-text">Acciones</span>
              </th>
              <td mat-cell *matCellDef="let product">
                <div class="action-buttons">
                  <button 
                    mat-icon-button 
                    color="primary"
                    [routerLink]="['/products/edit', product.id]"
                    matTooltip="Editar producto">
                    <mat-icon>edit</mat-icon>
                  </button>
                  <button 
                    mat-icon-button 
                    color="warn"
                    (click)="deleteProduct(product.id)"
                    matTooltip="Eliminar producto">
                    <mat-icon>delete</mat-icon>
                  </button>
                </div>
              </td>
            </ng-container>

            <tr mat-header-row *matHeaderRowDef="displayedColumns"></tr>
            <tr mat-row *matRowDef="let row; columns: displayedColumns;" class="table-row"></tr>
          </table>

          <!-- Estado vacío -->
          <div *ngIf="products.length === 0" class="empty-state">
            <mat-icon>inventory_2</mat-icon>
            <h3>No hay productos</h3>
            <p>Comienza agregando tu primer producto</p>
            <button mat-raised-button color="primary" routerLink="/products/create">
              <mat-icon>add</mat-icon>
              Crear Producto
            </button>
          </div>
        </div>
      </mat-card>
    </div>
  `,
  styles: [`
    .products-container {
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

    .table-card {
      border-radius: 12px !important;
      overflow: hidden;
      box-shadow: var(--shadow-sm) !important;
    }

    .table-container {
      overflow-x: auto;
    }

    .full-width {
      width: 100%;
    }

    table {
      background: var(--surface-primary);
    }

    .header-text {
      font-weight: 600;
      font-size: 0.875rem;
      color: var(--text-secondary);
      text-transform: uppercase;
      letter-spacing: 0.5px;
    }

    .table-row {
      transition: background-color 0.2s ease;
    }

    .table-row:hover {
      background: var(--surface-hover);
    }

    .id-badge {
      display: inline-flex;
      align-items: center;
      padding: 0.25rem 0.75rem;
      background: var(--color-primary-100);
      color: var(--color-primary-700);
      border-radius: 12px;
      font-size: 0.75rem;
      font-weight: 600;
    }

    .product-name-cell {
      display: flex;
      align-items: center;
      gap: 0.75rem;
    }

    .product-avatar {
      width: 36px;
      height: 36px;
      border-radius: 8px;
      background: linear-gradient(135deg, #667eea, #764ba2);
      color: white;
      display: flex;
      align-items: center;
      justify-content: center;
      font-weight: 700;
      font-size: 0.875rem;
    }

    .product-name {
      font-weight: 600;
      color: var(--text-primary);
    }

    .sku-text {
      font-family: var(--font-mono);
      font-size: 0.875rem;
      color: var(--text-secondary);
      background: var(--surface-secondary);
      padding: 0.25rem 0.5rem;
      border-radius: 4px;
    }

    .price-text {
      font-weight: 600;
      font-size: 1rem;
      color: var(--color-success);
    }

    .description-text {
      color: var(--text-secondary);
      font-size: 0.875rem;
      max-width: 200px;
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
      display: block;
    }

    .action-buttons {
      display: flex;
      gap: 0.5rem;
    }

    .empty-state {
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: center;
      padding: 4rem 2rem;
      text-align: center;
    }

    .empty-state mat-icon {
      font-size: 64px;
      width: 64px;
      height: 64px;
      color: var(--text-placeholder);
      margin-bottom: 1rem;
    }

    .empty-state h3 {
      font-size: 1.25rem;
      font-weight: 600;
      margin: 0 0 0.5rem 0;
      color: var(--text-primary);
    }

    .empty-state p {
      color: var(--text-secondary);
      margin: 0 0 1.5rem 0;
    }

    @media (max-width: 768px) {
      .page-header {
        flex-direction: column;
        align-items: flex-start;
      }

      .table-container {
        overflow-x: scroll;
      }
    }
  `]
})
export class ProductsListComponent implements OnInit {
  products: Product[] = [];
  displayedColumns: string[] = ['id', 'name', 'sku', 'price', 'description', 'actions'];

  constructor(
    private productService: ProductService,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.loadProducts();
  }

  private loadProducts(): void {
    this.productService.getAll().subscribe({
      next: (response: any) => {
        this.products = response.data || response || [];
      },
      error: (error: any) => {
        console.error('Error loading products:', error);
        this.snackBar.open('Error al cargar productos', 'Cerrar', { duration: 3000 });
        // Datos de prueba
        this.products = [
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
    });
  }

  deleteProduct(id: number): void {
    if (confirm('¿Estás seguro de que deseas eliminar este producto?')) {
      this.productService.deleteProduct(id).subscribe({
        next: () => {
          this.snackBar.open('Producto eliminado exitosamente', 'Cerrar', { duration: 3000 });
          this.loadProducts();
        },
        error: (error: any) => {
          console.error('Error deleting product:', error);
          this.snackBar.open('Error al eliminar el producto', 'Cerrar', { duration: 3000 });
        }
      });
    }
  }
}