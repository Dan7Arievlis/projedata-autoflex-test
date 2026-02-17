import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { InputsRoutingModule } from './inputs-routing-module';
import { InputComponent } from './input-component/input-list-component';
import { MatButtonModule } from '@angular/material/button';
import { MatLabel } from '@angular/material/select';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatCardModule } from '@angular/material/card';
import { MatTableModule } from '@angular/material/table';
import { ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from "@angular/material/icon";
import { InputDetailsComponent } from './input-details-component/input-details-component';
import { IfAdminDirective } from "../auth/if-admin-directive";


@NgModule({
  declarations: [
    InputComponent,
    InputDetailsComponent
  ],
  imports: [
    CommonModule,
    InputsRoutingModule,
    MatButtonModule,
    MatLabel,
    MatInputModule,
    MatPaginatorModule,
    MatCardModule,
    MatTableModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatIconModule,
    IfAdminDirective
]
})
export class InputsModule { }
