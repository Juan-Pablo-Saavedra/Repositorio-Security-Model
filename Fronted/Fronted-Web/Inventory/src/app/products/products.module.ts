import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ProductsRoutingModule } from './products-routing.module';

// Módulo refactorizado para usar componentes standalone con lazy loading

@NgModule({
  imports: [CommonModule, FormsModule, RouterModule, ProductsRoutingModule],
  exports: [RouterModule]
})
export class ProductsModule { }