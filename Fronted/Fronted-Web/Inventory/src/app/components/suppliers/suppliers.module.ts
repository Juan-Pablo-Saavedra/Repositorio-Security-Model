import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { SharedModule } from '../../shared/shared.module';

const routes: Routes = [
  {
    path: '',
    loadComponent: () => import('./suppliers-list.component').then(c => c.SuppliersListComponent)
  },
  {
    path: 'create',
    loadComponent: () => import('./supplier-form.component').then(c => c.SupplierFormComponent)
  },
  {
    path: 'edit/:id',
    loadComponent: () => import('./supplier-form.component').then(c => c.SupplierFormComponent)
  }
];

@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    SharedModule,
    RouterModule.forChild(routes)
  ]
})
export class SuppliersModule { }