import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-categories-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './categories-list.component.html',
  styleUrl: './categories-list.component.css'
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