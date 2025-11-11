import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule, Router } from '@angular/router';

@Component({
  selector: 'app-inventory-list',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './inventory-list.component.html',
  styleUrl: './inventory-list.component.css'
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