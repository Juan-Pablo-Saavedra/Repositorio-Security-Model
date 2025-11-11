import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

// Importa componentes que no son standalone
// Los componentes standalone ya no necesitan ser declarados aquí

const routes: Routes = [
  {
    path: '',
    redirectTo: 'list',
    pathMatch: 'full'
  },
  {
    path: 'list',
    loadComponent: () => import('./components/categories-list/categories-list.component').then(m => m.CategoriesListComponent)
  },
  {
    path: 'create',
    loadComponent: () => import('./components/category-form/category-form.component').then(m => m.CategoryFormComponent)
  },
  {
    path: 'edit/:id',
    loadComponent: () => import('./components/category-form/category-form.component').then(m => m.CategoryFormComponent)
  }
];

@NgModule({
  imports: [CommonModule, FormsModule, RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CategoriesModule { }