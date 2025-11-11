import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-products-list',
  template: `
    <div class="products-container">
      <div class="header-section">
        <h2>Gestión de Productos</h2>
        <button class="btn-primary" (click)="onCreateProduct()">
          <i class="material-icons">add</i>
          Nuevo Producto
        </button>
      </div>
      
      <div class="search-section">
        <div class="search-field">
          <i class="material-icons">search</i>
          <input 
            type="text" 
            placeholder="Buscar productos..." 
            [(ngModel)]="searchTerm" 
            (ngModelChange)="onSearch()">
        </div>
        
        <div class="filter-buttons">
          <button 
            class="filter-btn" 
            [class.active]="currentFilter === 'all'"
            (click)="onFilter('all')">
            Todos
          </button>
          <button 
            class="filter-btn" 
            [class.active]="currentFilter === 'active'"
            (click)="onFilter('active')">
            Activos
          </button>
          <button 
            class="filter-btn" 
            [class.active]="currentFilter === 'inactive'"
            (click)="onFilter('inactive')">
            Inactivos
          </button>
        </div>
      </div>
      
      <div class="table-container">
        <table class="products-table">
          <thead>
            <tr>
              <th>ID</th>
              <th>Nombre</th>
              <th>Descripción</th>
              <th>SKU</th>
              <th>Precio</th>
              <th>Estado</th>
              <th>Fecha Creación</th>
              <th>Acciones</th>
            </tr>
          </thead>
          <tbody>
            <tr *ngFor="let product of filteredProducts">
              <td>{{ product.id }}</td>
              <td>{{ product.name }}</td>
              <td class="description-cell">{{ product.description }}</td>
              <td class="sku-cell">{{ product.sku }}</td>
              <td class="price-cell">{{ product.price | currency }}</td>
              <td>
                <span class="status-badge" [class]="product.isActive ? 'active' : 'inactive'">
                  {{ product.isActive ? 'Activo' : 'Inactivo' }}
                </span>
              </td>
              <td>{{ product.createdAt | date:'dd/MM/yyyy' }}</td>
              <td>
                <div class="action-buttons">
                  <button class="btn-icon btn-edit" (click)="onEdit(product.id)" title="Editar">
                    <i class="material-icons">edit</i>
                  </button>
                  <button class="btn-icon btn-toggle" (click)="onToggleStatus(product.id)" title="Activar/Desactivar">
                    <i class="material-icons">{{ product.isActive ? 'visibility_off' : 'visibility' }}</i>
                  </button>
                  <button class="btn-icon btn-delete" (click)="onDelete(product.id)" title="Eliminar">
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
    .products-container {
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
    
    .filter-buttons {
      display: flex;
      gap: 8px;
    }
    
    .filter-btn {
      padding: 8px 16px;
      border: 1px solid #e2e8f0;
      background: white;
      border-radius: 6px;
      cursor: pointer;
      font-size: 13px;
      transition: all 0.2s ease;
    }
    
    .filter-btn:hover {
      background: #f7fafc;
    }
    
    .filter-btn.active {
      background: #667eea;
      color: white;
      border-color: #667eea;
    }
    
    .table-container {
      background: white;
      border-radius: 12px;
      box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
      overflow: hidden;
    }
    
    .products-table {
      width: 100%;
      border-collapse: collapse;
    }
    
    .products-table th {
      background: #f8f9fa;
      padding: 15px;
      text-align: left;
      font-weight: 600;
      color: #333;
      border-bottom: 2px solid #e2e8f0;
    }
    
    .products-table td {
      padding: 15px;
      border-bottom: 1px solid #f1f1f1;
    }
    
    .products-table tr:hover {
      background-color: #f8f9fa;
    }
    
    .description-cell {
      max-width: 200px;
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
    }
    
    .sku-cell {
      font-family: 'Courier New', monospace;
      font-size: 12px;
      color: #666;
    }
    
    .price-cell {
      font-weight: 500;
      color: #059669;
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
      
      .filter-buttons {
        flex-wrap: wrap;
      }
      
      .products-table {
        font-size: 12px;
      }
      
      .products-table th,
      .products-table td {
        padding: 8px;
      }
    }
  `]
})
export class ProductsListComponent implements OnInit {
  products: any[] = [];
  filteredProducts: any[] = [];
  searchTerm: string = '';
  currentFilter: string = 'all';
  
  // Paginación
  currentPage: number = 1;
  pageSize: number = 10;
  totalPages: number = 0;

  constructor(private router: Router) {}

  ngOnInit(): void {
    this.loadProducts();
  }

  loadProducts(): void {
    // Datos de ejemplo - en producción vendrían del servicio
    this.products = [
      {
        id: 1,
        name: 'iPhone 15 Pro',
        description: 'Smartphone de última generación con chip A17 Pro',
        sku: 'IPH15PRO-001',
        price: 999.99,
        isActive: true,
        createdAt: new Date('2024-01-15')
      },
      {
        id: 2,
        name: 'MacBook Air M2',
        description: 'Laptop ultraliviana con chip M2 de Apple',
        sku: 'MBA-M2-001',
        price: 1199.99,
        isActive: true,
        createdAt: new Date('2024-01-16')
      },
      {
        id: 3,
        name: 'Samsung Galaxy S24',
        description: 'Smartphone Android premium',
        sku: 'SGS24-001',
        price: 899.99,
        isActive: false,
        createdAt: new Date('2024-01-17')
      },
      {
        id: 4,
        name: 'iPad Pro 12.9',
        description: 'Tablet profesional con pantalla Liquid Retina XDR',
        sku: 'IPP129-001',
        price: 1099.99,
        isActive: true,
        createdAt: new Date('2024-01-18')
      }
    ];
    
    this.applyFilters();
  }

  onSearch(): void {
    this.applyFilters();
  }

  onFilter(filter: string): void {
    this.currentFilter = filter;
    this.applyFilters();
  }

  applyFilters(): void {
    let filtered = [...this.products];
    
    // Filtro por búsqueda
    if (this.searchTerm) {
      const term = this.searchTerm.toLowerCase();
      filtered = filtered.filter(product => 
        product.name.toLowerCase().includes(term) ||
        product.sku.toLowerCase().includes(term) ||
        product.description.toLowerCase().includes(term)
      );
    }
    
    // Filtro por estado
    if (this.currentFilter !== 'all') {
      if (this.currentFilter === 'active') {
        filtered = filtered.filter(product => product.isActive);
      } else if (this.currentFilter === 'inactive') {
        filtered = filtered.filter(product => !product.isActive);
      }
    }
    
    this.filteredProducts = filtered;
    this.calculateTotalPages();
  }

  calculateTotalPages(): void {
    this.totalPages = Math.ceil(this.filteredProducts.length / this.pageSize);
  }

  onPageChange(page: number): void {
    this.currentPage = page;
  }

  onCreateProduct(): void {
    this.router.navigate(['/products/create']);
  }

  onEdit(id: number): void {
    this.router.navigate(['/products/edit', id]);
  }

  onToggleStatus(id: number): void {
    const product = this.products.find(p => p.id === id);
    if (product) {
      product.isActive = !product.isActive;
      console.log(`Producto ${id} ${product.isActive ? 'activado' : 'desactivado'}`);
    }
  }

  onDelete(id: number): void {
    if (confirm('¿Estás seguro de que deseas eliminar este producto?')) {
      console.log('Eliminando producto:', id);
      this.products = this.products.filter(p => p.id !== id);
      this.applyFilters();
    }
  }
}