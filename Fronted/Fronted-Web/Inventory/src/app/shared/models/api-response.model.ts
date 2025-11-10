export interface ApiResponse<T = any> {
  success: boolean;
  message: string;
  data: T;
  timestamp?: Date;
}

export interface PaginatedResponse<T> {
  content: T[];
  totalElements: number;
  totalPages: number;
  currentPage: number;
  pageSize: number;
}

export interface ApiError {
  success: false;
  message: string;
  errors?: string[];
  timestamp: Date;
}