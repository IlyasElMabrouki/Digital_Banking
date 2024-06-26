import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavbarComponent } from './navbar/navbar.component';
import { CustomersComponent } from './customers/customers.component';
import { AccountsComponent } from './accounts/accounts.component';
import {HttpClientModule} from "@angular/common/http";
import {ReactiveFormsModule} from "@angular/forms";
import { CustomerFormComponent } from './customer-form/customer-form.component';
import { AccountFormComponent } from './account-form/account-form.component';
import { LoginComponent } from './login/login.component';
import { OperationsComponent } from './operations/operations.component';
import { OperationFormComponent } from './operation-form/operation-form.component';

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    CustomersComponent,
    AccountsComponent,
    CustomerFormComponent,
    AccountFormComponent,
    LoginComponent,
    OperationsComponent,
    OperationFormComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
