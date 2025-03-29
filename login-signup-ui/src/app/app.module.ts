import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {HttpClient, HttpClientModule} from "@angular/common/http";
import { LoginComponent } from './pages/login/login.component';
import {FormsModule} from "@angular/forms";
import { RegisterComponent } from './pages/register/register.component';
import { ActivateAccountComponent } from './pages/activate-account/activate-account.component';
import {CodeInputModule} from "angular-code-input";
import { BodyComponent } from './modules/dashboard/body/body.component';
import { SidenavComponent } from './modules/dashboard/sidenav/sidenav.component';
import { DashboardComponent } from './modules/dashboard/dashboard/dashboard.component';
import { SettingsComponent } from './modules/dashboard/settings/settings.component';
import { PagesComponent } from './modules/dashboard/pages/pages.component';
import { MainDashboardComponent } from './modules/dashboard/main-dashboard/main-dashboard.component';
import { UsersComponent } from './modules/dashboard/users/users.component';
import { ProductsComponent } from './modules/dashboard/products/products.component';
import { OrdersComponent } from './modules/dashboard/orders/orders.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    ActivateAccountComponent,
    BodyComponent,
    SidenavComponent,
    DashboardComponent,
    SettingsComponent,
    PagesComponent,
    MainDashboardComponent,
    UsersComponent,
    ProductsComponent,
    OrdersComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    CodeInputModule
  ],
  providers: [
    HttpClient
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
