import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LayoutComponent } from './layout/layout-component';

const routes: Routes = [
  {
    path: '',
    component: LayoutComponent,
    children: [
      {
        path: 'inputs',
        loadChildren: () => import('../inputs/inputs-module').then(module => module.InputsModule),
        data: {
          subtitle: 'Insumos',
          color: 'green'
        }
      },
      {
        path: 'products',
        loadChildren: () => import('../products/products-module').then(module => module.ProductsModule),
        data: {
          subtitle: 'Produtos',
          color: 'purple'
        }
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class TemplateRoutingModule { }
