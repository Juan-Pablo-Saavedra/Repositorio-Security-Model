import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatCardModule } from '@angular/material/card';
import { SupplierService } from '../../shared/services/supplier.service';
import { Supplier } from '../../shared/models/inventory.model';

@Component({
  selector: 'app-suppliers-list',
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
    <div class="suppliers-container">
      <mat-card>
        <mat-card-header>
          <mat-card-title>Gestión de Proveedores</mat-card-title>
          <div class="header-actions">
            <button mat-raised-button color="primary" routerLink="/suppliers/create">
              <mat-icon>add</mat-icon>
              Nuevo Proveedor
            </button>
          </div>
        </mat-card-header>
        <mat-card-content>
          <table mat-table [dataSource]="suppliers" class="full-width">
            <ng-container matColumnDef="id">
              <th mat-header-cell *matHeaderCellDef> ID </th>
              <td mat-cell *matCellDef="let supplier">{{supplier.id}}</td>
            </ng-container>

            <ng-container matColumnDef="name">
              <th mat-header-cell *matHeaderCellDef> Nombre </th>
              <td mat-cell *matCellDef="let supplier">{{supplier.name}}</td>
            </ng-container>

            <ng-container matColumnDef="contactEmail">
              <th mat-header-cell *matHeaderCellDef> Email </th>
              <td mat-cell *matCellDef="let supplier">{{supplier.contactEmail}}</td>
            </ng-container>

            <ng-container matColumnDef="contactPhone">
              <th mat-header-cell *matHeaderCellDef> Teléfono </th>
              <td mat-cell *matCellDef="let supplier">{{supplier.contactPhone}}</td>
            </ng-container>

            <ng-container matColumnDef="actions">
              <th mat-header-cell *matHeaderCellDef> Acciones </th>
              <td mat-cell *matCellDef="let supplier">
                <button mat-icon-button color="primary" [routerLink]="['/suppliers/edit', supplier.id]">
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
    .suppliers-container {
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
export class SuppliersListComponent implements OnInit {
  suppliers: Supplier[] = [];
  displayedColumns: string[] = ['id', 'name', 'contactEmail', 'contactPhone', 'actions'];

  constructor(private supplierService: SupplierService) {}

  ngOnInit() {
    this.loadSuppliers();
  }

  private loadSuppliers() {
    this.supplierService.getAll().subscribe({
      next: (response: any) => {
        this.suppliers = response.data || [];
      },
      error: (error: any) => {
        console.error('Error loading suppliers:', error);
        // Datos de prueba
        this.suppliers = [
          { id: 1, name: 'TechCorp', contactEmail: 'contacto@techcorp.com', contactPhone: '+1234567890', address: 'Calle Principal 123', createdAt: new Date(), updatedAt: new Date() },
          { id: 2, name: 'SupplyPlus', contactEmail: 'ventas@supplyplus.com', contactPhone: '+0987654321', address: 'Avenida Central 456', createdAt: new Date(), updatedAt: new Date() }
        ];
      }
    });
  }
}