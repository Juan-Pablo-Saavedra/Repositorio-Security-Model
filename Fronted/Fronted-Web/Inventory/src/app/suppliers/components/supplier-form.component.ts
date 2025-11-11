import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-supplier-form',
  template: `
    <div class="supplier-form-container">
      <div class="form-header">
        <h2>{{ isEditMode ? 'Editar Proveedor' : 'Nuevo Proveedor' }}</h2>
        <button class="btn-back" (click)="onCancel()">
          <i class="material-icons">arrow_back</i>
          Volver a Proveedores
        </button>
      </div>
      
      <form class="supplier-form" (submit)="onSubmit($event)">
        <div class="form-grid">
          <div class="form-section basic-info">
            <h3>Información Básica</h3>
            
            <div class="form-group">
              <label for="name">Nombre del Proveedor *</label>
              <input 
                type="text" 
                id="name"
                name="name"
                class="form-control"
                placeholder="Ej: TechCorp Supplies"
                [(ngModel)]="formData.name"
                required>
            </div>
            
            <div class="form-group">
              <label for="email">Email *</label>
              <input 
                type="email" 
                id="email"
                name="email"
                class="form-control"
                placeholder="contacto@proveedor.com"
                [(ngModel)]="formData.email"
                required>
            </div>
            
            <div class="form-group">
              <label for="phone">Teléfono *</label>
              <input 
                type="tel" 
                id="phone"
                name="phone"
                class="form-control"
                placeholder="+57 300 123 4567"
                [(ngModel)]="formData.phone"
                required>
            </div>
          </div>
          
          <div class="form-section location">
            <h3>Ubicación</h3>
            
            <div class="form-group">
              <label for="address">Dirección *</label>
              <textarea 
                id="address"
                name="address"
                class="form-control"
                rows="3"
                placeholder="Dirección completa del proveedor"
                [(ngModel)]="formData.address"
                required></textarea>
            </div>
            
            <div class="form-group">
              <label for="city">Ciudad</label>
              <input 
                type="text" 
                id="city"
                name="city"
                class="form-control"
                placeholder="Ciudad"
                [(ngModel)]="formData.city">
            </div>
            
            <div class="form-group">
              <label for="country">País</label>
              <input 
                type="text" 
                id="country"
                name="country"
                class="form-control"
                placeholder="País"
                [(ngModel)]="formData.country">
            </div>
          </div>
          
          <div class="form-section contact">
            <h3>Contacto Adicional</h3>
            
            <div class="form-group">
              <label for="contactPerson">Persona de Contacto</label>
              <input 
                type="text" 
                id="contactPerson"
                name="contactPerson"
                class="form-control"
                placeholder="Nombre del contacto principal"
                [(ngModel)]="formData.contactPerson">
            </div>
            
            <div class="form-group">
              <label for="contactPhone">Teléfono de Contacto</label>
              <input 
                type="tel" 
                id="contactPhone"
                name="contactPhone"
                class="form-control"
                placeholder="Teléfono de la persona de contacto"
                [(ngModel)]="formData.contactPhone">
            </div>
            
            <div class="form-group">
              <label for="website">Sitio Web</label>
              <input 
                type="url" 
                id="website"
                name="website"
                class="form-control"
                placeholder="https://www.proveedor.com"
                [(ngModel)]="formData.website">
            </div>
          </div>
          
          <div class="form-section status">
            <h3>Estado</h3>
            
            <div class="form-group">
              <label for="isActive">Estado del Proveedor</label>
              <select 
                id="isActive"
                name="isActive"
                class="form-control"
                [(ngModel)]="formData.isActive">
                <option [value]="true">Activo</option>
                <option [value]="false">Inactivo</option>
              </select>
            </div>
            
            <div class="form-group">
              <label for="category">Categoría</label>
              <select 
                id="category"
                name="category"
                class="form-control"
                [(ngModel)]="formData.category">
                <option value="">Seleccionar categoría</option>
                <option value="Electrónicos">Electrónicos</option>
                <option value="Ropa">Ropa</option>
                <option value="Hogar">Hogar</option>
                <option value="Deportes">Deportes</option>
                <option value="Industrial">Industrial</option>
              </select>
            </div>
            
            <div class="form-group">
              <label for="paymentTerms">Términos de Pago</label>
              <select 
                id="paymentTerms"
                name="paymentTerms"
                class="form-control"
                [(ngModel)]="formData.paymentTerms">
                <option value="immediate">Inmediato</option>
                <option value="15days">15 días</option>
                <option value="30days">30 días</option>
                <option value="45days">45 días</option>
                <option value="60days">60 días</option>
              </select>
            </div>
          </div>
        </div>
        
        <div class="form-actions">
          <button type="button" class="btn-secondary" (click)="onCancel()">
            Cancelar
          </button>
          <button 
            type="submit" 
            class="btn-primary"
            [disabled]="!isFormValid() || isLoading">
            <i class="material-icons" *ngIf="isLoading">hourglass_empty</i>
            {{ isEditMode ? 'Actualizar' : 'Crear' }} Proveedor
          </button>
        </div>
      </form>
    </div>
  `,
  styles: [`
    .supplier-form-container {
      padding: 20px;
      max-width: 1000px;
      margin: 0 auto;
    }
    
    .form-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-bottom: 30px;
    }
    
    .form-header h2 {
      color: #333;
      margin: 0;
      font-size: 28px;
      font-weight: 600;
    }
    
    .btn-back {
      background: none;
      border: 1px solid #e2e8f0;
      color: #666;
      padding: 10px 20px;
      border-radius: 8px;
      cursor: pointer;
      display: flex;
      align-items: center;
      gap: 8px;
      transition: all 0.3s ease;
    }
    
    .btn-back:hover {
      background-color: #f8f9fa;
      border-color: #cbd5e0;
    }
    
    .supplier-form {
      background: white;
      border-radius: 12px;
      box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
      overflow: hidden;
    }
    
    .form-grid {
      padding: 30px;
      display: grid;
      grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
      gap: 30px;
    }
    
    .form-section {
      background: #fafafa;
      padding: 20px;
      border-radius: 8px;
      border: 1px solid #f0f0f0;
    }
    
    .form-section h3 {
      margin: 0 0 20px 0;
      color: #333;
      font-size: 18px;
      font-weight: 600;
      border-bottom: 2px solid #667eea;
      padding-bottom: 8px;
    }
    
    .form-group {
      margin-bottom: 20px;
    }
    
    .form-group label {
      display: block;
      margin-bottom: 6px;
      color: #333;
      font-weight: 500;
      font-size: 14px;
    }
    
    .form-control {
      width: 100%;
      padding: 12px 14px;
      border: 1px solid #e2e8f0;
      border-radius: 8px;
      font-size: 14px;
      outline: none;
      transition: all 0.3s ease;
      background: white;
    }
    
    .form-control:focus {
      border-color: #667eea;
      box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.1);
    }
    
    .form-actions {
      background: #f8f9fa;
      padding: 20px 30px;
      display: flex;
      justify-content: flex-end;
      gap: 12px;
      border-top: 1px solid #e2e8f0;
    }
    
    .btn-secondary {
      background: #f7fafc;
      color: #4a5568;
      border: 1px solid #e2e8f0;
      padding: 12px 20px;
      border-radius: 8px;
      cursor: pointer;
      font-size: 14px;
      font-weight: 500;
      transition: all 0.3s ease;
    }
    
    .btn-secondary:hover {
      background: #edf2f7;
      border-color: #cbd5e0;
    }
    
    .btn-primary {
      background: linear-gradient(45deg, #667eea, #764ba2);
      color: white;
      border: none;
      padding: 12px 24px;
      border-radius: 8px;
      cursor: pointer;
      font-size: 14px;
      font-weight: 500;
      display: flex;
      align-items: center;
      gap: 8px;
      transition: all 0.3s ease;
    }
    
    .btn-primary:hover:not(:disabled) {
      transform: translateY(-2px);
      box-shadow: 0 4px 12px rgba(102, 126, 234, 0.3);
    }
    
    .btn-primary:disabled {
      opacity: 0.6;
      cursor: not-allowed;
      transform: none;
    }
    
    .material-icons {
      font-family: 'Material Icons';
      font-weight: normal;
      font-style: normal;
      font-size: 20px;
      line-height: 1;
      letter-spacing: normal;
      text-transform: none;
      display: inline-block;
      white-space: nowrap;
      word-wrap: normal;
      direction: ltr;
    }
    
    @media (max-width: 768px) {
      .form-grid {
        grid-template-columns: 1fr;
        padding: 20px;
      }
      
      .form-header {
        flex-direction: column;
        gap: 15px;
        align-items: stretch;
      }
      
      .form-actions {
        flex-direction: column;
        padding: 20px;
      }
    }
  `]
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