import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule, Router } from '@angular/router';

@Component({
  selector: 'app-products-list',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './products-list.component.html',
  styleUrl: './products-list.component.css'
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
        sku: 'IPH15PRO-001',
        category: 'Electrónicos',
        price: 999.99,
        stock: 25,
        minStock: 10,
        isActive: true,
        createdAt: new Date('2024-01-15')
      },
      {
        id: 2,
        name: 'MacBook Pro 16"',
        sku: 'MBP16-002',
        category: 'Electrónicos',
        price: 2499.99,
        stock: 8,
        minStock: 5,
        isActive: true,
        createdAt: new Date('2024-01-16')
      },
      {
        id: 3,
        name: 'Samsung Galaxy S24',
        sku: 'SGS24-003',
        category: 'Electrónicos',
        price: 799.99,
        stock: 2,
        minStock: 10,
        isActive: true,
        createdAt: new Date('2024-01-17')
      },
      {
        id: 4,
        name: 'Sony WH-1000XM5',
        sku: 'WH1000XM5-004',
        category: 'Electrónicos',
        price: 349.99,
        stock: 15,
        minStock: 8,
        isActive: false,
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
        product.category.toLowerCase().includes(term)
      );
    }

    // Filtro por estado
    if (this.currentFilter !== 'all') {
      if (this.currentFilter === 'active') {
        filtered = filtered.filter(product => product.isActive);
      } else if (this.currentFilter === 'inactive') {
        filtered = filtered.filter(product => !product.isActive);
      } else if (this.currentFilter === 'low-stock') {
        filtered = filtered.filter(product => product.stock <= product.minStock);
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