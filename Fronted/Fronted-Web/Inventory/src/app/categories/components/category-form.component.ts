import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-category-form',
  template: `
    <div class="category-form-container">
      <div class="form-header">
        <h2>{{ isEditMode ? 'Editar Categoría' : 'Nueva Categoría' }}</h2>
        <button class="btn-back" (click)="onCancel()">
          <i class="material-icons">arrow_back</i>
          Volver a Categorías
        </button>
      </div>
      
      <form class="category-form" (submit)="onSubmit($event)">
        <div class="form-grid">
          <div class="form-section basic-info">
            <h3>Información Básica</h3>
            
            <div class="form-group">
              <label for="name">Nombre de la Categoría *</label>
              <input 
                type="text" 
                id="name"
                name="name"
                class="form-control"
                placeholder="Ej: Electrónicos"
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
                placeholder="Descripción detallada de la categoría"
                [(ngModel)]="formData.description"></textarea>
            </div>
            
            <div class="form-group">
              <label for="icon">Icono</label>
              <input 
                type="text" 
                id="icon"
                name="icon"
                class="form-control"
                placeholder="Ej: laptop, phone, home"
                [(ngModel)]="formData.icon">
            </div>
          </div>
          
          <div class="form-section status">
            <h3>Configuración</h3>
            
            <div class="form-group">
              <label for="isActive">Estado</label>
              <select 
                id="isActive"
                name="isActive"
                class="form-control"
                [(ngModel)]="formData.isActive">
                <option [value]="true">Activa</option>
                <option [value]="false">Inactiva</option>
              </select>
            </div>
            
            <div class="form-group">
              <label for="color">Color</label>
              <input 
                type="color" 
                id="color"
                name="color"
                class="form-control"
                [(ngModel)]="formData.color">
            </div>
            
            <div class="form-group">
              <label for="sortOrder">Orden de Mostrar</label>
              <input 
                type="number" 
                id="sortOrder"
                name="sortOrder"
                class="form-control"
                placeholder="1"
                [(ngModel)]="formData.sortOrder">
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
            {{ isEditMode ? 'Actualizar' : 'Crear' }} Categoría
          </button>
        </div>
      </form>
    </div>
  `,
  styles: [`
    .category-form-container {
      padding: 20px;
      max-width: 800px;
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
    
    .category-form {
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