import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { SharedModule } from '../../shared/shared.module';

const routes: Routes = [
  {
    path: '',
    loadComponent: () => import('./products-list.component').then(c => c.ProductsListComponent)
  },
  {
    path: 'create',
    loadComponent: () => import('./product-form.component').then(c => c.ProductFormComponent)
  },
  {
    path: 'edit/:id',
    loadComponent: () => import('./product-form.component').then(c => c.ProductFormComponent)
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
export class ProductsModule { }