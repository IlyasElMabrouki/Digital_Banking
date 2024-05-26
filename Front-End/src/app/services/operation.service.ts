import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Operation} from "../models/operation.model";
import {Account} from "../models/account.model";

@Injectable({
  providedIn: 'root'
})
export class OperationService {

  backendHost: string = "http://localhost:8080";

  constructor(private http : HttpClient) { }

  public getOperations(accountId : string) : Observable<Array<Operation>>{
    return this.http.get<Array<Operation>>(this.backendHost+"/account/"+accountId+"/operations");
  }

  public debitOperation(operation : any): Observable<Operation> {
    return this.http.post<Operation>(this.backendHost+"/account/debit", operation);
  }

  public creditOperation(operation : any): Observable<Operation> {
    return this.http.post<Operation>(this.backendHost+"/account/credit", operation);
  }

  public transferOperation(operation : any): Observable<Operation> {
    return this.http.post<Operation>(this.backendHost+"/account/transfer", operation);
  }

}
