import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {Customer} from "../models/customer.model";

@Injectable({
  providedIn: 'root'
})
export class CustomerService {

  backendHost: string = "http://localhost:8080";

  constructor(private http:HttpClient) {}

  public getCustomers(): Observable<Array<Customer>> {
    return this.http.get<Array<Customer>>(this.backendHost+"/customers")
  }

  public searchCustomers(keyword : string): Observable<Array<Customer>> {
    return this.http.get<Array<Customer>>(this.backendHost+"/customers/search?keyword="+keyword)
  }

  public saveCustomer(customer : Customer): Observable<Customer> {
    return this.http.post<Customer>(this.backendHost+"/customers/save", customer);
  }

  public deleteCustomer(id : number): Observable<any> {
    return this.http.delete(this.backendHost+"/customers/"+id);
  }
}
