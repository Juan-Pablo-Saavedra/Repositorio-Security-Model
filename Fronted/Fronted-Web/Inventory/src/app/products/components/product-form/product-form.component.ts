import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-product-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './product-form.component.html',
  styleUrl: './product-form.component.css'
})
export class ProductFormComponent implements OnInit {
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
    isActive: true,
    supplier: ''
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
        description: 'Smartphone de última generación con chip A17 Pro',
        sku: 'IPH15PRO-001',
        category: 'Electrónicos',
        price: 999.99,
        cost: 800.00,
        isActive: true,
        supplier: '1'
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
        this.router.navigate(['/products']);
      }, 1500);
    }
  }

  onCancel(): void {
    this.router.navigate(['/products']);
  }
}