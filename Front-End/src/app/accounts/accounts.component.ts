import {Component, OnInit} from '@angular/core';
import {AccountService} from "../services/account.service";
import {catchError, Observable, throwError} from "rxjs";
import {Account} from "../models/account.model";
import {FormBuilder, FormGroup} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-accounts',
  templateUrl: './accounts.component.html',
  styleUrls: ['./accounts.component.css']
})
export class AccountsComponent implements OnInit {
  customerId! : number;
  accounts! : Observable<Array<Account>>;
  errorMessage! : string;
  searchFormGroup! : FormGroup;
  constructor(private accountService : AccountService,
              private fb : FormBuilder,
              private router: Router,
              private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    const idParam = this.route.snapshot.paramMap.get('id');
    if (idParam !== null) {
      this.customerId = parseInt(idParam, 10); // Parses the string to an integer
    }

    this.searchFormGroup = this.fb.group({
      keyword : this.fb.control("")
    });

    this.accounts = this.accountService.getAccounts(this.customerId).pipe(
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

  operationList(id: string){
    this.router.navigateByUrl("/operations/"+id);
  }

}
