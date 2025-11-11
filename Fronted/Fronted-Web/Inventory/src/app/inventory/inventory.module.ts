import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';

// Componentes básicos
import { InventoryListComponent } from './components/inventory-list.component';
import { InventoryFormComponent } from './components/inventory-form.component';

@NgModule({
  declarations: [
    InventoryListComponent,
    InventoryFormComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    RouterModule
  ]
})
export class InventoryModule { }