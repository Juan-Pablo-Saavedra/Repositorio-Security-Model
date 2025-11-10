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
import { CategoryService } from '../../shared/services/category.service';
import { Category } from '../../shared/models/inventory.model';

@Component({
  selector: 'app-category-form',
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
    <div class="category-form-container">
      <mat-card>
        <mat-card-header>
          <mat-card-title>{{ isEditing ? 'Editar Categoría' : 'Nueva Categoría' }}</mat-card-title>
        </mat-card-header>
        <mat-card-content>
          <form [formGroup]="categoryForm" (ngSubmit)="onSubmit()">
            <mat-form-field appearance="outline" class="full-width">
              <mat-label>Nombre</mat-label>
              <input matInput formControlName="name" placeholder="Ingrese el nombre de la categoría">
              <mat-error *ngIf="categoryForm.get('name')?.hasError('required')">
                El nombre es requerido
              </mat-error>
            </mat-form-field>

            <mat-form-field appearance="outline" class="full-width">
              <mat-label>Descripción</mat-label>
              <textarea matInput formControlName="description" placeholder="Ingrese la descripción de la categoría" rows="3"></textarea>
            </mat-form-field>

            <div class="form-actions">
              <button mat-button type="button" routerLink="/categories">
                <mat-icon>cancel</mat-icon>
                Cancelar
              </button>
              <button mat-raised-button color="primary" type="submit" [disabled]="categoryForm.invalid">
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
    .category-form-container {
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
export class CategoryFormComponent implements OnInit {
  categoryForm: FormGroup;
  isEditing = false;
  categoryId: number | null = null;

  constructor(
    private fb: FormBuilder,
    private categoryService: CategoryService,
    private route: ActivatedRoute,
    private router: Router,
    private snackBar: MatSnackBar
  ) {
    this.categoryForm = this.fb.group({
      name: ['', [Validators.required]],
      description: ['']
    });
  }

  ngOnInit() {
    this.categoryId = this.route.snapshot.params['id'];
    this.isEditing = !!this.categoryId;

    if (this.isEditing && this.categoryId) {
      this.loadCategory(this.categoryId);
    }
  }

  private loadCategory(id: number) {
    this.categoryService.findById(id).subscribe({
      next: (response: any) => {
        const category = response.data;
        if (category) {
          this.categoryForm.patchValue({
            name: category.name,
            description: category.description
          });
        }
      },
      error: (error: any) => {
        console.error('Error loading category:', error);
        this.snackBar.open('Error al cargar la categoría', 'Cerrar', { duration: 3000 });
      }
    });
  }

  onSubmit() {
    if (this.categoryForm.valid) {
      const categoryData = this.categoryForm.value;

      const request = this.isEditing && this.categoryId
        ? this.categoryService.update(this.categoryId, categoryData)
        : this.categoryService.create(categoryData);

      request.subscribe({
        next: (response: any) => {
          const message = this.isEditing ? 'Categoría actualizada exitosamente' : 'Categoría creada exitosamente';
          this.snackBar.open(message, 'Cerrar', { duration: 3000 });
          this.router.navigate(['/categories']);
        },
        error: (error: any) => {
          console.error('Error saving category:', error);
          this.snackBar.open('Error al guardar la categoría', 'Cerrar', { duration: 3000 });
        }
      });
    }
  }
}