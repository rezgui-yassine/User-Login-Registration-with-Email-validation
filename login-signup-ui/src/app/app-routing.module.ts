import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {LoginComponent} from "./pages/login/login.component";
import {RegisterComponent} from "./pages/register/register.component";
import {ActivateAccountComponent} from "./pages/activate-account/activate-account.component";
import {DashboardComponent} from "./modules/dashboard/dashboard/dashboard.component";
import {MainDashboardComponent} from "./modules/dashboard/main-dashboard/main-dashboard.component";
import {SettingsComponent} from "./modules/dashboard/settings/settings.component";
import {UsersComponent} from "./modules/dashboard/users/users.component";
import {ProductsComponent} from "./modules/dashboard/products/products.component";
import {OrdersComponent} from "./modules/dashboard/orders/orders.component";

const routes: Routes = [
  {
    path: 'login',
    component:LoginComponent
  },
  {
    path:'register',
    component:RegisterComponent
  },
  {
    path:'activate-account',
    component:ActivateAccountComponent
  },
  {
    path: 'dashboard',
    component: MainDashboardComponent,
    children: [
      { path: '', component: DashboardComponent },
      { path: 'users', component: UsersComponent },
      { path: 'products', component: ProductsComponent },
      { path: 'orders', component: OrdersComponent },
      { path: 'settings', component: SettingsComponent }
    ]
  },
  { path: '', redirectTo: '/dashboard', pathMatch: 'full' }, // Default route
  { path: '**', redirectTo: '/dashboard' } // Wildcard route for a 404 page
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
