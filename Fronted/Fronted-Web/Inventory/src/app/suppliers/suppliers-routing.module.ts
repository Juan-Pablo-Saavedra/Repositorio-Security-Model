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
    loadComponent: () => import('./components/suppliers-list/suppliers-list.component').then(m => m.SuppliersListComponent)
  },
  {
    path: 'create',
    loadComponent: () => import('./components/supplier-form/supplier-form.component').then(m => m.SupplierFormComponent)
  },
  {
    path: 'edit/:id',
    loadComponent: () => import('./components/supplier-form/supplier-form.component').then(m => m.SupplierFormComponent)
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SuppliersRoutingModule { }