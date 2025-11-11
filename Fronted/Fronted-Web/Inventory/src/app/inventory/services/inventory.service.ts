import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, throwError, of } from 'rxjs';
import { map, catchError } from 'rxjs/operators';
import { environment } from '../../../environments/environment';

export interface InventoryItem {
  id: number;
  name: string;
  description: string;
  category: string;
  sku: string;
  stock: number;
  minStock: number;
  price: number;
  cost: number;
  supplier: string;
  location: string;
  status: 'AVAILABLE' | 'LOW' | 'OUT' | 'DISCONTINUED';
  notes: string;
  createdAt: Date;
  updatedAt?: Date;
}

export interface InventoryRequest {
  name: string;
  description: string;
  category: string;
  sku: string;
  price: number;
  cost: number;
  stock: number;
  minStock: number;
  supplier?: string;
  location?: string;
  status: 'AVAILABLE' | 'LOW' | 'OUT' | 'DISCONTINUED';
  notes?: string;
}

export interface InventoryStats {
  totalProducts: number;
  lowStockCount: number;
  outOfStockCount: number;
  totalValue: number;
  averagePrice: number;
}

@Injectable({
  providedIn: 'root'
})
export class InventoryService {
  private readonly baseUrl = `${environment.apiUrl}/inventory`;

  constructor(private http: HttpClient) {}

  /**
   * Obtener todos los productos del inventario
   */
  getAll(): Observable<InventoryItem[]> {
    return this.http.get<{ success: boolean; data: InventoryItem[] }>(this.baseUrl)
      .pipe(
        map(response => response.data),
        catchError(this.handleError)
      );
  }

  /**
   * Obtener producto por ID
   */
  getById(id: number): Observable<InventoryItem> {
    return this.http.get<{ success: boolean; data: InventoryItem }>(`${this.baseUrl}/${id}`)
      .pipe(
        map(response => response.data),
        catchError(this.handleError)
      );
  }

  /**
   * Crear nuevo producto
   */
  create(productData: InventoryRequest): Observable<InventoryItem> {
    return this.http.post<{ success: boolean; data: InventoryItem; message: string }>(this.baseUrl, productData)
      .pipe(
        map(response => response.data),
        catchError(this.handleError)
      );
  }

  /**
   * Actualizar producto
   */
  update(id: number, productData: InventoryRequest): Observable<InventoryItem> {
    return this.http.put<{ success: boolean; data: InventoryItem; message: string }>(`${this.baseUrl}/${id}`, productData)
      .pipe(
        map(response => response.data),
        catchError(this.handleError)
      );
  }

  /**
   * Eliminar producto
   */
  delete(id: number): Observable<{ success: boolean; message: string }> {
    return this.http.delete<{ success: boolean; message: string }>(`${this.baseUrl}/${id}`)
      .pipe(
        catchError(this.handleError)
      );
  }

  /**
   * Buscar productos por nombre o SKU
   */
  search(query: string): Observable<InventoryItem[]> {
    return this.http.get<{ success: boolean; data: InventoryItem[] }>(`${this.baseUrl}/search?q=${encodeURIComponent(query)}`)
      .pipe(
        map(response => response.data),
        catchError(this.handleError)
      );
  }

  /**
   * Obtener productos con stock bajo
   */
  getLowStock(): Observable<InventoryItem[]> {
    return this.http.get<{ success: boolean; data: InventoryItem[] }>(`${this.baseUrl}/low-stock`)
      .pipe(
        map(response => response.data),
        catchError(this.handleError)
      );
  }

  /**
   * Obtener productos agotados
   */
  getOutOfStock(): Observable<InventoryItem[]> {
    return this.http.get<{ success: boolean; data: InventoryItem[] }>(`${this.baseUrl}/out-of-stock`)
      .pipe(
        map(response => response.data),
        catchError(this.handleError)
      );
  }

  /**
   * Obtener estadísticas del inventario
   */
  getStats(): Observable<InventoryStats> {
    return this.http.get<{ success: boolean; data: InventoryStats }>(`${this.baseUrl}/stats`)
      .pipe(
        map(response => response.data),
        catchError(this.handleError)
      );
  }

  /**
   * Actualizar stock de un producto
   */
  updateStock(id: number, newStock: number): Observable<InventoryItem> {
    return this.http.patch<{ success: boolean; data: InventoryItem; message: string }>(`${this.baseUrl}/${id}/stock`, { stock: newStock })
      .pipe(
        map(response => response.data),
        catchError(this.handleError)
      );
  }

  /**
   * Importar productos desde archivo CSV
   */
  importFromCSV(file: File): Observable<{ success: boolean; message: string; imported: number; errors: string[] }> {
    const formData = new FormData();
    formData.append('file', file);

    return this.http.post<{ success: boolean; message: string; imported: number; errors: string[] }>(`${this.baseUrl}/import`, formData)
      .pipe(
        catchError(this.handleError)
      );
  }

  /**
   * Exportar inventario a CSV
   */
  exportToCSV(): Observable<Blob> {
    return this.http.get(`${this.baseUrl}/export`, { 
      responseType: 'blob' 
    }).pipe(
      catchError(error => {
        console.error('Error al exportar inventario:', error);
        return throwError(() => new Error('Error al exportar inventario'));
      })
    );
  }

  /**
   * Obtener productos por categoría
   */
  getByCategory(category: string): Observable<InventoryItem[]> {
    return this.http.get<{ success: boolean; data: InventoryItem[] }>(`${this.baseUrl}/category/${encodeURIComponent(category)}`)
      .pipe(
        map(response => response.data),
        catchError(this.handleError)
      );
  }

  /**
   * Verificar disponibilidad de SKU
   */
  checkSkuAvailability(sku: string): Observable<boolean> {
    return this.http.get<{ success: boolean; data: boolean }>(`${this.baseUrl}/check-sku/${encodeURIComponent(sku)}`)
      .pipe(
        map(response => response.data),
        catchError(() => throwError(() => new Error('Error al verificar SKU')))
      );
  }

  /**
   * Obtener valor total del inventario
   */
  getTotalValue(): Observable<number> {
    return this.http.get<{ success: boolean; data: number }>(`${this.baseUrl}/total-value`)
      .pipe(
        map(response => response.data),
        catchError(this.handleError)
      );
  }

  /**
   * Obtener productos más vendidos (mock data)
   */
  getTopSelling(limit: number = 10): Observable<InventoryItem[]> {
    // En una implementación real, esto vendría del backend
    return this.getAll().pipe(
      map(products => products.slice(0, limit)),
      catchError(() => throwError(() => new Error('Error al obtener productos más vendidos')))
    );
  }

  /**
   * Manejo de errores HTTP
   */
  private handleError = (error: any): Observable<never> => {
    console.error('Error en InventoryService:', error);
    let errorMessage = 'Ocurrió un error inesperado';
    
    if (error.error?.message) {
      errorMessage = error.error.message;
    } else if (error.message) {
      errorMessage = error.message;
    }
    
    return throwError(() => new Error(errorMessage));
  }
}