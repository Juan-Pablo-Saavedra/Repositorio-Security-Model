import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  {
    path: '',
    redirectTo: 'list',
    pathMatch: 'full'
  },
  {
    path: 'list',
    loadComponent: () => import('./components/categories-list.component').then(m => m.CategoriesListComponent)
  },
  {
    path: 'create',
    loadComponent: () => import('./components/category-form.component').then(m => m.CategoryFormComponent)
  },
  {
    path: 'edit/:id',
    loadComponent: () => import('./components/category-form.component').then(m => m.CategoryFormComponent)
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CategoriesRoutingModule { }