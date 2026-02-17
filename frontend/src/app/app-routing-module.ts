import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { authGuard } from './core/guards/auth-guard';

const routes: Routes = [
  {
    path: '',
    redirectTo: 'auth/login',
    pathMatch: 'full'
  },

  {
    path: 'pages',
    loadChildren: () => import('./template/template-module').then(module => module.TemplateModule),
    canActivate: [ authGuard ]
  },

  {
    path: 'auth',
    loadChildren: () => import('./auth/auth-module').then(module => module.AuthModule)
  },

  {
    path: '**',
    redirectTo: 'auth/login'
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
