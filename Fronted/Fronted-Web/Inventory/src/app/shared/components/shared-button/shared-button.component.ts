import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';

export type ButtonType = 'primary' | 'secondary' | 'success' | 'danger' | 'warning' | 'info';
export type ButtonSize = 'small' | 'medium' | 'large';

@Component({
  selector: 'shared-button',
  standalone: true,
  imports: [CommonModule, MatButtonModule, MatIconModule],
  template: `
    <button
      mat-raised-button
      [disabled]="disabled"
      [color]="color"
      [class]="getButtonClass()"
      (click)="onClick($event)"
    >
      <mat-icon *ngIf="icon" [class]="'icon-' + size">{{ icon }}</mat-icon>
      <span *ngIf="label">{{ label }}</span>
    </button>
  `,
  styles: [`
    :host {
      display: inline-block;
    }
    
    .btn-small {
      min-width: 80px;
      height: 32px;
    }
    
    .btn-medium {
      min-width: 120px;
      height: 40px;
    }
    
    .btn-large {
      min-width: 160px;
      height: 48px;
    }
    
    .icon-small {
      font-size: 16px;
      margin-right: 4px;
    }
    
    .icon-medium {
      font-size: 20px;
      margin-right: 6px;
    }
    
    .icon-large {
      font-size: 24px;
      margin-right: 8px;
    }
  `]
})
export class SharedButtonComponent {
  @Input() label?: string;
  @Input() icon?: string;
  @Input() color: ButtonType = 'primary';
  @Input() size: ButtonSize = 'medium';
  @Input() disabled = false;
  @Output() clicked = new EventEmitter<Event>();

  onClick(event: Event): void {
    if (!this.disabled) {
      this.clicked.emit(event);
    }
  }

  getButtonClass(): string {
    return `btn-${this.size}`;
  }
}