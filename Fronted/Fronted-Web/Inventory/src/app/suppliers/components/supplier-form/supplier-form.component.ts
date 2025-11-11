import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule, Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-supplier-form',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './supplier-form.component.html',
  styleUrl: './supplier-form.component.css'
})
export class SupplierFormComponent implements OnInit {
  isEditMode = false;
  isLoading = false;
  supplierId: number | null = null;
  
  formData: any = {
    name: '',
    email: '',
    phone: '',
    address: '',
    city: '',
    country: '',
    contactPerson: '',
    contactPhone: '',
    website: '',
    isActive: true,
    category: '',
    paymentTerms: '30days'
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
        this.supplierId = +params['id'];
        this.loadSupplier();
      }
    });
  }

  private loadSupplier(): void {
    if (this.isEditMode && this.supplierId) {
      // En producción, aquí cargarías los datos del servidor
      this.formData = {
        name: 'TechCorp Supplies',
        email: 'contacto@techcorp.com',
        phone: '+57 300 123 4567',
        address: 'Calle 45 #20-30, Bogotá',
        city: 'Bogotá',
        country: 'Colombia',
        contactPerson: 'Juan Pérez',
        contactPhone: '+57 301 555 0000',
        website: 'https://www.techcorp.com',
        isActive: true,
        category: 'Electrónicos',
        paymentTerms: '30days'
      };
    }
  }

  isFormValid(): boolean {
    return !!(this.formData.name && 
             this.formData.email && 
             this.formData.phone && 
             this.formData.address);
  }

  onSubmit(event: Event): void {
    event.preventDefault();
    
    if (this.isFormValid() && !this.isLoading) {
      this.isLoading = true;
      
      // Simular guardado
      setTimeout(() => {
        console.log('Proveedor guardado:', this.formData);
        this.isLoading = false;
        this.router.navigate(['/suppliers']);
      }, 1500);
    }
  }

  onCancel(): void {
    this.router.navigate(['/suppliers']);
  }
}