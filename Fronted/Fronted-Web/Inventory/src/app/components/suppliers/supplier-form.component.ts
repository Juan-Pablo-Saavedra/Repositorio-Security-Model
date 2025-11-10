import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, ActivatedRoute, Router } from '@angular/router';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatSnackBarModule, MatSnackBar } from '@angular/material/snack-bar';
import { SupplierService } from '../../shared/services/supplier.service';
import { Supplier } from '../../shared/models/inventory.model';

@Component({
  selector: 'app-supplier-form',
  standalone: true,
  imports: [
    CommonModule,
    RouterModule,
    ReactiveFormsModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule,
    MatSnackBarModule
  ],
  template: `
    <div class="supplier-form-container">
      <mat-card>
        <mat-card-header>
          <mat-card-title>{{ isEditing ? 'Editar Proveedor' : 'Nuevo Proveedor' }}</mat-card-title>
        </mat-card-header>
        <mat-card-content>
          <form [formGroup]="supplierForm" (ngSubmit)="onSubmit()">
            <mat-form-field appearance="outline" class="full-width">
              <mat-label>Nombre</mat-label>
              <input matInput formControlName="name" placeholder="Ingrese el nombre del proveedor">
              <mat-error *ngIf="supplierForm.get('name')?.hasError('required')">
                El nombre es requerido
              </mat-error>
            </mat-form-field>

            <mat-form-field appearance="outline" class="full-width">
              <mat-label>Email de Contacto</mat-label>
              <input matInput type="email" formControlName="contactEmail" placeholder="Ingrese el email de contacto">
              <mat-error *ngIf="supplierForm.get('contactEmail')?.hasError('required')">
                El email es requerido
              </mat-error>
              <mat-error *ngIf="supplierForm.get('contactEmail')?.hasError('email')">
                Ingrese un email válido
              </mat-error>
            </mat-form-field>

            <mat-form-field appearance="outline" class="full-width">
              <mat-label>Teléfono de Contacto</mat-label>
              <input matInput formControlName="contactPhone" placeholder="Ingrese el teléfono de contacto">
            </mat-form-field>

            <mat-form-field appearance="outline" class="full-width">
              <mat-label>Dirección</mat-label>
              <textarea matInput formControlName="address" placeholder="Ingrese la dirección" rows="3"></textarea>
            </mat-form-field>

            <div class="form-actions">
              <button mat-button type="button" routerLink="/suppliers">
                <mat-icon>cancel</mat-icon>
                Cancelar
              </button>
              <button mat-raised-button color="primary" type="submit" [disabled]="supplierForm.invalid">
                <mat-icon>{{ isEditing ? 'save' : 'add' }}</mat-icon>
                {{ isEditing ? 'Actualizar' : 'Crear' }}
              </button>
            </div>
          </form>
        </mat-card-content>
      </mat-card>
    </div>
  `,
  styles: [`
    .supplier-form-container {
      padding: 20px;
      max-width: 600px;
      margin: 0 auto;
    }

    .full-width {
      width: 100%;
      margin-bottom: 16px;
    }

    .form-actions {
      display: flex;
      justify-content: flex-end;
      gap: 12px;
      margin-top: 24px;
    }
  `]
})
export class SupplierFormComponent implements OnInit {
  supplierForm: FormGroup;
  isEditing = false;
  supplierId: number | null = null;

  constructor(
    private fb: FormBuilder,
    private supplierService: SupplierService,
    private route: ActivatedRoute,
    private router: Router,
    private snackBar: MatSnackBar
  ) {
    this.supplierForm = this.fb.group({
      name: ['', [Validators.required]],
      contactEmail: ['', [Validators.required, Validators.email]],
      contactPhone: [''],
      address: ['']
    });
  }

  ngOnInit() {
    this.supplierId = this.route.snapshot.params['id'];
    this.isEditing = !!this.supplierId;

    if (this.isEditing && this.supplierId) {
      this.loadSupplier(this.supplierId);
    }
  }

  private loadSupplier(id: number) {
    this.supplierService.findById(id).subscribe({
      next: (response: any) => {
        const supplier = response.data;
        if (supplier) {
          this.supplierForm.patchValue({
            name: supplier.name,
            contactEmail: supplier.contactEmail,
            contactPhone: supplier.contactPhone,
            address: supplier.address
          });
        }
      },
      error: (error: any) => {
        console.error('Error loading supplier:', error);
        this.snackBar.open('Error al cargar el proveedor', 'Cerrar', { duration: 3000 });
      }
    });
  }

  onSubmit() {
    if (this.supplierForm.valid) {
      const supplierData = this.supplierForm.value;

      const request = this.isEditing && this.supplierId
        ? this.supplierService.update(this.supplierId, supplierData)
        : this.supplierService.create(supplierData);

      request.subscribe({
        next: (response: any) => {
          const message = this.isEditing ? 'Proveedor actualizado exitosamente' : 'Proveedor creado exitosamente';
          this.snackBar.open(message, 'Cerrar', { duration: 3000 });
          this.router.navigate(['/suppliers']);
        },
        error: (error: any) => {
          console.error('Error saving supplier:', error);
          this.snackBar.open('Error al guardar el proveedor', 'Cerrar', { duration: 3000 });
        }
      });
    }
  }
}