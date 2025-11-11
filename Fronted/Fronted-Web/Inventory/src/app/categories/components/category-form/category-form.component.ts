import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-category-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './category-form.component.html',
  styleUrl: './category-form.component.css'
})
export class CategoryFormComponent implements OnInit {
  isEditMode = false;
  isLoading = false;
  categoryId: number | null = null;
  
  formData: any = {
    name: '',
    description: '',
    icon: '',
    isActive: true,
    color: '#667eea',
    sortOrder: 1
  };

  constructor(
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.checkEditMode();
  }

  private checkEditMode(): void {
    this.route.params.subscribe(params => {
      if (params['id']) {
        this.isEditMode = true;
        this.categoryId = +params['id'];
        this.loadCategory();
      }
    });
  }

  private loadCategory(): void {
    if (this.isEditMode && this.categoryId) {
      // En producción, aquí cargarías los datos del servidor
      this.formData = {
        name: 'Electrónicos',
        description: 'Dispositivos electrónicos y tecnológicos',
        icon: 'devices',
        isActive: true,
        color: '#667eea',
        sortOrder: 1
      };
    }
  }

  isFormValid(): boolean {
    return !!this.formData.name;
  }

  onSubmit(event: Event): void {
    event.preventDefault();
    
    if (this.isFormValid() && !this.isLoading) {
      this.isLoading = true;
      
      // Simular guardado
      setTimeout(() => {
        console.log('Categoría guardada:', this.formData);
        this.isLoading = false;
        this.router.navigate(['/categories']);
      }, 1500);
    }
  }

  onCancel(): void {
    this.router.navigate(['/categories']);
  }
}