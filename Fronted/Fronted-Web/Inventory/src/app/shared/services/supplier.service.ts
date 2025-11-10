import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Supplier } from '../models/inventory.model';
import { ApiService } from './api.service';

@Injectable({
  providedIn: 'root'
})
export class SupplierService {
  private readonly endpoint = '/suppliers';

  constructor(private apiService: ApiService) {}

  getAll(): Observable<Supplier[]> {
    return this.apiService.getArray<Supplier>(this.endpoint);
  }

  findById(id: number): Observable<Supplier> {
    return this.apiService.getSingle<Supplier>(`${this.endpoint}/${id}`);
  }

  create(supplier: Supplier): Observable<Supplier> {
    return this.apiService.post<Supplier>(this.endpoint, supplier);
  }

  update(id: number, supplier: Supplier): Observable<Supplier> {
    return this.apiService.put<Supplier>(`${this.endpoint}/${id}`, supplier);
  }

  deleteSupplier(id: number): Observable<any> {
    return this.apiService.delete(`${this.endpoint}/${id}`);
  }
}