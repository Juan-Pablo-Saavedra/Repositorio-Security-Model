import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Category } from '../models/inventory.model';
import { ApiService } from './api.service';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {
  private readonly endpoint = '/categories';

  constructor(private apiService: ApiService) {}

  getAll(): Observable<Category[]> {
    return this.apiService.getArray<Category>(this.endpoint);
  }

  findById(id: number): Observable<Category> {
    return this.apiService.getSingle<Category>(`${this.endpoint}/${id}`);
  }

  getCategoryByName(name: string): Observable<Category> {
    return this.apiService.getSingle<Category>(`${this.endpoint}/name/${name}`);
  }

  create(category: Category): Observable<Category> {
    return this.apiService.post<Category>(this.endpoint, category);
  }

  update(id: number, category: Category): Observable<Category> {
    return this.apiService.put<Category>(`${this.endpoint}/${id}`, category);
  }

  deleteCategory(id: number): Observable<any> {
    return this.apiService.delete(`${this.endpoint}/${id}`);
  }
}