import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { SharedModule } from '../../shared/shared.module';

const routes: Routes = [
  {
    path: '',
    loadComponent: () => import('./categories-list.component').then(c => c.CategoriesListComponent)
  },
  {
    path: 'create',
    loadComponent: () => import('./category-form.component').then(c => c.CategoryFormComponent)
  },
  {
    path: 'edit/:id',
    loadComponent: () => import('./category-form.component').then(c => c.CategoryFormComponent)
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
export class CategoriesModule { }