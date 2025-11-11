import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { map, catchError } from 'rxjs/operators';
import { environment } from '../../../environments/environment';

export interface Category {
  id: number;
  name: string;
  description: string;
  createdAt: Date;
  updatedAt?: Date;
}

export interface CategoryRequest {
  name: string;
  description: string;
}

@Injectable({
  providedIn: 'root'
})
export class CategoryService {
  private readonly baseUrl = `${environment.apiUrl}/categories`;

  constructor(private http: HttpClient) {}

  /**
   * Obtener todas las categorías
   */
  getAll(): Observable<Category[]> {
    return this.http.get<{ success: boolean; data: Category[] }>(this.baseUrl)
      .pipe(
        map(response => response.data),
        catchError(this.handleError)
      );
  }

  /**
   * Obtener categoría por ID
   */
  getById(id: number): Observable<Category> {
    return this.http.get<{ success: boolean; data: Category }>(`${this.baseUrl}/${id}`)
      .pipe(
        map(response => response.data),
        catchError(this.handleError)
      );
  }

  /**
   * Crear nueva categoría
   */
  create(categoryData: CategoryRequest): Observable<Category> {
    return this.http.post<{ success: boolean; data: Category; message: string }>(this.baseUrl, categoryData)
      .pipe(
        map(response => response.data),
        catchError(this.handleError)
      );
  }

  /**
   * Actualizar categoría
   */
  update(id: number, categoryData: CategoryRequest): Observable<Category> {
    return this.http.put<{ success: boolean; data: Category; message: string }>(`${this.baseUrl}/${id}`, categoryData)
      .pipe(
        map(response => response.data),
        catchError(this.handleError)
      );
  }

  /**
   * Eliminar categoría
   */
  delete(id: number): Observable<{ success: boolean; message: string }> {
    return this.http.delete<{ success: boolean; message: string }>(`${this.baseUrl}/${id}`)
      .pipe(
        catchError(this.handleError)
      );
  }

  /**
   * Buscar categorías por nombre
   */
  searchByName(name: string): Observable<Category[]> {
    return this.http.get<{ success: boolean; data: Category[] }>(`${this.baseUrl}/name/${encodeURIComponent(name)}`)
      .pipe(
        map(response => response.data),
        catchError(this.handleError)
      );
  }

  /**
   * Verificar si existe una categoría por nombre
   */
  existsByName(name: string): Observable<boolean> {
    return this.http.get<{ success: boolean; data: boolean }>(`${this.baseUrl}/check-name/${encodeURIComponent(name)}`)
      .pipe(
        map(response => response.data),
        catchError(() => throwError(() => new Error('Error al verificar nombre de categoría')))
      );
  }

  /**
   * Manejo de errores HTTP
   */
  private handleError = (error: any): Observable<never> => {
    console.error('Error en CategoryService:', error);
    let errorMessage = 'Ocurrió un error inesperado';
    
    if (error.error?.message) {
      errorMessage = error.error.message;
    } else if (error.message) {
      errorMessage = error.message;
    }
    
    return throwError(() => new Error(errorMessage));
  }
}