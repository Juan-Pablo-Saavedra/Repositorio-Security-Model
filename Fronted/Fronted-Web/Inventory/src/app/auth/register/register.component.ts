import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  template: `
    <div class="register-page">
      <div class="register-container animate-fade-in">
        <div class="register-card animate-scale-in">
          <!-- Header con Logo -->
          <div class="register-header">
            <div class="logo-wrapper">
              <svg class="register-logo" width="64" height="64" viewBox="0 0 64 64" fill="none" xmlns="http://www.w3.org/2000/svg">
                <rect width="64" height="64" rx="16" fill="url(#logo-gradient)"/>
                <path d="M32 16L16 24V40L32 48L48 40V24L32 16Z" fill="white" opacity="0.9"/>
                <path d="M32 24L24 28V36L32 40L40 36V28L32 24Z" fill="white"/>
                <defs>
                  <linearGradient id="logo-gradient" x1="0" y1="0" x2="64" y2="64" gradientUnits="userSpaceOnUse">
                    <stop stop-color="#ff6b35"/>
                    <stop offset="1" stop-color="#f7b731"/>
                  </linearGradient>
                </defs>
              </svg>
            </div>
            <h1 class="app-title">InventarioPro</h1>
            <p class="app-subtitle">Crear nueva cuenta</p>
          </div>

          <!-- Formulario de Registro -->
          <form [formGroup]="registerForm" (ngSubmit)="onSubmit()" class="register-form">
            <div class="form-section">
              <h2 class="form-title">Registro de usuario</h2>
              <p class="form-description">Completa los datos para crear tu cuenta</p>

              <div class="form-row animate-slide-in-left" style="animation-delay: 0.1s;">
                <div class="form-group">
                  <label for="firstName" class="form-label">
                    Nombre
                  </label>
                  <input
                    type="text"
                    id="firstName"
                    formControlName="firstName"
                    class="form-input"
                    placeholder="Tu nombre"
                    [class.error]="isFieldInvalid('firstName')"
                  />
                  <div class="error-message" *ngIf="isFieldInvalid('firstName')">
                    El nombre es requerido
                  </div>
                </div>

                <div class="form-group">
                  <label for="lastName" class="form-label">
                    Apellido
                  </label>
                  <input
                    type="text"
                    id="lastName"
                    formControlName="lastName"
                    class="form-input"
                    placeholder="Tu apellido"
                    [class.error]="isFieldInvalid('lastName')"
                  />
                  <div class="error-message" *ngIf="isFieldInvalid('lastName')">
                    El apellido es requerido
                  </div>
                </div>
              </div>

              <div class="form-group animate-slide-in-left" style="animation-delay: 0.2s;">
                <label for="email" class="form-label">
                  Correo electrónico
                </label>
                <input
                  type="email"
                  id="email"
                  formControlName="email"
                  class="form-input"
                  placeholder="tu@email.com"
                  [class.error]="isFieldInvalid('email')"
                />
                <div class="error-message" *ngIf="isFieldInvalid('email')">
                  Ingresa un email válido
                </div>
              </div>

              <div class="form-group animate-slide-in-left" style="animation-delay: 0.3s;">
                <label for="username" class="form-label">
                  Usuario
                </label>
                <input
                  type="text"
                  id="username"
                  formControlName="username"
                  class="form-input"
                  placeholder="nombre_usuario"
                  [class.error]="isFieldInvalid('username')"
                />
                <div class="error-message" *ngIf="isFieldInvalid('username')">
                  El usuario es requerido
                </div>
              </div>

              <div class="form-group animate-slide-in-left" style="animation-delay: 0.4s;">
                <label for="password" class="form-label">
                  Contraseña
                </label>
                <div class="input-wrapper">
                  <input
                    [type]="showPassword ? 'text' : 'password'"
                    id="password"
                    formControlName="password"
                    class="form-input"
                    placeholder="Mínimo 6 caracteres"
                    [class.error]="isFieldInvalid('password')"
                  />
                  <button 
                    type="button" 
                    class="password-toggle"
                    (click)="togglePasswordVisibility()">
                    {{ showPassword ? '👁️' : '🔒' }}
                  </button>
                </div>
                <div class="error-message" *ngIf="isFieldInvalid('password')">
                  La contraseña debe tener al menos 6 caracteres
                </div>
              </div>

              <div class="form-group animate-slide-in-left" style="animation-delay: 0.5s;">
                <label for="confirmPassword" class="form-label">
                  Confirmar contraseña
                </label>
                <input
                  [type]="showConfirmPassword ? 'text' : 'password'"
                  id="confirmPassword"
                  formControlName="confirmPassword"
                  class="form-input"
                  placeholder="Repite tu contraseña"
                  [class.error]="isFieldInvalid('confirmPassword')"
                />
                <div class="error-message" *ngIf="isFieldInvalid('confirmPassword')">
                  Las contraseñas no coinciden
                </div>
              </div>

              <div class="form-options animate-slide-in-left" style="animation-delay: 0.6s;">
                <label class="checkbox-wrapper">
                  <input type="checkbox" class="checkbox" formControlName="terms" />
                  <span class="checkbox-label">
                    Acepto los <a href="#" class="terms-link">términos y condiciones</a>
                  </span>
                </label>
              </div>

              <button 
                type="submit" 
                class="register-button animate-slide-in-left" 
                [disabled]="registerForm.invalid || isLoading"
                style="animation-delay: 0.7s;">
                <span class="button-content">
                  <span *ngIf="!isLoading">Crear cuenta</span>
                  <span *ngIf="isLoading">Creando cuenta...</span>
                </span>
              </button>
            </div>
          </form>

          <!-- Footer -->
          <div class="register-footer animate-fade-in" style="animation-delay: 0.8s;">
            <p class="footer-text">
              ¿Ya tienes una cuenta? 
              <a [routerLink]="['/auth/login']" class="login-link">Inicia sesión</a>
            </p>
          </div>
        </div>
      </div>

      <!-- Decoraciones de fondo -->
      <div class="background-decoration">
        <div class="floating-element element-1"></div>
        <div class="floating-element element-2"></div>
        <div class="floating-element element-3"></div>
      </div>
    </div>
  `,
  styles: [`
    /* Estilos base de la página - Mismos que login */
    .register-page {
      min-height: 100vh;
      display: flex;
      align-items: center;
      justify-content: center;
      background: var(--bg-gradient-primary);
      position: relative;
      overflow: hidden;
      font-family: var(--font-family-primary);
      padding: var(--spacing-lg);
    }

    .background-decoration {
      position: absolute;
      top: 0;
      left: 0;
      right: 0;
      bottom: 0;
      pointer-events: none;
      z-index: 0;
    }

    .floating-element {
      position: absolute;
      border-radius: 50%;
      opacity: 0.1;
      animation: float 6s ease-in-out infinite;
    }

    .element-1 {
      width: 200px;
      height: 200px;
      background: var(--secondary-yellow);
      top: 10%;
      left: 10%;
      animation-delay: 0s;
    }

    .element-2 {
      width: 150px;
      height: 150px;
      background: var(--primary-orange-light);
      top: 60%;
      right: 15%;
      animation-delay: 2s;
    }

    .element-3 {
      width: 100px;
      height: 100px;
      background: var(--beige);
      bottom: 20%;
      left: 20%;
      animation-delay: 4s;
    }

    @keyframes float {
      0%, 100% { transform: translateY(0px) rotate(0deg); }
      50% { transform: translateY(-20px) rotate(180deg); }
    }

    /* Contenedor principal del registro */
    .register-container {
      width: 100%;
      max-width: 480px;
      z-index: 1;
      position: relative;
    }

    /* Card del registro */
    .register-card {
      background: var(--surface-primary);
      border-radius: var(--radius-2xl);
      box-shadow: var(--shadow-2xl);
      border: 1px solid var(--border-primary);
      overflow: hidden;
      position: relative;
    }

    .register-card::before {
      content: '';
      position: absolute;
      top: 0;
      left: 0;
      right: 0;
      height: 4px;
      background: var(--bg-gradient-primary);
    }

    /* Header del registro */
    .register-header {
      text-align: center;
      padding: var(--spacing-2xl) var(--spacing-xl) var(--spacing-xl);
      background: var(--surface-secondary);
    }

    .logo-wrapper {
      margin-bottom: var(--spacing-lg);
      display: flex;
      justify-content: center;
    }

    .register-logo {
      filter: drop-shadow(0 4px 8px rgba(255, 107, 53, 0.3));
      animation: pulse 3s ease-in-out infinite;
    }

    .app-title {
      font-size: var(--font-size-3xl);
      font-weight: var(--font-weight-bold);
      color: var(--text-primary);
      margin: 0 0 var(--spacing-sm) 0;
      background: var(--bg-gradient-primary);
      -webkit-background-clip: text;
      -webkit-text-fill-color: transparent;
      background-clip: text;
    }

    .app-subtitle {
      font-size: var(--font-size-base);
      color: var(--text-secondary);
      margin: 0;
      font-weight: var(--font-weight-normal);
    }

    /* Formulario */
    .register-form {
      padding: var(--spacing-2xl);
    }

    .form-section {
      display: flex;
      flex-direction: column;
      gap: var(--spacing-lg);
    }

    .form-title {
      font-size: var(--font-size-xl);
      font-weight: var(--font-weight-semibold);
      color: var(--text-primary);
      margin: 0;
      text-align: center;
    }

    .form-description {
      font-size: var(--font-size-sm);
      color: var(--text-secondary);
      margin: 0 0 var(--spacing-lg) 0;
      text-align: center;
    }

    .form-row {
      display: grid;
      grid-template-columns: 1fr 1fr;
      gap: var(--spacing-md);
    }

    .form-group {
      display: flex;
      flex-direction: column;
      gap: var(--spacing-sm);
    }

    .form-label {
      display: block;
      font-size: var(--font-size-sm);
      font-weight: var(--font-weight-medium);
      color: var(--text-secondary);
      margin-bottom: var(--spacing-sm);
    }

    .input-wrapper {
      position: relative;
      display: flex;
      align-items: center;
    }

    .form-input {
      width: 100%;
      padding: var(--spacing-md) var(--spacing-lg);
      border: 2px solid var(--border-primary);
      border-radius: var(--radius-lg);
      font-size: var(--font-size-base);
      background: var(--surface-secondary);
      color: var(--text-primary);
      transition: all var(--transition-fast);
      outline: none;
    }

    .form-input:focus {
      border-color: var(--border-accent);
      background: var(--surface-primary);
      box-shadow: 0 0 0 3px rgba(255, 107, 53, 0.1);
      transform: translateY(-1px);
    }

    .form-input.error {
      border-color: var(--error);
      background: var(--error-light);
    }

    .form-input::placeholder {
      color: var(--text-placeholder);
    }

    .password-toggle {
      position: absolute;
      right: var(--spacing-md);
      background: none;
      border: none;
      color: var(--text-tertiary);
      cursor: pointer;
      padding: var(--spacing-xs);
      border-radius: var(--radius-sm);
      transition: all var(--transition-fast);
      font-size: 16px;
    }

    .password-toggle:hover {
      color: var(--text-secondary);
      background: var(--surface-hover);
    }

    .error-message {
      font-size: var(--font-size-xs);
      color: var(--error);
      margin-top: var(--spacing-xs);
    }

    .form-options {
      display: flex;
      align-items: flex-start;
      margin-top: var(--spacing-sm);
    }

    .checkbox-wrapper {
      display: flex;
      align-items: flex-start;
      gap: var(--spacing-sm);
      cursor: pointer;
    }

    .checkbox {
      width: 18px;
      height: 18px;
      accent-color: var(--primary-orange);
      margin-top: 2px;
    }

    .checkbox-label {
      font-size: var(--font-size-sm);
      color: var(--text-secondary);
      line-height: 1.4;
    }

    .terms-link {
      color: var(--text-accent);
      text-decoration: none;
    }

    .terms-link:hover {
      text-decoration: underline;
    }

    /* Botón de registro */
    .register-button {
      background: var(--bg-gradient-primary);
      color: var(--text-inverse);
      border: none;
      padding: var(--spacing-lg) var(--spacing-xl);
      border-radius: var(--radius-lg);
      font-size: var(--font-size-base);
      font-weight: var(--font-weight-semibold);
      cursor: pointer;
      transition: all var(--transition-fast);
      box-shadow: var(--shadow-md);
      position: relative;
      overflow: hidden;
      margin-top: var(--spacing-sm);
    }

    .register-button:hover:not(:disabled) {
      transform: translateY(-2px);
      box-shadow: var(--shadow-lg);
    }

    .register-button:active {
      transform: translateY(0);
    }

    .register-button:disabled {
      opacity: 0.7;
      cursor: not-allowed;
      transform: none;
    }

    .button-content {
      display: flex;
      align-items: center;
      justify-content: center;
    }

    /* Footer */
    .register-footer {
      text-align: center;
      padding: var(--spacing-lg) var(--spacing-xl);
      background: var(--surface-secondary);
      border-top: 1px solid var(--border-primary);
    }

    .footer-text {
      font-size: var(--font-size-sm);
      color: var(--text-secondary);
      margin: 0;
    }

    .login-link {
      color: var(--text-accent);
      text-decoration: none;
      font-weight: var(--font-weight-semibold);
    }

    .login-link:hover {
      text-decoration: underline;
    }

    /* Responsive */
    @media (max-width: 768px) {
      .register-page {
        padding: var(--spacing-md);
      }

      .form-row {
        grid-template-columns: 1fr;
        gap: var(--spacing-lg);
      }

      .register-header {
        padding: var(--spacing-xl) var(--spacing-lg) var(--spacing-lg);
      }

      .register-form {
        padding: var(--spacing-xl);
      }

      .app-title {
        font-size: var(--font-size-2xl);
      }
    }

    /* Animaciones */
    @keyframes fadeIn {
      from { 
        opacity: 0; 
        transform: translateY(20px);
      }
      to { 
        opacity: 1; 
        transform: translateY(0);
      }
    }

    @keyframes scaleIn {
      from { 
        opacity: 0; 
        transform: scale(0.9);
      }
      to { 
        opacity: 1; 
        transform: scale(1);
      }
    }

    @keyframes slideInLeft {
      from { 
        opacity: 0; 
        transform: translateX(-30px);
      }
      to { 
        opacity: 1; 
        transform: translateX(0);
      }
    }

    @keyframes pulse {
      0%, 100% { transform: scale(1); }
      50% { transform: scale(1.05); }
    }

    .animate-fade-in { 
      animation: fadeIn 0.6s ease-out forwards; 
    }

    .animate-scale-in { 
      animation: scaleIn 0.4s ease-out forwards; 
    }

    .animate-slide-in-left { 
      animation: slideInLeft 0.5s ease-out forwards; 
      opacity: 0;
    }

    .animate-slide-in-left[style*="animation-delay"] {
      animation-fill-mode: forwards;
    }
  `]
})
export class RegisterComponent implements OnInit {
  registerForm!: FormGroup;
  isLoading = false;
  showPassword = false;
  showConfirmPassword = false;

  constructor(
    private fb: FormBuilder,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.registerForm = this.fb.group({
      firstName: ['', [Validators.required, Validators.minLength(2)]],
      lastName: ['', [Validators.required, Validators.minLength(2)]],
      email: ['', [Validators.required, Validators.email]],
      username: ['', [Validators.required, Validators.minLength(3)]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      confirmPassword: ['', [Validators.required]],
      terms: [false, [Validators.requiredTrue]]
    }, { validators: this.passwordMatchValidator });
  }

  isFieldInvalid(fieldName: string): boolean {
    const field = this.registerForm.get(fieldName);
    return !!(field && field.invalid && (field.dirty || field.touched));
  }

  passwordMatchValidator(form: FormGroup) {
    const password = form.get('password');
    const confirmPassword = form.get('confirmPassword');
    
    if (password && confirmPassword && password.value !== confirmPassword.value) {
      confirmPassword.setErrors({ passwordMismatch: true });
    } else if (confirmPassword?.errors?.['passwordMismatch']) {
      delete confirmPassword.errors['passwordMismatch'];
      if (Object.keys(confirmPassword.errors).length === 0) {
        confirmPassword.setErrors(null);
      }
    }
    return null;
  }

  togglePasswordVisibility(): void {
    this.showPassword = !this.showPassword;
  }

  toggleConfirmPasswordVisibility(): void {
    this.showConfirmPassword = !this.showConfirmPassword;
  }

  onSubmit(): void {
    if (this.registerForm.valid && !this.isLoading) {
      this.isLoading = true;
      
      // TODO: Implementar registro con servicio
      setTimeout(() => {
        this.isLoading = false;
        // Redireccionar después de registro exitoso
        this.router.navigate(['/auth/login']);
      }, 2000);
    }
  }
}