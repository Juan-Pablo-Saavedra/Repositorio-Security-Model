import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatCardModule } from '@angular/material/card';
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
    MatCardModule
  ],
  template: `
    <div class="products-container">
      <mat-card>
        <mat-card-header>
          <mat-card-title>Gestión de Productos</mat-card-title>
          <div class="header-actions">
            <button mat-raised-button color="primary" routerLink="/products/create">
              <mat-icon>add</mat-icon>
              Nuevo Producto
            </button>
          </div>
        </mat-card-header>
        <mat-card-content>
          <table mat-table [dataSource]="products" class="full-width">
            <ng-container matColumnDef="id">
              <th mat-header-cell *matHeaderCellDef> ID </th>
              <td mat-cell *matCellDef="let product">{{product.id}}</td>
            </ng-container>

            <ng-container matColumnDef="name">
              <th mat-header-cell *matHeaderCellDef> Nombre </th>
              <td mat-cell *matCellDef="let product">{{product.name}}</td>
            </ng-container>

            <ng-container matColumnDef="sku">
              <th mat-header-cell *matHeaderCellDef> SKU </th>
              <td mat-cell *matCellDef="let product">{{product.sku}}</td>
            </ng-container>

            <ng-container matColumnDef="price">
              <th mat-header-cell *matHeaderCellDef> Precio </th>
              <td mat-cell *matCellDef="let product">{{product.price | currency}}</td>
            </ng-container>

            <ng-container matColumnDef="actions">
              <th mat-header-cell *matHeaderCellDef> Acciones </th>
              <td mat-cell *matCellDef="let product">
                <button mat-icon-button color="primary">
                  <mat-icon>edit</mat-icon>
                </button>
                <button mat-icon-button color="warn">
                  <mat-icon>delete</mat-icon>
                </button>
              </td>
            </ng-container>

            <tr mat-header-row *matHeaderRowDef="displayedColumns"></tr>
            <tr mat-row *matRowDef="let row; columns: displayedColumns;"></tr>
          </table>
        </mat-card-content>
      </mat-card>
    </div>
  `,
  styles: [`
    .products-container {
      padding: 20px;
    }

    .header-actions {
      margin-left: auto;
    }

    .full-width {
      width: 100%;
    }
  `]
})
export class ProductsListComponent implements OnInit {
  products: Product[] = [];
  displayedColumns: string[] = ['id', 'name', 'sku', 'price', 'actions'];

  constructor(private productService: ProductService) {}

  ngOnInit() {
    this.loadProducts();
  }

  private loadProducts() {
    this.productService.getAll().subscribe({
      next: (products: Product[]) => {
        this.products = products || [];
      },
      error: (error: any) => {
        console.error('Error loading products:', error);
        // Datos de prueba
        this.products = [
          { id: 1, name: 'Producto 1', sku: 'P001', description: 'Descripción 1', price: 100, categoryId: 1, supplierId: 1, createdAt: new Date(), updatedAt: new Date() },
          { id: 2, name: 'Producto 2', sku: 'P002', description: 'Descripción 2', price: 200, categoryId: 2, supplierId: 2, createdAt: new Date(), updatedAt: new Date() }
        ];
      }
    });
  }
}