import {Component, OnInit} from '@angular/core';
import {AccountService} from "../services/account.service";
import {catchError, Observable, throwError} from "rxjs";
import {Account} from "../models/account.model";
import {FormBuilder, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-accounts',
  templateUrl: './accounts.component.html',
  styleUrls: ['./accounts.component.css']
})
export class AccountsComponent implements OnInit {

  accounts! : Observable<Array<Account>>;
  errorMessage! : string;
  searchFormGroup! : FormGroup;
  constructor(private accountService : AccountService, private fb : FormBuilder) {
  }

  ngOnInit(): void {
    this.searchFormGroup = this.fb.group({
      keyword : this.fb.control("")
    });
    this.accounts = this.accountService.getAccounts().pipe(
      catchError(err => {
        this.errorMessage = err.message;
        return throwError(err);
      })
    )
  }

  handleSearchCustomers() {
    this.accounts = this.accountService.searchAccounts(this.searchFormGroup.value.keyword).pipe(
      catchError(err => {
        this.errorMessage = err.message;
        return throwError(err);
      })
    )
  }

}
