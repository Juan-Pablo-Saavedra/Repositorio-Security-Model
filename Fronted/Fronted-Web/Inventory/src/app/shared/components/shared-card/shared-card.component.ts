import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';

@Component({
  selector: 'shared-card',
  standalone: true,
  imports: [CommonModule, MatCardModule],
  template: `
    <mat-card class="shared-card" [class]="'card-' + variant" [ngClass]="{'card-clickable': clickable}">
      <mat-card-header *ngIf="title || subtitle">
        <mat-card-title *ngIf="title">{{ title }}</mat-card-title>
        <mat-card-subtitle *ngIf="subtitle">{{ subtitle }}</mat-card-subtitle>
      </mat-card-header>
      
      <mat-card-content>
        <ng-content></ng-content>
      </mat-card-content>
      
      <mat-card-actions *ngIf="hasActions">
        <ng-content select="[card-actions]"></ng-content>
      </mat-card-actions>
    </mat-card>
  `,
  styles: [`
    .shared-card {
      margin: 16px;
      transition: all 0.3s ease;
    }
    
    .card-clickable {
      cursor: pointer;
    }
    
    .card-clickable:hover {
      transform: translateY(-2px);
      box-shadow: 0 8px 25px rgba(0, 0, 0, 0.15);
    }
    
    .card-primary {
      border-left: 4px solid #1976d2;
    }
    
    .card-success {
      border-left: 4px solid #388e3c;
    }
    
    .card-warning {
      border-left: 4px solid #f57c00;
    }
    
    .card-danger {
      border-left: 4px solid #d32f2f;
    }
    
    .card-info {
      border-left: 4px solid #0288d1;
    }
    
    mat-card-content {
      min-height: 60px;
    }
  `]
})
export class SharedCardComponent {
  @Input() title?: string;
  @Input() subtitle?: string;
  @Input() variant: 'primary' | 'success' | 'warning' | 'danger' | 'info' = 'primary';
  @Input() clickable = false;
  @Input() hasActions = false;
}