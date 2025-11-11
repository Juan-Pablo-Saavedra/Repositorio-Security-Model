import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-product-form',
  template: `
    <div class="product-form-container">
      <div class="form-header">
        <h2>{{ isEditMode ? 'Editar Producto' : 'Nuevo Producto' }}</h2>
        <button class="btn-back" (click)="onCancel()">
          <i class="material-icons">arrow_back</i>
          Volver a Productos
        </button>
      </div>
      
      <form class="product-form" (submit)="onSubmit($event)">
        <div class="form-grid">
          <div class="form-section basic-info">
            <h3>Información Básica</h3>
            
            <div class="form-group">
              <label for="name">Nombre del Producto *</label>
              <input 
                type="text" 
                id="name"
                name="name"
                class="form-control"
                placeholder="Ej: iPhone 15 Pro"
                [(ngModel)]="formData.name"
                required>
            </div>
            
            <div class="form-group">
              <label for="description">Descripción</label>
              <textarea 
                id="description"
                name="description"
                class="form-control"
                rows="4"
                placeholder="Descripción detallada del producto"
                [(ngModel)]="formData.description"></textarea>
            </div>
            
            <div class="form-row">
              <div class="form-group">
                <label for="sku">Código SKU *</label>
                <input 
                  type="text" 
                  id="sku"
                  name="sku"
                  class="form-control"
                  placeholder="Ej: IPH15PRO-001"
                  [(ngModel)]="formData.sku"
                  required>
              </div>
              
              <div class="form-group">
                <label for="category">Categoría *</label>
                <select 
                  id="category"
                  name="category"
                  class="form-control"
                  [(ngModel)]="formData.category"
                  required>
                  <option value="">Seleccionar categoría</option>
                  <option value="Electrónicos">Electrónicos</option>
                  <option value="Ropa">Ropa</option>
                  <option value="Hogar">Hogar</option>
                  <option value="Deportes">Deportes</option>
                  <option value="Libros">Libros</option>
                </select>
              </div>
            </div>
          </div>
          
          <div class="form-section pricing">
            <h3>Precios</h3>
            
            <div class="form-group">
              <label for="price">Precio de Venta *</label>
              <div class="input-group">
                <span class="input-prefix">$</span>
                <input 
                  type="number" 
                  id="price"
                  name="price"
                  class="form-control"
                  placeholder="0.00"
                  step="0.01"
                  min="0"
                  [(ngModel)]="formData.price"
                  required>
              </div>
            </div>
            
            <div class="form-group">
              <label for="cost">Costo de Adquisición</label>
              <div class="input-group">
                <span class="input-prefix">$</span>
                <input 
                  type="number" 
                  id="cost"
                  name="cost"
                  class="form-control"
                  placeholder="0.00"
                  step="0.01"
                  min="0"
                  [(ngModel)]="formData.cost">
              </div>
            </div>
          </div>
          
          <div class="form-section status">
            <h3>Estado</h3>
            
            <div class="form-group">
              <label for="isActive">Estado del Producto</label>
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
              <label for="supplier">Proveedor</label>
              <select 
                id="supplier"
                name="supplier"
                class="form-control"
                [(ngModel)]="formData.supplier">
                <option value="">Seleccionar proveedor</option>
                <option value="1">TechCorp Suppliers</option>
                <option value="2">Global Electronics</option>
                <option value="3">Local Distributors</option>
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
            {{ isEditMode ? 'Actualizar' : 'Crear' }} Producto
          </button>
        </div>
      </form>
    </div>
  `,
  styles: [`
    .product-form-container {
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
    
    .product-form {
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
    
    .form-row {
      display: grid;
      grid-template-columns: 1fr 1fr;
      gap: 15px;
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
    
    .input-group {
      position: relative;
    }
    
    .input-prefix {
      position: absolute;
      left: 12px;
      top: 50%;
      transform: translateY(-50%);
      color: #666;
      font-weight: 500;
    }
    
    .input-group .form-control {
      padding-left: 30px;
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
      
      .form-row {
        grid-template-columns: 1fr;
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