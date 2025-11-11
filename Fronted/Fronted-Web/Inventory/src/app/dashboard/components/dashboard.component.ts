import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-dashboard',
  template: `
    <div class="dashboard-container">
      <h1>Dashboard</h1>
      <p>Panel principal del sistema Inventory</p>
      
      <div class="stats-grid">
        <div class="stat-card primary">
          <h3>Productos</h3>
          <p class="stat-number">150</p>
          <span>Total de productos</span>
        </div>
        
        <div class="stat-card success">
          <h3>Categorías</h3>
          <p class="stat-number">25</p>
          <span>Categorías activas</span>
        </div>
        
        <div class="stat-card info">
          <h3>Proveedores</h3>
          <p class="stat-number">18</p>
          <span>Proveedores registrados</span>
        </div>
        
        <div class="stat-card warning">
          <h3>Pedidos</h3>
          <p class="stat-number">8</p>
          <span>Pedidos pendientes</span>
        </div>
      </div>
    </div>
  `,
  styles: [`
    .dashboard-container {
      padding: 2rem;
    }
    
    h1 {
      color: #333;
      margin-bottom: 1rem;
    }
    
    .stats-grid {
      display: grid;
      grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
      gap: 1.5rem;
      margin-top: 2rem;
    }
    
    .stat-card {
      background: white;
      border-radius: 12px;
      padding: 1.5rem;
      box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
      border-left: 4px solid;
      transition: transform 0.2s ease;
    }
    
    .stat-card:hover {
      transform: translateY(-4px);
    }
    
    .stat-card.primary { border-left-color: #1976d2; }
    .stat-card.success { border-left-color: #388e3c; }
    .stat-card.info { border-left-color: #0288d1; }
    .stat-card.warning { border-left-color: #f57c00; }
    
    .stat-card h3 {
      margin: 0 0 0.5rem 0;
      color: #333;
      font-size: 1.1rem;
    }
    
    .stat-card span {
      color: #666;
      font-size: 0.9rem;
    }
    
    .stat-number {
      font-size: 2.5rem;
      font-weight: bold;
      color: #667eea;
      margin: 0.5rem 0;
    }
  `]
})
export class DashboardComponent implements OnInit {
  ngOnInit(): void {
    // TODO: Cargar estadísticas del backend
  }
}