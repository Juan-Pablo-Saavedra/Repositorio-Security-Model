export interface Product {
  id?: number;
  name: string;
  sku: string;
  description?: string;
  price: number;
  stock?: number;
  minStock?: number;
  category?: Category;
  categoryId?: number;
  supplier?: Supplier;
  supplierId?: number;
  imageUrl?: string;
  isActive?: boolean;
  createdAt?: Date;
  updatedAt?: Date;
}

export interface Category {
  id?: number;
  name: string;
  description?: string;
  isActive?: boolean;
  createdAt?: Date;
  updatedAt?: Date;
}

export interface Supplier {
  id?: number;
  name: string;
  contactEmail: string;
  contactPhone?: string;
  address?: string;
  isActive?: boolean;
  createdAt?: Date;
  updatedAt?: Date;
}

export interface Client {
  id?: number;
  name: string;
  email: string;
  phone?: string;
  address?: string;
  isActive?: boolean;
  createdAt?: Date;
  updatedAt?: Date;
}

export interface Order {
  id?: number;
  client: Client;
  clientId?: number;
  total: number;
  status: 'PENDING' | 'CONFIRMED' | 'SHIPPED' | 'DELIVERED' | 'CANCELLED';
  orderDate: Date;
  notes?: string;
  createdAt?: Date;
  updatedAt?: Date;
}