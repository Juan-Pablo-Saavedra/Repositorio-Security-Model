import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';

import { ProductsRoutingModule } from './products-routing.module';
import { ProductsListComponent } from './components/products-list.component';
import { ProductFormComponent } from './components/product-form.component';

@NgModule({
  declarations: [
    ProductsListComponent,
    ProductFormComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    RouterModule,
    ProductsRoutingModule
  ]
})
export class ProductsModule { }