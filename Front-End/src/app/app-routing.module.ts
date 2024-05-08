import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {CustomersComponent} from "./customers/customers.component";
import {AccountsComponent} from "./accounts/accounts.component";
import {CustomerFormComponent} from "./customer-form/customer-form.component";
import {AccountFormComponent} from "./account-form/account-form.component";
import {LoginComponent} from "./login/login.component";

const routes: Routes = [
  { path: "login", component: LoginComponent},
  { path: "", redirectTo:"/login", pathMatch:"full"},
  { path: "customers", component: CustomersComponent},
  { path: "accounts/:id", component: AccountsComponent},
  { path: "new-customer", component: CustomerFormComponent},
  { path: "new-account/:id", component: AccountFormComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
