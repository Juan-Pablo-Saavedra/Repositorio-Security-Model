import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Router } from '@angular/router';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSnackBarModule, MatSnackBar } from '@angular/material/snack-bar';
import { AuthService } from '../../../shared/services/auth.service';
import { RegisterRequest } from '../../../shared/models/user.model';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [
    CommonModule,
    RouterModule,
    ReactiveFormsModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule,
    MatProgressSpinnerModule,
    MatSnackBarModule
  ],
  template: `
    <div class="register-container">
      <mat-card class="register-card">
        <mat-card-header>
          <mat-card-title class="register-title">Crear Cuenta</mat-card-title>
          <mat-card-subtitle>Regístrate para acceder al sistema de inventario</mat-card-subtitle>
        </mat-card-header>

        <mat-card-content>
          <form [formGroup]="registerForm" (ngSubmit)="onSubmit()" class="register-form">
            <mat-form-field appearance="outline" class="full-width">
              <mat-label>Usuario</mat-label>
              <input matInput formControlName="username" placeholder="Ingresa tu usuario" autocomplete="username">
              <mat-icon matPrefix>person</mat-icon>
              <mat-error *ngIf="registerForm.get('username')?.hasError('required') && registerForm.get('username')?.touched">
                El usuario es requerido
              </mat-error>
              <mat-error *ngIf="registerForm.get('username')?.hasError('minlength')">
                El usuario debe tener al menos 3 caracteres
              </mat-error>
            </mat-form-field>

            <mat-form-field appearance="outline" class="full-width">
              <mat-label>Email</mat-label>
              <input matInput type="email" formControlName="email" placeholder="Ingresa tu email" autocomplete="email">
              <mat-icon matPrefix>email</mat-icon>
              <mat-error *ngIf="registerForm.get('email')?.hasError('required') && registerForm.get('email')?.touched">
                El email es requerido
              </mat-error>
              <mat-error *ngIf="registerForm.get('email')?.hasError('email')">
                Ingresa un email válido
              </mat-error>
            </mat-form-field>

            <mat-form-field appearance="outline" class="full-width">
              <mat-label>Contraseña</mat-label>
              <input matInput type="password" formControlName="password" placeholder="Ingresa tu contraseña" autocomplete="new-password">
              <mat-icon matPrefix>lock</mat-icon>
              <mat-error *ngIf="registerForm.get('password')?.hasError('required') && registerForm.get('password')?.touched">
                La contraseña es requerida
              </mat-error>
              <mat-error *ngIf="registerForm.get('password')?.hasError('minlength')">
                La contraseña debe tener al menos 6 caracteres
              </mat-error>
            </mat-form-field>

            <mat-form-field appearance="outline" class="full-width">
              <mat-label>Confirmar Contraseña</mat-label>
              <input matInput type="password" formControlName="confirmPassword" placeholder="Confirma tu contraseña" autocomplete="new-password">
              <mat-icon matPrefix>lock</mat-icon>
              <mat-error *ngIf="registerForm.get('confirmPassword')?.hasError('required') && registerForm.get('confirmPassword')?.touched">
                La confirmación de contraseña es requerida
              </mat-error>
              <mat-error *ngIf="registerForm.hasError('passwordMismatch')">
                Las contraseñas no coinciden
              </mat-error>
            </mat-form-field>

            <button mat-raised-button color="primary" type="submit" class="register-button" [disabled]="registerForm.invalid || isLoading">
              <mat-icon *ngIf="!isLoading">person_add</mat-icon>
              <mat-spinner diameter="20" *ngIf="isLoading"></mat-spinner>
              {{ isLoading ? 'Creando cuenta...' : 'Crear Cuenta' }}
            </button>
          </form>
        </mat-card-content>

        <mat-card-actions class="register-actions">
          <p>¿Ya tienes cuenta?
            <a routerLink="/auth/login" class="login-link">Inicia sesión aquí</a>
          </p>
        </mat-card-actions>
      </mat-card>
    </div>
  `,
  styles: [`
    .register-container {
      display: flex;
      justify-content: center;
      align-items: center;
      min-height: 100vh;
      background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
      padding: 20px;
    }

    .register-card {
      max-width: 400px;
      width: 100%;
      box-shadow: 0 8px 32px rgba(0, 0, 0, 0.1);
    }

    .register-title {
      text-align: center;
      color: #333;
      font-size: 24px;
      font-weight: 500;
    }

    .register-form {
      display: flex;
      flex-direction: column;
      gap: 16px;
    }

    .full-width {
      width: 100%;
    }

    .register-button {
      height: 48px;
      font-size: 16px;
      margin-top: 8px;
    }

    .register-actions {
      justify-content: center;
      padding: 16px;
    }

    .login-link {
      color: #1976d2;
      text-decoration: none;
      font-weight: 500;
    }

    .login-link:hover {
      text-decoration: underline;
    }
  `]
})
export class RegisterComponent {
  registerForm: FormGroup;
  isLoading = false;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router,
    private snackBar: MatSnackBar
  ) {
    this.registerForm = this.fb.group({
      username: ['', [Validators.required, Validators.minLength(3)]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      confirmPassword: ['', [Validators.required]]
    }, { validators: this.passwordMatchValidator });
  }

  passwordMatchValidator(group: FormGroup): any {
    const password = group.get('password');
    const confirmPassword = group.get('confirmPassword');
    return password && confirmPassword && password.value === confirmPassword.value
      ? null : { passwordMismatch: true };
  }

  onSubmit() {
    if (this.registerForm.valid) {
      this.isLoading = true;
      const userData: RegisterRequest = {
        username: this.registerForm.value.username,
        email: this.registerForm.value.email,
        password: this.registerForm.value.password,
        firstName: this.registerForm.value.firstName,
        lastName: this.registerForm.value.lastName
      };

      this.authService.register(userData).subscribe({
        next: (response: any) => {
          this.isLoading = false;
          if (response.success) {
            this.snackBar.open('Cuenta creada exitosamente. Ahora puedes iniciar sesión.', 'Cerrar', { duration: 5000 });
            this.router.navigate(['/auth/login']);
          } else {
            this.snackBar.open(response.message || 'Error al crear la cuenta', 'Cerrar', { duration: 3000 });
          }
        },
        error: (error: any) => {
          this.isLoading = false;
          console.error('Register error:', error);
          const errorMessage = error.error?.message || 'Error al crear la cuenta';
          this.snackBar.open(errorMessage, 'Cerrar', { duration: 3000 });
        }
      });
    } else {
      this.markFormGroupTouched();
    }
  }

  private markFormGroupTouched() {
    Object.keys(this.registerForm.controls).forEach(key => {
      const control = this.registerForm.get(key);
      control?.markAsTouched();
    });
  }
}