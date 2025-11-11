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
    loadComponent: () => import('./components/inventory-list/inventory-list.component').then(m => m.InventoryListComponent)
  },
  {
    path: 'add',
    loadComponent: () => import('./components/inventory-form/inventory-form.component').then(m => m.InventoryFormComponent)
  },
  {
    path: 'edit/:id',
    loadComponent: () => import('./components/inventory-form/inventory-form.component').then(m => m.InventoryFormComponent)
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class InventoryRoutingModule { }