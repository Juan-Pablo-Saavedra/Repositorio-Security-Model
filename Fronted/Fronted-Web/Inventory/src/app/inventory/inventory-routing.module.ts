import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { InventoryListComponent } from './components/inventory-list.component';
import { InventoryFormComponent } from './components/inventory-form.component';

const routes: Routes = [
  {
    path: '',
    redirectTo: 'list',
    pathMatch: 'full'
  },
  {
    path: 'list',
    component: InventoryListComponent
  },
  {
    path: 'add',
    component: InventoryFormComponent
  },
  {
    path: 'edit/:id',
    component: InventoryFormComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class InventoryRoutingModule { }