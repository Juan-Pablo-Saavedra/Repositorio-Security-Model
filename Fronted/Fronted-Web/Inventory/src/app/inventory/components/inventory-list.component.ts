import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-inventory-list',
  template: `
    <div class="inventory-container">
      <div class="header-section">
        <h2>Gestión de Inventario</h2>
        <button class="btn-primary" (click)="onAddItem()">
          <i class="material-icons">add</i>
          Agregar Producto
        </button>
      </div>
      
      <div class="stats-cards">
        <div class="stat-card">
          <div class="stat-icon">
            <i class="material-icons">inventory_2</i>
          </div>
          <div class="stat-content">
            <h3>{{ totalProducts }}</h3>
            <p>Total Productos</p>
          </div>
        </div>
        
        <div class="stat-card low-stock">
          <div class="stat-icon">
            <i class="material-icons">warning</i>
          </div>
          <div class="stat-content">
            <h3>{{ lowStockCount }}</h3>
            <p>Stock Bajo</p>
          </div>
        </div>
        
        <div class="stat-card">
          <div class="stat-icon">
            <i class="material-icons">attach_money</i>
          </div>
          <div class="stat-content">
            <h3>{{ totalValue | currency }}</h3>
            <p>Valor Total</p>
          </div>
        </div>
      </div>
      
      <div class="search-section">
        <div class="search-field">
          <i class="material-icons">search</i>
          <input type="text" placeholder="Buscar productos..." [(ngModel)]="searchTerm" (ngModelChange)="onSearch()">
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
            [class.active]="currentFilter === 'low'"
            (click)="onFilter('low')">
            Stock Bajo
          </button>
          <button 
            class="filter-btn" 
            [class.active]="currentFilter === 'normal'"
            (click)="onFilter('normal')">
            Stock Normal
          </button>
        </div>
      </div>
      
      <div class="table-container">
        <table class="inventory-table">
          <thead>
            <tr>
              <th>Producto</th>
              <th>Categoría</th>
              <th>SKU</th>
              <th>Stock</th>
              <th>Precio</th>
              <th>Valor Total</th>
              <th>Estado</th>
              <th>Acciones</th>
            </tr>
          </thead>
          <tbody>
            <tr *ngFor="let item of filteredInventory">
              <td>
                <div class="product-info">
                  <div class="product-name">{{ item.name }}</div>
                  <div class="product-description">{{ item.description }}</div>
                </div>
              </td>
              <td>{{ item.category }}</td>
              <td class="sku-cell">{{ item.sku }}</td>
              <td>
                <span class="stock-badge" [class.low-stock]="item.stock <= item.minStock">
                  {{ item.stock }}
                </span>
              </td>
              <td>{{ item.price | currency }}</td>
              <td>{{ (item.price * item.stock) | currency }}</td>
              <td>
                <span class="status-badge" [class]="item.status.toLowerCase()">
                  {{ getStatusText(item.status) }}
                </span>
              </td>
              <td>
                <div class="action-buttons">
                  <button class="btn-icon btn-edit" (click)="onEdit(item.id)" title="Editar">
                    <i class="material-icons">edit</i>
                  </button>
                  <button class="btn-icon btn-view" (click)="onView(item.id)" title="Ver detalles">
                    <i class="material-icons">visibility</i>
                  </button>
                  <button class="btn-icon btn-delete" (click)="onDelete(item.id)" title="Eliminar">
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
    .inventory-container {
      padding: 20px;
      max-width: 1400px;
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
    
    .stats-cards {
      display: grid;
      grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
      gap: 20px;
      margin-bottom: 30px;
    }
    
    .stat-card {
      background: white;
      border-radius: 12px;
      padding: 20px;
      box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
      display: flex;
      align-items: center;
      gap: 15px;
      transition: transform 0.2s ease;
    }
    
    .stat-card:hover {
      transform: translateY(-2px);
      box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
    }
    
    .stat-card.low-stock {
      background: linear-gradient(135deg, #fff5f5 0%, #fed7d7 100%);
      border-left: 4px solid #f56565;
    }
    
    .stat-icon {
      width: 48px;
      height: 48px;
      border-radius: 12px;
      background: linear-gradient(135deg, #667eea, #764ba2);
      display: flex;
      align-items: center;
      justify-content: center;
      color: white;
    }
    
    .low-stock .stat-icon {
      background: linear-gradient(135deg, #f56565, #c53030);
    }
    
    .stat-content h3 {
      margin: 0;
      font-size: 24px;
      font-weight: 700;
      color: #333;
    }
    
    .stat-content p {
      margin: 4px 0 0 0;
      color: #666;
      font-size: 14px;
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
    }
    
    .search-field {
      position: relative;
      max-width: 300px;
      margin-bottom: 15px;
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
    
    .inventory-table {
      width: 100%;
      border-collapse: collapse;
    }
    
    .inventory-table th {
      background: #f8f9fa;
      padding: 15px;
      text-align: left;
      font-weight: 600;
      color: #333;
      border-bottom: 2px solid #e2e8f0;
    }
    
    .inventory-table td {
      padding: 15px;
      border-bottom: 1px solid #f1f1f1;
    }
    
    .inventory-table tr:hover {
      background-color: #f8f9fa;
    }
    
    .product-info {
      display: flex;
      flex-direction: column;
    }
    
    .product-name {
      font-weight: 500;
      color: #333;
    }
    
    .product-description {
      font-size: 12px;
      color: #666;
      margin-top: 2px;
    }
    
    .sku-cell {
      font-family: 'Courier New', monospace;
      font-size: 12px;
      color: #666;
    }
    
    .stock-badge {
      padding: 4px 8px;
      border-radius: 4px;
      font-size: 12px;
      font-weight: 500;
      background: #e6fffa;
      color: #065f46;
    }
    
    .stock-badge.low-stock {
      background: #fef2f2;
      color: #991b1b;
    }
    
    .status-badge {
      padding: 4px 8px;
      border-radius: 4px;
      font-size: 12px;
      font-weight: 500;
    }
    
    .status-badge.available {
      background: #d1fae5;
      color: #065f46;
    }
    
    .status-badge.out {
      background: #fee2e2;
      color: #991b1b;
    }
    
    .status-badge.discontinued {
      background: #f3f4f6;
      color: #6b7280;
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
    
    .btn-view {
      color: #10b981;
    }
    
    .btn-view:hover {
      background-color: #d1fae5;
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
      
      .stats-cards {
        grid-template-columns: 1fr;
      }
      
      .search-section {
        padding: 15px;
      }
      
      .filter-buttons {
        flex-wrap: wrap;
      }
      
      .inventory-table {
        font-size: 12px;
      }
      
      .inventory-table th,
      .inventory-table td {
        padding: 8px;
      }
    }
  `]
})
export class InventoryListComponent implements OnInit {
  inventory: any[] = [];
  filteredInventory: any[] = [];
  searchTerm: string = '';
  currentFilter: string = 'all';
  
  // Estadísticas
  totalProducts: number = 0;
  lowStockCount: number = 0;
  totalValue: number = 0;
  
  // Paginación
  currentPage: number = 1;
  pageSize: number = 10;
  totalPages: number = 0;

  constructor(private router: Router) {}

  ngOnInit(): void {
    this.loadInventory();
  }

  loadInventory(): void {
    // Datos de ejemplo - en producción vendrían del servicio
    this.inventory = [
      {
        id: 1,
        name: 'iPhone 15 Pro',
        description: 'Smartphone de última generación',
        category: 'Electrónicos',
        sku: 'IPH15PRO-001',
        stock: 25,
        minStock: 10,
        price: 999.99,
        status: 'AVAILABLE'
      },
      {
        id: 2,
        name: 'MacBook Air M2',
        description: 'Laptop ultraliviana con chip M2',
        category: 'Electrónicos',
        sku: 'MBA-M2-001',
        stock: 5,
        minStock: 15,
        price: 1199.99,
        status: 'LOW'
      },
      {
        id: 3,
        name: 'Camiseta Básica',
        description: 'Camiseta de algodón 100%',
        category: 'Ropa',
        sku: 'TSH-BASIC-001',
        stock: 150,
        minStock: 20,
        price: 19.99,
        status: 'AVAILABLE'
      },
      {
        id: 4,
        name: 'Zapatillas Deportivas',
        description: 'Zapatillas para correr',
        category: 'Deportes',
        sku: 'SHO-RUN-001',
        stock: 0,
        minStock: 5,
        price: 89.99,
        status: 'OUT'
      }
    ];
    
    this.calculateStats();
    this.applyFilters();
  }

  calculateStats(): void {
    this.totalProducts = this.inventory.length;
    this.lowStockCount = this.inventory.filter(item => item.stock <= item.minStock).length;
    this.totalValue = this.inventory.reduce((sum, item) => sum + (item.price * item.stock), 0);
  }

  onSearch(): void {
    this.applyFilters();
  }

  onFilter(filter: string): void {
    this.currentFilter = filter;
    this.applyFilters();
  }

  applyFilters(): void {
    let filtered = [...this.inventory];
    
    // Filtro por búsqueda
    if (this.searchTerm) {
      const term = this.searchTerm.toLowerCase();
      filtered = filtered.filter(item => 
        item.name.toLowerCase().includes(term) ||
        item.sku.toLowerCase().includes(term) ||
        item.category.toLowerCase().includes(term)
      );
    }
    
    // Filtro por estado
    if (this.currentFilter !== 'all') {
      if (this.currentFilter === 'low') {
        filtered = filtered.filter(item => item.stock <= item.minStock);
      } else if (this.currentFilter === 'normal') {
        filtered = filtered.filter(item => item.stock > item.minStock);
      }
    }
    
    this.filteredInventory = filtered;
    this.calculateTotalPages();
  }

  calculateTotalPages(): void {
    this.totalPages = Math.ceil(this.filteredInventory.length / this.pageSize);
  }

  onPageChange(page: number): void {
    this.currentPage = page;
    // En una implementación real, aquí cargarías la página específica
  }

  getStatusText(status: string): string {
    const statusMap: { [key: string]: string } = {
      'AVAILABLE': 'Disponible',
      'LOW': 'Stock Bajo',
      'OUT': 'Agotado',
      'DISCONTINUED': 'Descontinuado'
    };
    return statusMap[status] || status;
  }

  onAddItem(): void {
    this.router.navigate(['/inventory/add']);
  }

  onEdit(id: number): void {
    this.router.navigate(['/inventory/edit', id]);
  }

  onView(id: number): void {
    console.log('Ver detalles del producto:', id);
    // Implementar vista de detalles
  }

  onDelete(id: number): void {
    if (confirm('¿Estás seguro de que deseas eliminar este producto del inventario?')) {
      console.log('Eliminando producto:', id);
      // Implementar eliminación
      this.loadInventory();
    }
  }
}