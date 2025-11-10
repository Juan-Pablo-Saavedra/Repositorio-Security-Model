import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { AuthService } from '../../shared/services/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    RouterModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule,
    MatProgressSpinnerModule,
    MatSnackBarModule
  ],
  template: `
    <div class="login-container">
      <mat-card class="login-card">
        <!-- Logo y Título -->
        <div class="login-header">
          <svg class="logo" width="64" height="64" viewBox="0 0 32 32" fill="none" xmlns="http://www.w3.org/2000/svg">
            <rect width="32" height="32" rx="8" fill="url(#logo-gradient)"/>
            <path d="M16 8L8 12V20L16 24L24 20V12L16 8Z" fill="white" opacity="0.9"/>
            <path d="M16 12L12 14V18L16 20L20 18V14L16 12Z" fill="white"/>
            <defs>
              <linearGradient id="logo-gradient" x1="0" y1="0" x2="32" y2="32" gradientUnits="userSpaceOnUse">
                <stop stop-color="#667eea"/>
                <stop offset="1" stop-color="#764ba2"/>
              </linearGradient>
            </defs>
          </svg>
          <h1>InventarioPro</h1>
          <p>Sistema de Gestión de Inventarios</p>
        </div>

        <!-- Formulario de Login -->
        <form class="login-form" [formGroup]="loginForm" (ngSubmit)="onSubmit()">
          <mat-form-field appearance="outline" class="full-width">
            <mat-label>Usuario o Email</mat-label>
            <input 
              matInput 
              type="text" 
              formControlName="username" 
              placeholder="admin@inventario.com"
              required>
            <mat-icon matPrefix>person</mat-icon>
            <mat-error *ngIf="loginForm.get('username')?.hasError('required')">
              El usuario es requerido
            </mat-error>
          </mat-form-field>

          <mat-form-field appearance="outline" class="full-width">
            <mat-label>Contraseña</mat-label>
            <input 
              matInput 
              [type]="hidePassword ? 'password' : 'text'" 
              formControlName="password" 
              placeholder="••••••••"
              required>
            <mat-icon matPrefix>lock</mat-icon>
            <button 
              mat-icon-button 
              matSuffix 
              type="button"
              (click)="hidePassword = !hidePassword"
              [attr.aria-label]="'Ocultar contraseña'"
              [attr.aria-pressed]="hidePassword">
              <mat-icon>{{hidePassword ? 'visibility_off' : 'visibility'}}</mat-icon>
            </button>
            <mat-error *ngIf="loginForm.get('password')?.hasError('required')">
              La contraseña es requerida
            </mat-error>
          </mat-form-field>

          <div class="login-options">
            <label class="remember-me">
              <input type="checkbox" formControlName="rememberMe">
              <span>Recordarme</span>
            </label>
            <a href="#" class="forgot-password">¿Olvidaste tu contraseña?</a>
          </div>

          <button 
            mat-raised-button 
            color="primary" 
            class="login-button"
            type="submit"
            [disabled]="loginForm.invalid || isLoading">
            <mat-spinner *ngIf="isLoading" diameter="20" class="inline-spinner"></mat-spinner>
            <mat-icon *ngIf="!isLoading">login</mat-icon>
            <span *ngIf="!isLoading">Iniciar Sesión</span>
          </button>

          <div class="demo-credentials">
            <p class="demo-title">
              <mat-icon>info</mat-icon>
              Credenciales de prueba
            </p>
            <p>Usuario: <strong>admin</strong></p>
            <p>Contraseña: <strong>admin123</strong></p>
          </div>
        </form>
      </mat-card>
    </div>
  `,
  styles: [`
    .login-container {
      min-height: 100vh;
      display: flex;
      align-items: center;
      justify-content: center;
      padding: 1rem;
      background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
      position: relative;
      overflow: hidden;
    }

    .login-container::before {
      content: '';
      position: absolute;
      top: 0;
      left: 0;
      right: 0;
      bottom: 0;
      background: url('data:image/svg+xml,<svg width="60" height="60" xmlns="http://www.w3.org/2000/svg"><rect width="60" height="60" fill="none"/><circle cx="30" cy="30" r="1.5" fill="white" opacity="0.1"/></svg>');
      animation: fadeIn 1s ease-out;
    }

    @keyframes fadeIn {
      from { opacity: 0; }
      to { opacity: 1; }
    }

    .login-card {
      width: 100%;
      max-width: 420px;
      padding: 3rem 2rem !important;
      border-radius: 20px !important;
      box-shadow: 0 20px 60px rgba(0, 0, 0, 0.3) !important;
      animation: slideUp 0.5s ease-out;
      position: relative;
      z-index: 1;
    }

    @keyframes slideUp {
      from {
        opacity: 0;
        transform: translateY(30px);
      }
      to {
        opacity: 1;
        transform: translateY(0);
      }
    }

    .login-header {
      text-align: center;
      margin-bottom: 2rem;
    }

    .logo {
      width: 64px;
      height: 64px;
      margin: 0 auto 1rem;
      filter: drop-shadow(0 4px 8px rgba(0, 0, 0, 0.1));
      animation: float 3s ease-in-out infinite;
    }

    @keyframes float {
      0%, 100% { transform: translateY(0px); }
      50% { transform: translateY(-10px); }
    }

    .login-header h1 {
      font-size: 1.75rem;
      font-weight: 700;
      margin: 0 0 0.5rem 0;
      background: linear-gradient(135deg, #667eea, #764ba2);
      -webkit-background-clip: text;
      -webkit-text-fill-color: transparent;
      background-clip: text;
    }

    .login-header p {
      font-size: 0.875rem;
      color: var(--text-secondary);
      margin: 0;
    }

    .login-form {
      display: flex;
      flex-direction: column;
      gap: 1rem;
    }

    .full-width {
      width: 100%;
    }

    .login-options {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin: -0.5rem 0 0.5rem 0;
      font-size: 0.875rem;
    }

    .remember-me {
      display: flex;
      align-items: center;
      gap: 0.5rem;
      cursor: pointer;
      color: var(--text-secondary);
    }

    .remember-me input[type="checkbox"] {
      cursor: pointer;
    }

    .forgot-password {
      color: #667eea;
      text-decoration: none;
      font-weight: 500;
      transition: color 0.2s ease;
    }

    .forgot-password:hover {
      color: #764ba2;
      text-decoration: underline;
    }

    .login-button {
      width: 100%;
      height: 48px;
      font-size: 1rem;
      font-weight: 600;
      margin-top: 0.5rem;
      border-radius: 12px !important;
      box-shadow: 0 4px 12px rgba(102, 126, 234, 0.4) !important;
      transition: all 0.3s ease;
    }

    .login-button:hover:not([disabled]) {
      transform: translateY(-2px);
      box-shadow: 0 6px 20px rgba(102, 126, 234, 0.5) !important;
    }

    .login-button mat-icon {
      margin-right: 0.5rem;
    }

    .inline-spinner {
      margin-right: 8px;
      display: inline-block;
    }

    .demo-credentials {
      margin-top: 1.5rem;
      padding: 1rem;
      background: var(--color-info-light);
      border-radius: 12px;
      border-left: 4px solid var(--color-info);
    }

    .demo-title {
      display: flex;
      align-items: center;
      gap: 0.5rem;
      font-size: 0.875rem;
      font-weight: 600;
      color: var(--color-info-dark);
      margin: 0 0 0.5rem 0;
    }

    .demo-title mat-icon {
      font-size: 18px;
      width: 18px;
      height: 18px;
    }

    .demo-credentials p {
      font-size: 0.875rem;
      margin: 0.25rem 0;
      color: var(--text-secondary);
    }

    .demo-credentials strong {
      color: var(--text-primary);
      font-weight: 600;
    }

    @media (max-width: 480px) {
      .login-card {
        padding: 2rem 1.5rem !important;
      }

      .login-header h1 {
        font-size: 1.5rem;
      }
    }
  `]
})
export class LoginComponent implements OnInit {
  loginForm: FormGroup;
  isLoading = false;
  hidePassword = true;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router,
    private snackBar: MatSnackBar
  ) {
    this.loginForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required],
      rememberMe: [false]
    });
  }

  ngOnInit(): void {
    // Redirigir si ya está autenticado
    this.authService.isAuthenticated$.subscribe(
      isAuth => {
        if (isAuth) {
          this.router.navigate(['/dashboard']);
        }
      }
    );
  }

  onSubmit(): void {
    if (this.loginForm.valid) {
      this.isLoading = true;
      const { username, password } = this.loginForm.value;

      this.authService.login({ username, password }).subscribe({
        next: (response) => {
          this.isLoading = false;
          if (response.success) {
            this.snackBar.open('Login exitoso', 'Cerrar', { duration: 3000 });
            this.router.navigate(['/dashboard']);
          } else {
            this.snackBar.open('Error en las credenciales', 'Cerrar', { duration: 3000 });
          }
        },
        error: (error) => {
          this.isLoading = false;
          console.error('Error de login:', error);
          this.snackBar.open('Error de conexión o credenciales inválidas', 'Cerrar', { duration: 3000 });
        }
      });
    }
  }
}