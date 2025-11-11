import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-categories-list',
  template: `
    <div class="categories-container">
      <div class="header-section">
        <h2>Gestión de Categorías</h2>
        <button class="btn-primary" (click)="onCreateCategory()">
          <i class="material-icons">add</i>
          Nueva Categoría
        </button>
      </div>
      
      <div class="search-section">
        <div class="search-field">
          <i class="material-icons">search</i>
          <input 
            type="text" 
            placeholder="Buscar categorías..." 
            [(ngModel)]="searchTerm" 
            (ngModelChange)="onSearch()">
        </div>
        
        <div class="stats-cards">
          <div class="stat-card">
            <div class="stat-icon">
              <i class="material-icons">category</i>
            </div>
            <div class="stat-content">
              <h3>{{ categories.length }}</h3>
              <p>Total Categorías</p>
            </div>
          </div>
          
          <div class="stat-card">
            <div class="stat-icon">
              <i class="material-icons">inventory</i>
            </div>
            <div class="stat-content">
              <h3>{{ getTotalProducts() }}</h3>
              <p>Productos Totales</p>
            </div>
          </div>
        </div>
      </div>
      
      <div class="table-container">
        <table class="categories-table">
          <thead>
            <tr>
              <th>ID</th>
              <th>Nombre</th>
              <th>Descripción</th>
              <th>Productos</th>
              <th>Estado</th>
              <th>Fecha Creación</th>
              <th>Acciones</th>
            </tr>
          </thead>
          <tbody>
            <tr *ngFor="let category of filteredCategories">
              <td>{{ category.id }}</td>
              <td>{{ category.name }}</td>
              <td class="description-cell">{{ category.description }}</td>
              <td>
                <span class="product-count">{{ category.productCount }}</span>
              </td>
              <td>
                <span class="status-badge" [class]="category.isActive ? 'active' : 'inactive'">
                  {{ category.isActive ? 'Activa' : 'Inactiva' }}
                </span>
              </td>
              <td>{{ formatDate(category.createdAt) }}</td>
              <td>
                <div class="action-buttons">
                  <button class="btn-icon btn-edit" (click)="onEdit(category.id)" title="Editar">
                    <i class="material-icons">edit</i>
                  </button>
                  <button class="btn-icon btn-toggle" (click)="onToggleStatus(category.id)" title="Activar/Desactivar">
                    <i class="material-icons">{{ category.isActive ? 'visibility_off' : 'visibility' }}</i>
                  </button>
                  <button class="btn-icon btn-delete" (click)="onDelete(category.id)" title="Eliminar">
                    <i class="material-icons">delete</i>
                  </button>
                </div>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
      
      <div class="pagination" *ngIf="totalPages > 1">
        <button 
          class="page-btn" 
          [disabled]="currentPage === 1"
          (click)="onPageChange(currentPage - 1)">
          <i class="material-icons">chevron_left</i>
        </button>
        <span>Página {{ currentPage }} de {{ totalPages }}</span>
        <button 
          class="page-btn" 
          [disabled]="currentPage === totalPages"
          (click)="onPageChange(currentPage + 1)">
          <i class="material-icons">chevron_right</i>
        </button>
      </div>
    </div>
  `,
  styles: [`
    .categories-container {
      padding: 20px;
      max-width: 1200px;
      margin: 0 auto;
    }
    
    .header-section {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-bottom: 30px;
    }
    
    .header-section h2 {
      color: #333;
      margin: 0;
      font-size: 28px;
      font-weight: 600;
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
    
    .btn-primary:hover {
      transform: translateY(-2px);
      box-shadow: 0 4px 12px rgba(102, 126, 234, 0.3);
    }
    
    .search-section {
      background: white;
      padding: 20px;
      border-radius: 12px;
      box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
      margin-bottom: 20px;
      display: flex;
      justify-content: space-between;
      align-items: center;
    }
    
    .search-field {
      position: relative;
      max-width: 300px;
    }
    
    .search-field i {
      position: absolute;
      left: 12px;
      top: 50%;
      transform: translateY(-50%);
      color: #666;
    }
    
    .search-field input {
      width: 100%;
      padding: 10px 12px 10px 40px;
      border: 1px solid #e2e8f0;
      border-radius: 8px;
      font-size: 14px;
      outline: none;
    }
    
    .search-field input:focus {
      border-color: #667eea;
      box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.1);
    }
    
    .stats-cards {
      display: flex;
      gap: 20px;
    }
    
    .stat-card {
      background: linear-gradient(135deg, #667eea, #764ba2);
      color: white;
      padding: 20px;
      border-radius: 12px;
      display: flex;
      align-items: center;
      gap: 15px;
      min-width: 150px;
    }
    
    .stat-icon {
      width: 48px;
      height: 48px;
      background: rgba(255, 255, 255, 0.2);
      border-radius: 12px;
      display: flex;
      align-items: center;
      justify-content: center;
    }
    
    .stat-content h3 {
      margin: 0;
      font-size: 24px;
      font-weight: 700;
    }
    
    .stat-content p {
      margin: 4px 0 0 0;
      font-size: 14px;
      opacity: 0.9;
    }
    
    .table-container {
      background: white;
      border-radius: 12px;
      box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
      overflow: hidden;
    }
    
    .categories-table {
      width: 100%;
      border-collapse: collapse;
    }
    
    .categories-table th {
      background: #f8f9fa;
      padding: 15px;
      text-align: left;
      font-weight: 600;
      color: #333;
      border-bottom: 2px solid #e2e8f0;
    }
    
    .categories-table td {
      padding: 15px;
      border-bottom: 1px solid #f1f1f1;
    }
    
    .categories-table tr:hover {
      background-color: #f8f9fa;
    }
    
    .description-cell {
      max-width: 200px;
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
    }
    
    .product-count {
      background: #e0f2fe;
      color: #0369a1;
      padding: 4px 8px;
      border-radius: 4px;
      font-size: 12px;
      font-weight: 500;
    }
    
    .status-badge {
      padding: 4px 8px;
      border-radius: 4px;
      font-size: 12px;
      font-weight: 500;
    }
    
    .status-badge.active {
      background: #d1fae5;
      color: #065f46;
    }
    
    .status-badge.inactive {
      background: #fee2e2;
      color: #991b1b;
    }
    
    .action-buttons {
      display: flex;
      gap: 4px;
    }
    
    .btn-icon {
      background: none;
      border: none;
      cursor: pointer;
      padding: 6px;
      border-radius: 4px;
      transition: background-color 0.2s;
    }
    
    .btn-edit {
      color: #3b82f6;
    }
    
    .btn-edit:hover {
      background-color: #dbeafe;
    }
    
    .btn-toggle {
      color: #f59e0b;
    }
    
    .btn-toggle:hover {
      background-color: #fef3c7;
    }
    
    .btn-delete {
      color: #ef4444;
    }
    
    .btn-delete:hover {
      background-color: #fee2e2;
    }
    
    .pagination {
      display: flex;
      justify-content: center;
      align-items: center;
      gap: 15px;
      margin-top: 20px;
      padding: 20px;
    }
    
    .page-btn {
      background: white;
      border: 1px solid #e2e8f0;
      padding: 8px 12px;
      border-radius: 6px;
      cursor: pointer;
      display: flex;
      align-items: center;
      transition: all 0.2s ease;
    }
    
    .page-btn:hover:not(:disabled) {
      background: #f7fafc;
    }
    
    .page-btn:disabled {
      opacity: 0.5;
      cursor: not-allowed;
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
      .header-section {
        flex-direction: column;
        gap: 15px;
        align-items: stretch;
      }
      
      .search-section {
        flex-direction: column;
        gap: 15px;
        align-items: stretch;
      }
      
      .stats-cards {
        flex-direction: column;
        width: 100%;
      }
      
      .categories-table {
        font-size: 12px;
      }
      
      .categories-table th,
      .categories-table td {
        padding: 8px;
      }
    }
  `]
})
export class CategoriesListComponent implements OnInit {
  categories: any[] = [];
  filteredCategories: any[] = [];
  searchTerm: string = '';
  
  // Paginación
  currentPage: number = 1;
  pageSize: number = 10;
  totalPages: number = 0;

  constructor(private router: Router) {}

  ngOnInit(): void {
    this.loadCategories();
  }

  loadCategories(): void {
    // Datos de ejemplo - en producción vendrían del servicio
    this.categories = [
      {
        id: 1,
        name: 'Electrónicos',
        description: 'Dispositivos electrónicos y tecnológicos',
        productCount: 45,
        isActive: true,
        createdAt: new Date('2024-01-15')
      },
      {
        id: 2,
        name: 'Ropa',
        description: 'Prendas de vestir y accesorios',
        productCount: 32,
        isActive: true,
        createdAt: new Date('2024-01-16')
      },
      {
        id: 3,
        name: 'Hogar',
        description: 'Artículos para el hogar y decoración',
        productCount: 18,
        isActive: false,
        createdAt: new Date('2024-01-17')
      },
      {
        id: 4,
        name: 'Deportes',
        description: 'Equipamiento deportivo y fitness',
        productCount: 25,
        isActive: true,
        createdAt: new Date('2024-01-18')
      }
    ];
    
    this.applyFilters();
  }

  onSearch(): void {
    this.applyFilters();
  }

  applyFilters(): void {
    let filtered = [...this.categories];
    
    // Filtro por búsqueda
    if (this.searchTerm) {
      const term = this.searchTerm.toLowerCase();
      filtered = filtered.filter(category => 
        category.name.toLowerCase().includes(term) ||
        category.description.toLowerCase().includes(term)
      );
    }
    
    this.filteredCategories = filtered;
    this.calculateTotalPages();
  }

  calculateTotalPages(): void {
    this.totalPages = Math.ceil(this.filteredCategories.length / this.pageSize);
  }

  onPageChange(page: number): void {
    this.currentPage = page;
  }

  getTotalProducts(): number {
    return this.categories.reduce((total, category) => total + category.productCount, 0);
  }

  formatDate(date: Date): string {
    return new Date(date).toLocaleDateString('es-ES');
  }

  onCreateCategory(): void {
    this.router.navigate(['/categories/create']);
  }

  onEdit(id: number): void {
    this.router.navigate(['/categories/edit', id]);
  }

  onToggleStatus(id: number): void {
    const category = this.categories.find(c => c.id === id);
    if (category) {
      category.isActive = !category.isActive;
      console.log(`Categoría ${id} ${category.isActive ? 'activada' : 'desactivada'}`);
    }
  }

  onDelete(id: number): void {
    if (confirm('¿Estás seguro de que deseas eliminar esta categoría?')) {
      console.log('Eliminando categoría:', id);
      this.categories = this.categories.filter(c => c.id !== id);
      this.applyFilters();
    }
  }
}