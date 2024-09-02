import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavbarComponent } from './navbar/navbar.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { BreadcrumbComponent } from './breadcrumb/breadcrumb.component';
import { TableModule } from 'primeng/table';
import { PaginatorModule } from 'primeng/paginator';
import { RouterModule } from '@angular/router';
import { DialogModule } from 'primeng/dialog';
import { ButtonModule } from 'primeng/button';

@NgModule({
  declarations: [
    NavbarComponent, BreadcrumbComponent
  ],
  imports: [
    CommonModule, FormsModule, HttpClientModule, TableModule, PaginatorModule, RouterModule, ReactiveFormsModule, DialogModule, ButtonModule
  ],
  exports: [
    NavbarComponent, 
    BreadcrumbComponent, 
    CommonModule, 
    FormsModule, 
    HttpClientModule, 
    TableModule, 
    PaginatorModule,
    RouterModule, 
    ReactiveFormsModule,
    DialogModule,
    ButtonModule
  ]
})
export class SharedComponentsModule { }
