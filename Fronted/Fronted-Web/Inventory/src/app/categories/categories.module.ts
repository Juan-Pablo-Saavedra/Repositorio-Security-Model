import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';

// Componentes básicos
import { CategoriesListComponent } from './components/categories-list.component';
import { CategoryFormComponent } from './components/category-form.component';

@NgModule({
  declarations: [
    CategoriesListComponent,
    CategoryFormComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    RouterModule
  ]
})
export class CategoriesModule { }