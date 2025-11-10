import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedModule } from '../../shared/shared.module';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, SharedModule],
  template: `
    <div class="dashboard-container">
      <h1>Dashboard de Gestión de Inventarios</h1>
      <div class="dashboard-stats">
        <mat-card class="stat-card">
          <mat-card-header>
            <mat-card-title>Productos</mat-card-title>
          </mat-card-header>
          <mat-card-content>
            <div class="stat-number">150</div>
            <p>Total de productos</p>
          </mat-card-content>
        </mat-card>

        <mat-card class="stat-card">
          <mat-card-header>
            <mat-card-title>Categorías</mat-card-title>
          </mat-card-header>
          <mat-card-content>
            <div class="stat-number">25</div>
            <p>Categorías activas</p>
          </mat-card-content>
        </mat-card>

        <mat-card class="stat-card">
          <mat-card-header>
            <mat-card-title>Proveedores</mat-card-title>
          </mat-card-header>
          <mat-card-content>
            <div class="stat-number">45</div>
            <p>Proveedores registrados</p>
          </mat-card-content>
        </mat-card>

        <mat-card class="stat-card">
          <mat-card-header>
            <mat-card-title>Órdenes</mat-card-title>
          </mat-card-header>
          <mat-card-content>
            <div class="stat-number">1,250</div>
            <p>Órdenes procesadas</p>
          </mat-card-content>
        </mat-card>
      </div>
    </div>
  `,
  styles: [`
    .dashboard-container {
      padding: 20px;
      background-color: #f5f5f5;
      min-height: 100vh;
    }

    .dashboard-stats {
      display: grid;
      grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
      gap: 20px;
      margin-top: 30px;
    }

    .stat-card {
      text-align: center;
      padding: 20px;
    }

    .stat-number {
      font-size: 2.5em;
      font-weight: bold;
      color: #3f51b5;
      margin: 10px 0;
    }

    h1 {
      color: #333;
      margin-bottom: 20px;
    }
  `]
})
export class DashboardComponent { }