import { Component, OnInit, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet, Router } from '@angular/router';
import { AuthService } from './shared/services/auth.service';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatMenuModule } from '@angular/material/menu';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatListModule } from '@angular/material/list';
import { MatSidenav } from '@angular/material/sidenav';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    CommonModule,
    RouterOutlet,
    MatToolbarModule,
    MatButtonModule,
    MatIconModule,
    MatMenuModule,
    MatSidenavModule,
    MatListModule
  ],
  template: `
    <div class="app-container">
      <mat-toolbar color="primary" *ngIf="isAuthenticated">
        <button mat-icon-button (click)="toggleSidenav()">
          <mat-icon>menu</mat-icon>
        </button>
        <span class="app-title">Sistema de Inventarios</span>
        <span class="spacer"></span>
        <button mat-button [matMenuTriggerFor]="userMenu">
          <mat-icon>account_circle</mat-icon>
          {{ currentUser?.username }}
        </button>
        <mat-menu #userMenu="matMenu">
          <button mat-menu-item (click)="logout()">
            <mat-icon>exit_to_app</mat-icon>
            Cerrar Sesión
          </button>
        </mat-menu>
      </mat-toolbar>

      <mat-sidenav-container class="sidenav-container" *ngIf="isAuthenticated">
        <mat-sidenav #sidenav mode="side" [opened]="true">
          <mat-nav-list>
            <a mat-list-item routerLink="/dashboard" routerLinkActive="active">
              <mat-icon>dashboard</mat-icon>
              <span>Dashboard</span>
            </a>
            <a mat-list-item routerLink="/inventory" routerLinkActive="active">
              <mat-icon>inventory_2</mat-icon>
              <span>Inventario</span>
            </a>
            <a mat-list-item routerLink="/products" routerLinkActive="active">
              <mat-icon>category</mat-icon>
              <span>Productos</span>
            </a>
            <a mat-list-item routerLink="/categories" routerLinkActive="active">
              <mat-icon>label</mat-icon>
              <span>Categorías</span>
            </a>
            <a mat-list-item routerLink="/suppliers" routerLinkActive="active">
              <mat-icon>local_shipping</mat-icon>
              <span>Proveedores</span>
            </a>
            <a mat-list-item routerLink="/orders" routerLinkActive="active">
              <mat-icon>shopping_cart</mat-icon>
              <span>Órdenes</span>
            </a>
          </mat-nav-list>
        </mat-sidenav>

        <mat-sidenav-content class="main-content">
          <router-outlet></router-outlet>
        </mat-sidenav-content>
      </mat-sidenav-container>

      <div *ngIf="!isAuthenticated" class="auth-container">
        <router-outlet></router-outlet>
      </div>
    </div>
  `,
  styles: [`
    .app-container {
      height: 100vh;
      display: flex;
      flex-direction: column;
    }

    .app-title {
      font-size: 1.2em;
      font-weight: 500;
    }

    .spacer {
      flex: 1 1 auto;
    }

    .sidenav-container {
      flex: 1;
    }

    .sidenav {
      width: 250px;
    }

    .main-content {
      padding: 20px;
      background-color: #f5f5f5;
    }

    .auth-container {
      height: 100vh;
      display: flex;
      align-items: center;
      justify-content: center;
      background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
    }

    .active {
      background-color: rgba(0, 0, 0, 0.1) !important;
    }

    mat-nav-list a {
      display: flex;
      align-items: center;
      gap: 12px;
    }

    @media (max-width: 768px) {
      .sidenav {
        width: 200px;
      }
    }
  `]
})
export class AppComponent implements OnInit {
  title = 'Sistema de Inventarios';
  isAuthenticated = false;
  currentUser: any = null;
  @ViewChild('sidenav') sidenav!: MatSidenav;

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit() {
    this.authService.isAuthenticated$.subscribe(
      isAuth => {
        this.isAuthenticated = isAuth;
        if (!isAuth && this.router.url !== '/auth/login') {
          this.router.navigate(['/auth/login']);
        }
      }
    );

    this.authService.currentUser$.subscribe(
      user => {
        this.currentUser = user;
      }
    );
  }

  toggleSidenav() {
    if (this.sidenav) {
      this.sidenav.toggle();
    }
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/auth/login']);
  }
}
