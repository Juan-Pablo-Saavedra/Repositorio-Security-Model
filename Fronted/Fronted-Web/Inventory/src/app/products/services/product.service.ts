import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Product } from '../models/inventory.model';
import { ApiService } from './api.service';

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  private readonly endpoint = '/products';

  constructor(private apiService: ApiService) {}

  getAll(): Observable<Product[]> {
    return this.apiService.getArray<Product>(this.endpoint);
  }

  findById(id: number): Observable<Product> {
    return this.apiService.getSingle<Product>(`${this.endpoint}/${id}`);
  }

  getProductBySku(sku: string): Observable<Product> {
    return this.apiService.getSingle<Product>(`${this.endpoint}/sku/${sku}`);
  }

  searchProductsByName(name: string): Observable<Product[]> {
    return this.apiService.getArray<Product>(`${this.endpoint}/search?name=${name}`);
  }

  getProductsByPriceRange(minPrice: number, maxPrice: number): Observable<Product[]> {
    return this.apiService.getArray<Product>(`${this.endpoint}/price-range?minPrice=${minPrice}&maxPrice=${maxPrice}`);
  }

  create(product: Product): Observable<Product> {
    return this.apiService.post<Product>(this.endpoint, product);
  }

  update(id: number, product: Product): Observable<Product> {
    return this.apiService.put<Product>(`${this.endpoint}/${id}`, product);
  }

  deleteProduct(id: number): Observable<any> {
    return this.apiService.delete(`${this.endpoint}/${id}`);
  }
}