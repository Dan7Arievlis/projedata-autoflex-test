import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { TemplateRoutingModule } from './template-routing-module';
import { LayoutComponent } from './layout/layout-component';
import { MatButtonModule } from '@angular/material/button';


@NgModule({
  declarations: [
    LayoutComponent
  ],
  imports: [
    CommonModule,
    TemplateRoutingModule,
    MatButtonModule
  ]
})
export class TemplateModule { }
