import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatCardModule } from '@angular/material/card';
import { CategoryService } from '../../shared/services/category.service';
import { Category } from '../../shared/models/inventory.model';

@Component({
  selector: 'app-categories-list',
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
    <div class="categories-container">
      <mat-card>
        <mat-card-header>
          <mat-card-title>Gestión de Categorías</mat-card-title>
          <div class="header-actions">
            <button mat-raised-button color="primary" routerLink="/categories/create">
              <mat-icon>add</mat-icon>
              Nueva Categoría
            </button>
          </div>
        </mat-card-header>
        <mat-card-content>
          <table mat-table [dataSource]="categories" class="full-width">
            <ng-container matColumnDef="id">
              <th mat-header-cell *matHeaderCellDef> ID </th>
              <td mat-cell *matCellDef="let category">{{category.id}}</td>
            </ng-container>

            <ng-container matColumnDef="name">
              <th mat-header-cell *matHeaderCellDef> Nombre </th>
              <td mat-cell *matCellDef="let category">{{category.name}}</td>
            </ng-container>

            <ng-container matColumnDef="description">
              <th mat-header-cell *matHeaderCellDef> Descripción </th>
              <td mat-cell *matCellDef="let category">{{category.description}}</td>
            </ng-container>

            <ng-container matColumnDef="actions">
              <th mat-header-cell *matHeaderCellDef> Acciones </th>
              <td mat-cell *matCellDef="let category">
                <button mat-icon-button color="primary" [routerLink]="['/categories/edit', category.id]">
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
    .categories-container {
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
export class CategoriesListComponent implements OnInit {
  categories: Category[] = [];
  displayedColumns: string[] = ['id', 'name', 'description', 'actions'];

  constructor(private categoryService: CategoryService) {}

  ngOnInit() {
    this.loadCategories();
  }

  private loadCategories() {
    this.categoryService.getAll().subscribe({
      next: (response: any) => {
        this.categories = response.data || [];
      },
      error: (error: any) => {
        console.error('Error loading categories:', error);
        // Datos de prueba
        this.categories = [
          { id: 1, name: 'Electrónicos', description: 'Productos electrónicos y tecnológicos', createdAt: new Date(), updatedAt: new Date() },
          { id: 2, name: 'Ropa', description: 'Ropa y accesorios', createdAt: new Date(), updatedAt: new Date() }
        ];
      }
    });
  }
}