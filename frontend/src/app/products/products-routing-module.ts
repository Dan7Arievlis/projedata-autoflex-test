import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ProductListComponent } from './product-list-component/product-list-component';
import { ProductDetailsComponent } from './product-details-component/product-details-component';

const routes: Routes = [
  {
    path: '',
    component: ProductListComponent,
    data: {
      title: 'Lista'
    }
  },
  {
    path: ':id',
    component: ProductDetailsComponent,
    data: {
      title: 'Detalhes'
    }
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ProductsRoutingModule { }
