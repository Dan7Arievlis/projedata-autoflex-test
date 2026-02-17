import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { InputComponent } from './input-component/input-list-component';
import { InputDetailsComponent } from './input-details-component/input-details-component';

const routes: Routes = [
  {
    path: '',
    component: InputComponent,
    data: {
      title: 'Cadastro'
    }
  },
  {
    path: ':id',
    component: InputDetailsComponent,
    data: {
      title: 'Detalhes'
    }
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class InputsRoutingModule { }
