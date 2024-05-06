import {Component, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {CustomerService} from "../services/customer.service";
import {catchError, Observable, throwError} from "rxjs";
import {Customer} from "../models/customer.model";
import {FormBuilder, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-customers',
  templateUrl: './customers.component.html',
  styleUrls: ['./customers.component.css']
})
export class CustomersComponent implements OnInit{

    customers! : Observable<Array<Customer>>;
    errorMessage! : string;
    searchFormGroup! : FormGroup;
    constructor(private customerService : CustomerService, private fb : FormBuilder) {
    }
    ngOnInit(): void {
      this.searchFormGroup = this.fb.group({
        keyword : this.fb.control("")
      });
      this.customers = this.customerService.getCustomers().pipe(
        catchError(err => {
          this.errorMessage = err.message;
          return throwError(err);
        })
      )
    }

    handleSearchCustomers() {
      this.customers = this.customerService.searchCustomers(this.searchFormGroup.value.keyword).pipe(
        catchError(err => {
          this.errorMessage = err.message;
          return throwError(err);
        })
      )
    }
}
