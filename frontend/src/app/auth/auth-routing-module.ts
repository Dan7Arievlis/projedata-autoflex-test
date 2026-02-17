import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './pages/login-component/login-component';
import { RegisterComponent } from './pages/register-component/register-component';
import { OauthCallbackComponent } from './pages/oauth-callback-component/oauth-callback-component';
import { guestGuard } from '../core/guards/guest-guard';

const routes: Routes = [
  { 
    path: 'login',
    component: LoginComponent,
    canActivate: [guestGuard]
  },
  {
    path: 'register',
    component: RegisterComponent,
    canActivate: [guestGuard]
  },
  {
    path: 'callback',
    component: OauthCallbackComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AuthRoutingModule { }
