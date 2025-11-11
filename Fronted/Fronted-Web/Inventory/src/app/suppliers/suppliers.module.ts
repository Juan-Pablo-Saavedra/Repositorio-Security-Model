import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';

// Componentes básicos
import { SuppliersListComponent } from './components/suppliers-list.component';
import { SupplierFormComponent } from './components/supplier-form.component';

@NgModule({
  declarations: [
    SuppliersListComponent,
    SupplierFormComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    RouterModule
  ]
})
export class SuppliersModule { }