import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {CustomersComponent} from "./customers/customers.component";
import {AccountsComponent} from "./accounts/accounts.component";
import {CustomerFormComponent} from "./customer-form/customer-form.component";

const routes: Routes = [
  { path: "customers", component: CustomersComponent},
  { path: "accounts", component: AccountsComponent},
  { path: "new-customer", component: CustomerFormComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
