import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule, Router } from '@angular/router';

@Component({
  selector: 'app-suppliers-list',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './suppliers-list.component.html',
  styleUrl: './suppliers-list.component.css'
})
export class SuppliersListComponent implements OnInit {
  suppliers: any[] = [];
  filteredSuppliers: any[] = [];
  searchTerm: string = '';
  currentFilter: string = 'all';
  
  // Paginación
  currentPage: number = 1;
  pageSize: number = 10;
  totalPages: number = 0;

  constructor(private router: Router) {}

  ngOnInit(): void {
    this.loadSuppliers();
  }

  loadSuppliers(): void {
    // Datos de ejemplo - en producción vendrían del servicio
    this.suppliers = [
      {
        id: 1,
        name: 'TechCorp Supplies',
        email: 'contacto@techcorp.com',
        phone: '+57 300 123 4567',
        address: 'Calle 45 #20-30, Bogotá',
        isActive: true,
        createdAt: new Date('2024-01-15')
      },
      {
        id: 2,
        name: 'Global Electronics',
        email: 'ventas@globalelectronic.com',
        phone: '+57 301 987 6543',
        address: 'Carrera 15 #93-15, Bogotá',
        isActive: true,
        createdAt: new Date('2024-01-16')
      },
      {
        id: 3,
        name: 'Local Distributors',
        email: 'info@localdist.com',
        phone: '+57 302 555 0123',
        address: 'Avenida 68 #45-23, Bogotá',
        isActive: false,
        createdAt: new Date('2024-01-17')
      },
      {
        id: 4,
        name: 'Industrial Partners',
        email: 'contacto@industrial.com',
        phone: '+57 303 777 8888',
        address: 'Zona Industrial, Cota',
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
    let filtered = [...this.suppliers];
    
    // Filtro por búsqueda
    if (this.searchTerm) {
      const term = this.searchTerm.toLowerCase();
      filtered = filtered.filter(supplier => 
        supplier.name.toLowerCase().includes(term) ||
        supplier.email.toLowerCase().includes(term) ||
        supplier.phone.toLowerCase().includes(term)
      );
    }
    
    // Filtro por estado
    if (this.currentFilter !== 'all') {
      if (this.currentFilter === 'active') {
        filtered = filtered.filter(supplier => supplier.isActive);
      } else if (this.currentFilter === 'inactive') {
        filtered = filtered.filter(supplier => !supplier.isActive);
      }
    }
    
    this.filteredSuppliers = filtered;
    this.calculateTotalPages();
  }

  calculateTotalPages(): void {
    this.totalPages = Math.ceil(this.filteredSuppliers.length / this.pageSize);
  }

  onPageChange(page: number): void {
    this.currentPage = page;
  }

  onCreateSupplier(): void {
    this.router.navigate(['/suppliers/create']);
  }

  onEdit(id: number): void {
    this.router.navigate(['/suppliers/edit', id]);
  }

  onToggleStatus(id: number): void {
    const supplier = this.suppliers.find(s => s.id === id);
    if (supplier) {
      supplier.isActive = !supplier.isActive;
      console.log(`Proveedor ${id} ${supplier.isActive ? 'activado' : 'desactivado'}`);
    }
  }

  onDelete(id: number): void {
    if (confirm('¿Estás seguro de que deseas eliminar este proveedor?')) {
      console.log('Eliminando proveedor:', id);
      this.suppliers = this.suppliers.filter(s => s.id !== id);
      this.applyFilters();
    }
  }
}