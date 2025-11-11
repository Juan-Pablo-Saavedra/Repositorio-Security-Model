import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule, Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-inventory-form',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './inventory-form.component.html',
  styleUrl: './inventory-form.component.css'
})
export class InventoryFormComponent implements OnInit {
  isEditMode = false;
  isLoading = false;
  productId: number | null = null;
  
  formData: any = {
    name: '',
    description: '',
    sku: '',
    category: '',
    price: 0,
    cost: 0,
    stock: 0,
    minStock: 0,
    supplier: '',
    location: '',
    status: 'AVAILABLE',
    notes: ''
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
        this.productId = +params['id'];
        this.loadProduct();
      }
    });
  }

  private loadProduct(): void {
    if (this.isEditMode && this.productId) {
      // En producción, aquí cargarías los datos del servidor
      this.formData = {
        name: 'iPhone 15 Pro',
        description: 'Smartphone de última generación',
        sku: 'IPH15PRO-001',
        category: 'Electrónicos',
        price: 999.99,
        cost: 800.00,
        stock: 25,
        minStock: 10,
        supplier: '1',
        location: 'Aisle 3, Section B',
        status: 'AVAILABLE',
        notes: 'Producto de alta demanda'
      };
    }
  }

  isFormValid(): boolean {
    return !!(this.formData.name && 
             this.formData.sku && 
             this.formData.category && 
             this.formData.price > 0);
  }

  onSubmit(event: Event): void {
    event.preventDefault();
    
    if (this.isFormValid() && !this.isLoading) {
      this.isLoading = true;
      
      // Simular guardado
      setTimeout(() => {
        console.log('Producto guardado:', this.formData);
        this.isLoading = false;
        this.router.navigate(['/inventory']);
      }, 1500);
    }
  }

  onSaveDraft(): void {
    if (!this.isLoading) {
      this.isLoading = true;
      
      console.log('Guardando como borrador...');
      
      setTimeout(() => {
        this.isLoading = false;
        alert('Borrador guardado exitosamente');
      }, 800);
    }
  }

  onCancel(): void {
    this.router.navigate(['/inventory']);
  }
}