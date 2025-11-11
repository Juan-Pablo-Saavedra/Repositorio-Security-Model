import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SuppliersListComponent } from './components/suppliers-list.component';
import { SupplierFormComponent } from './components/supplier-form.component';

const routes: Routes = [
  {
    path: '',
    redirectTo: 'list',
    pathMatch: 'full'
  },
  {
    path: 'list',
    component: SuppliersListComponent
  },
  {
    path: 'create',
    component: SupplierFormComponent
  },
  {
    path: 'edit/:id',
    component: SupplierFormComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SuppliersRoutingModule { }