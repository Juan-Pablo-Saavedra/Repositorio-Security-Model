import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, MatCardModule, MatIconModule, MatButtonModule, RouterModule],
  template: `
    <div class="dashboard-container">
      <!-- Header del Dashboard -->
      <div class="dashboard-header">
        <div>
          <h1 class="dashboard-title">Dashboard</h1>
          <p class="dashboard-subtitle">Resumen general del inventario</p>
        </div>
        <button mat-raised-button color="primary" routerLink="/products/create">
          <mat-icon>add</mat-icon>
          Nuevo Producto
        </button>
      </div>

      <!-- Cards de Estadísticas -->
      <div class="stats-grid">
        <mat-card class="stat-card" *ngFor="let stat of stats">
          <div class="stat-icon" [style.background]="stat.color + '15'">
            <mat-icon [style.color]="stat.color">{{ stat.icon }}</mat-icon>
          </div>
          <div class="stat-content">
            <h3 class="stat-value">{{ stat.value }}</h3>
            <p class="stat-label">{{ stat.label }}</p>
            <span class="stat-change" [class.positive]="stat.change > 0" [class.negative]="stat.change < 0">
              <mat-icon>{{ stat.change > 0 ? 'trending_up' : 'trending_down' }}</mat-icon>
              {{ stat.change > 0 ? '+' : '' }}{{ stat.change }}%
            </span>
          </div>
        </mat-card>
      </div>

      <!-- Gráficos y Tablas -->
      <div class="content-grid">
        <!-- Productos con Stock Bajo -->
        <mat-card class="content-card">
          <div class="card-header">
            <h2>
              <mat-icon>inventory_2</mat-icon>
              Stock Bajo
            </h2>
            <button mat-icon-button routerLink="/inventory">
              <mat-icon>more_vert</mat-icon>
            </button>
          </div>
          <div class="card-body">
            <div class="product-item" *ngFor="let product of lowStockProducts">
              <div class="product-info">
                <div class="product-avatar" [style.background]="product.color">
                  {{ product.name.charAt(0) }}
                </div>
                <div>
                  <p class="product-name">{{ product.name }}</p>
                  <p class="product-category">{{ product.category }}</p>
                </div>
              </div>
              <div class="product-stock">
                <span class="stock-badge low">{{ product.stock }} unidades</span>
              </div>
            </div>
          </div>
        </mat-card>

        <!-- Actividad Reciente -->
        <mat-card class="content-card">
          <div class="card-header">
            <h2>
              <mat-icon>history</mat-icon>
              Actividad Reciente
            </h2>
            <button mat-icon-button>
              <mat-icon>more_vert</mat-icon>
            </button>
          </div>
          <div class="card-body">
            <div class="activity-item" *ngFor="let activity of recentActivity">
              <div class="activity-icon" [class]="activity.type">
                <mat-icon>{{ activity.icon }}</mat-icon>
              </div>
              <div class="activity-info">
                <p class="activity-text">{{ activity.text }}</p>
                <p class="activity-time">{{ activity.time }}</p>
              </div>
            </div>
          </div>
        </mat-card>
      </div>

      <!-- Accesos Rápidos -->
      <div class="quick-actions">
        <h2 class="section-title">Accesos Rápidos</h2>
        <div class="actions-grid">
          <button mat-raised-button class="action-button" routerLink="/products">
            <mat-icon>inventory</mat-icon>
            <span>Ver Productos</span>
          </button>
          <button mat-raised-button class="action-button" routerLink="/categories">
            <mat-icon>category</mat-icon>
            <span>Categorías</span>
          </button>
          <button mat-raised-button class="action-button" routerLink="/suppliers">
            <mat-icon>local_shipping</mat-icon>
            <span>Proveedores</span>
          </button>
          <button mat-raised-button class="action-button" routerLink="/orders">
            <mat-icon>shopping_cart</mat-icon>
            <span>Órdenes</span>
          </button>
        </div>
      </div>
    </div>
  `,
  styles: [`
    .dashboard-container {
      padding: 0;
      animation: fadeIn 0.3s ease-out;
    }

    @keyframes fadeIn {
      from { opacity: 0; transform: translateY(20px); }
      to { opacity: 1; transform: translateY(0); }
    }

    .dashboard-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-bottom: 2rem;
      flex-wrap: wrap;
      gap: 1rem;
    }

    .dashboard-title {
      font-size: 2rem;
      font-weight: 700;
      margin: 0;
      color: var(--text-primary);
    }

    .dashboard-subtitle {
      font-size: 0.875rem;
      color: var(--text-secondary);
      margin: 0.5rem 0 0 0;
    }

    .stats-grid {
      display: grid;
      grid-template-columns: repeat(auto-fit, minmax(240px, 1fr));
      gap: 1.5rem;
      margin-bottom: 2rem;
    }

    .stat-card {
      display: flex;
      align-items: center;
      gap: 1rem;
      padding: 1.5rem !important;
      border-radius: 12px !important;
      transition: all 0.2s ease;
      cursor: pointer;
    }

    .stat-card:hover {
      transform: translateY(-4px);
      box-shadow: 0 8px 16px rgba(0, 0, 0, 0.1) !important;
    }

    .stat-icon {
      width: 56px;
      height: 56px;
      border-radius: 12px;
      display: flex;
      align-items: center;
      justify-content: center;
    }

    .stat-icon mat-icon {
      font-size: 28px;
      width: 28px;
      height: 28px;
    }

    .stat-content {
      flex: 1;
    }

    .stat-value {
      font-size: 1.75rem;
      font-weight: 700;
      margin: 0;
      color: var(--text-primary);
    }

    .stat-label {
      font-size: 0.875rem;
      color: var(--text-secondary);
      margin: 0.25rem 0;
    }

    .stat-change {
      display: inline-flex;
      align-items: center;
      gap: 0.25rem;
      font-size: 0.75rem;
      font-weight: 600;
      padding: 0.25rem 0.5rem;
      border-radius: 4px;
    }

    .stat-change mat-icon {
      font-size: 16px;
      width: 16px;
      height: 16px;
    }

    .stat-change.positive {
      color: var(--color-success);
      background: var(--color-success-light);
    }

    .stat-change.negative {
      color: var(--color-error);
      background: var(--color-error-light);
    }

    .content-grid {
      display: grid;
      grid-template-columns: repeat(auto-fit, minmax(400px, 1fr));
      gap: 1.5rem;
      margin-bottom: 2rem;
    }

    .content-card {
      border-radius: 12px !important;
      padding: 0 !important;
      overflow: hidden;
    }

    .card-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      padding: 1.5rem;
      border-bottom: 1px solid var(--border-primary);
    }

    .card-header h2 {
      display: flex;
      align-items: center;
      gap: 0.5rem;
      margin: 0;
      font-size: 1.125rem;
      font-weight: 600;
      color: var(--text-primary);
    }

    .card-header mat-icon {
      font-size: 20px;
      width: 20px;
      height: 20px;
    }

    .card-body {
      padding: 1rem;
    }

    .product-item {
      display: flex;
      justify-content: space-between;
      align-items: center;
      padding: 1rem;
      border-radius: 8px;
      margin-bottom: 0.5rem;
      transition: background-color 0.2s ease;
    }

    .product-item:hover {
      background: var(--surface-hover);
    }

    .product-info {
      display: flex;
      align-items: center;
      gap: 1rem;
    }

    .product-avatar {
      width: 40px;
      height: 40px;
      border-radius: 8px;
      display: flex;
      align-items: center;
      justify-content: center;
      color: white;
      font-weight: 700;
      font-size: 1rem;
    }

    .product-name {
      font-weight: 600;
      margin: 0;
      color: var(--text-primary);
    }

    .product-category {
      font-size: 0.75rem;
      color: var(--text-secondary);
      margin: 0.25rem 0 0 0;
    }

    .stock-badge {
      padding: 0.25rem 0.75rem;
      border-radius: 12px;
      font-size: 0.75rem;
      font-weight: 600;
    }

    .stock-badge.low {
      background: var(--color-warning-light);
      color: var(--color-warning-dark);
    }

    .activity-item {
      display: flex;
      align-items: flex-start;
      gap: 1rem;
      padding: 1rem;
      border-radius: 8px;
      margin-bottom: 0.5rem;
      transition: background-color 0.2s ease;
    }

    .activity-item:hover {
      background: var(--surface-hover);
    }

    .activity-icon {
      width: 40px;
      height: 40px;
      border-radius: 8px;
      display: flex;
      align-items: center;
      justify-content: center;
      flex-shrink: 0;
    }

    .activity-icon mat-icon {
      font-size: 20px;
      width: 20px;
      height: 20px;
    }

    .activity-icon.add {
      background: var(--color-success-light);
      color: var(--color-success);
    }

    .activity-icon.remove {
      background: var(--color-error-light);
      color: var(--color-error);
    }

    .activity-icon.update {
      background: var(--color-info-light);
      color: var(--color-info);
    }

    .activity-info {
      flex: 1;
    }

    .activity-text {
      font-size: 0.875rem;
      color: var(--text-primary);
      margin: 0 0 0.25rem 0;
    }

    .activity-time {
      font-size: 0.75rem;
      color: var(--text-secondary);
      margin: 0;
    }

    .quick-actions {
      margin-top: 2rem;
    }

    .section-title {
      font-size: 1.25rem;
      font-weight: 600;
      margin: 0 0 1rem 0;
      color: var(--text-primary);
    }

    .actions-grid {
      display: grid;
      grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
      gap: 1rem;
    }

    .action-button {
      display: flex;
      flex-direction: column;
      align-items: center;
      gap: 0.5rem;
      padding: 1.5rem !important;
      height: auto !important;
      border-radius: 12px !important;
      transition: all 0.2s ease;
    }

    .action-button:hover {
      transform: translateY(-2px);
      box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1) !important;
    }

    .action-button mat-icon {
      font-size: 32px;
      width: 32px;
      height: 32px;
    }

    .action-button span {
      font-weight: 500;
    }

    @media (max-width: 768px) {
      .content-grid {
        grid-template-columns: 1fr;
      }

      .stats-grid {
        grid-template-columns: 1fr;
      }

      .actions-grid {
        grid-template-columns: repeat(2, 1fr);
      }
    }
  `]
})
export class DashboardComponent {
  stats = [
    {
      icon: 'inventory_2',
      value: '1,234',
      label: 'Total Productos',
      change: 12,
      color: '#0ea5e9'
    },
    {
      icon: 'trending_down',
      value: '23',
      label: 'Stock Bajo',
      change: -5,
      color: '#f59e0b'
    },
    {
      icon: 'shopping_cart',
      value: '56',
      label: 'Órdenes Pendientes',
      change: 8,
      color: '#8b5cf6'
    },
    {
      icon: 'payments',
      value: '$45,231',
      label: 'Valor Inventario',
      change: 15,
      color: '#10b981'
    }
  ];

  lowStockProducts = [
    { name: 'Laptop HP ProBook', category: 'Electrónica', stock: 3, color: '#0ea5e9' },
    { name: 'Mouse Logitech MX', category: 'Accesorios', stock: 5, color: '#8b5cf6' },
    { name: 'Teclado Mecánico', category: 'Accesorios', stock: 7, color: '#f59e0b' },
    { name: 'Monitor Samsung 27"', category: 'Electrónica', stock: 2, color: '#10b981' }
  ];

  recentActivity = [
    { icon: 'add_circle', text: 'Se agregaron 50 unidades de "Laptop Dell"', time: 'Hace 5 minutos', type: 'add' },
    { icon: 'remove_circle', text: 'Se vendieron 15 unidades de "Mouse Logitech"', time: 'Hace 15 minutos', type: 'remove' },
    { icon: 'edit', text: 'Se actualizó el precio de "Teclado Mecánico"', time: 'Hace 30 minutos', type: 'update' },
    { icon: 'add_circle', text: 'Nueva orden de compra #ORD-2024-001', time: 'Hace 1 hora', type: 'add' }
  ];
}