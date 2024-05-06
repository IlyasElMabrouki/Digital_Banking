import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Account} from "../models/account.model";

@Injectable({
  providedIn: 'root'
})
export class AccountService {

  backendHost: string = "http://localhost:8080";

  constructor(private http : HttpClient) { }

  public getAccounts() : Observable<Array<Account>>{
    return this.http.get<Array<Account>>(this.backendHost+"/accounts");
  }

  public searchAccounts(keyword : string): Observable<Array<Account>> {
    return this.http.get<Array<Account>>(this.backendHost+"/accounts/search?keyword="+keyword)
  }
}
