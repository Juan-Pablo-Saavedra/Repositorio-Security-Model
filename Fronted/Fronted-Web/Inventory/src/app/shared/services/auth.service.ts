import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { environment } from '../../../environments/environment';
import { User, AuthRequest, AuthResponse, RegisterRequest } from '../models/user.model';
import { ApiResponse } from '../models/api-response.model';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private readonly baseUrl = environment.apiUrl;
  private readonly tokenKey = environment.jwtTokenKey;
  private readonly userDataKey = environment.userDataKey;

  private currentUserSubject = new BehaviorSubject<User | null>(null);
  public currentUser$ = this.currentUserSubject.asObservable();

  private isAuthenticatedSubject = new BehaviorSubject<boolean>(false);
  public isAuthenticated$ = this.isAuthenticatedSubject.asObservable();

  constructor(private http: HttpClient) {
    this.initializeAuth();
  }

  private initializeAuth(): void {
    const token = this.getToken();
    const userData = this.getUserData();
    
    if (token && userData) {
      this.isAuthenticatedSubject.next(true);
      this.currentUserSubject.next(userData);
    }
  }

  login(credentials: AuthRequest): Observable<ApiResponse<AuthResponse>> {
    return this.http.post<ApiResponse<AuthResponse>>(`${this.baseUrl}/users/login`, credentials)
      .pipe(
        tap(response => {
          if (response.success && response.data) {
            this.setToken(response.data.token);
            this.setUserData({
              id: response.data.id,
              username: response.data.username,
              email: response.data.email,
              firstName: response.data.firstName,
              lastName: response.data.lastName
            });
            
            this.isAuthenticatedSubject.next(true);
            this.currentUserSubject.next(response.data);
          }
        })
      );
  }

  register(userData: RegisterRequest): Observable<ApiResponse<User>> {
    return this.http.post<ApiResponse<User>>(`${this.baseUrl}/users/register`, userData);
  }

  logout(): void {
    this.removeToken();
    this.removeUserData();
    this.isAuthenticatedSubject.next(false);
    this.currentUserSubject.next(null);
  }

  getToken(): string | null {
    return localStorage.getItem(this.tokenKey);
  }

  getUserData(): User | null {
    const userData = localStorage.getItem(this.userDataKey);
    return userData ? JSON.parse(userData) : null;
  }

  private setToken(token: string): void {
    localStorage.setItem(this.tokenKey, token);
  }

  private setUserData(user: User): void {
    localStorage.setItem(this.userDataKey, JSON.stringify(user));
  }

  private removeToken(): void {
    localStorage.removeItem(this.tokenKey);
  }

  private removeUserData(): void {
    localStorage.removeItem(this.userDataKey);
  }

  get currentUser(): User | null {
    return this.currentUserSubject.value;
  }

  get isAuthenticated(): boolean {
    return this.isAuthenticatedSubject.value;
  }

  hasRole(role: string): boolean {
    // Implementar lógica de roles si es necesario
    return true; // Por ahora retornamos true para todos los usuarios autenticados
  }
}