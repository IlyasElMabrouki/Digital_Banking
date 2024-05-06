import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Account} from "../models/account.model";
import {Customer} from "../models/customer.model";

@Injectable({
  providedIn: 'root'
})
export class AccountService {

  backendHost: string = "http://localhost:8080";

  constructor(private http : HttpClient) { }

  public getAccounts(customerId : number) : Observable<Array<Account>>{
    return this.http.get<Array<Account>>(this.backendHost+"/accounts/"+customerId);
  }

  public searchAccounts(keyword : string): Observable<Array<Account>> {
    return this.http.get<Array<Account>>(this.backendHost+"/accounts/search?keyword="+keyword)
  }

  public saveAccount(account : any): Observable<Account> {
    return this.http.post<Account>(this.backendHost+"/account/save", account);
  }

}
