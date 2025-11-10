import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, ActivatedRoute, Router } from '@angular/router';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatSelectModule } from '@angular/material/select';
import { MatSnackBarModule, MatSnackBar } from '@angular/material/snack-bar';
import { ProductService } from '../../shared/services/product.service';
import { CategoryService } from '../../shared/services/category.service';
import { SupplierService } from '../../shared/services/supplier.service';
import { Product, Category, Supplier } from '../../shared/models/inventory.model';

@Component({
  selector: 'app-product-form',
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
    MatSelectModule,
    MatSnackBarModule
  ],
  template: `
    <div class="product-form-container">
      <mat-card>
        <mat-card-header>
          <mat-card-title>{{ isEditing ? 'Editar Producto' : 'Nuevo Producto' }}</mat-card-title>
        </mat-card-header>
        <mat-card-content>
          <form [formGroup]="productForm" (ngSubmit)="onSubmit()">
            <mat-form-field appearance="outline" class="full-width">
              <mat-label>Nombre</mat-label>
              <input matInput formControlName="name" placeholder="Ingrese el nombre del producto">
              <mat-error *ngIf="productForm.get('name')?.hasError('required')">
                El nombre es requerido
              </mat-error>
            </mat-form-field>

            <mat-form-field appearance="outline" class="full-width">
              <mat-label>SKU</mat-label>
              <input matInput formControlName="sku" placeholder="Ingrese el SKU del producto">
              <mat-error *ngIf="productForm.get('sku')?.hasError('required')">
                El SKU es requerido
              </mat-error>
            </mat-form-field>

            <mat-form-field appearance="outline" class="full-width">
              <mat-label>Descripción</mat-label>
              <textarea matInput formControlName="description" placeholder="Ingrese la descripción del producto" rows="3"></textarea>
            </mat-form-field>

            <mat-form-field appearance="outline" class="full-width">
              <mat-label>Precio</mat-label>
              <input matInput type="number" formControlName="price" placeholder="0.00" step="0.01">
              <mat-error *ngIf="productForm.get('price')?.hasError('required')">
                El precio es requerido
              </mat-error>
              <mat-error *ngIf="productForm.get('price')?.hasError('min')">
                El precio debe ser mayor a 0
              </mat-error>
            </mat-form-field>

            <mat-form-field appearance="outline" class="full-width">
              <mat-label>Categoría</mat-label>
              <mat-select formControlName="categoryId">
                <mat-option *ngFor="let category of categories" [value]="category.id">
                  {{ category.name }}
                </mat-option>
              </mat-select>
            </mat-form-field>

            <mat-form-field appearance="outline" class="full-width">
              <mat-label>Proveedor</mat-label>
              <mat-select formControlName="supplierId">
                <mat-option *ngFor="let supplier of suppliers" [value]="supplier.id">
                  {{ supplier.name }}
                </mat-option>
              </mat-select>
            </mat-form-field>

            <div class="form-actions">
              <button mat-button type="button" routerLink="/products">
                <mat-icon>cancel</mat-icon>
                Cancelar
              </button>
              <button mat-raised-button color="primary" type="submit" [disabled]="productForm.invalid">
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
    .product-form-container {
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
export class ProductFormComponent implements OnInit {
  productForm: FormGroup;
  isEditing = false;
  productId: number | null = null;
  categories: Category[] = [];
  suppliers: Supplier[] = [];

  constructor(
    private fb: FormBuilder,
    private productService: ProductService,
    private categoryService: CategoryService,
    private supplierService: SupplierService,
    private route: ActivatedRoute,
    private router: Router,
    private snackBar: MatSnackBar
  ) {
    this.productForm = this.fb.group({
      name: ['', [Validators.required]],
      sku: ['', [Validators.required]],
      description: [''],
      price: [0, [Validators.required, Validators.min(0.01)]],
      categoryId: [null],
      supplierId: [null]
    });
  }

  ngOnInit() {
    this.productId = this.route.snapshot.params['id'];
    this.isEditing = !!this.productId;

    this.loadCategories();
    this.loadSuppliers();

    if (this.isEditing && this.productId) {
      this.loadProduct(this.productId);
    }
  }

  private loadCategories() {
    this.categoryService.getAll().subscribe({
      next: (response: any) => {
        this.categories = response.data || [];
      },
      error: (error: any) => {
        console.error('Error loading categories:', error);
      }
    });
  }

  private loadSuppliers() {
    this.supplierService.getAll().subscribe({
      next: (response: any) => {
        this.suppliers = response.data || [];
      },
      error: (error: any) => {
        console.error('Error loading suppliers:', error);
      }
    });
  }

  private loadProduct(id: number) {
    this.productService.findById(id).subscribe({
      next: (response: any) => {
        const product = response.data;
        if (product) {
          this.productForm.patchValue({
            name: product.name,
            sku: product.sku,
            description: product.description,
            price: product.price,
            categoryId: product.categoryId,
            supplierId: product.supplierId
          });
        }
      },
      error: (error: any) => {
        console.error('Error loading product:', error);
        this.snackBar.open('Error al cargar el producto', 'Cerrar', { duration: 3000 });
      }
    });
  }

  onSubmit() {
    if (this.productForm.valid) {
      const productData = this.productForm.value;

      const request = this.isEditing && this.productId
        ? this.productService.update(this.productId, productData)
        : this.productService.create(productData);

      request.subscribe({
        next: (response: any) => {
          const message = this.isEditing ? 'Producto actualizado exitosamente' : 'Producto creado exitosamente';
          this.snackBar.open(message, 'Cerrar', { duration: 3000 });
          this.router.navigate(['/products']);
        },
        error: (error: any) => {
          console.error('Error saving product:', error);
          this.snackBar.open('Error al guardar el producto', 'Cerrar', { duration: 3000 });
        }
      });
    }
  }
}