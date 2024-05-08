import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  backendHost: string = "http://localhost:8080";

  constructor(private http:HttpClient) { }

  public login(username: string, password: string){
    let options = {
      headers : new HttpHeaders().set("Content-Type", "application/x-www-form-urlencoded")
    }
    let params = new HttpParams().set("username", username).set("password", password);
    return this.http.post(this.backendHost+"/auth/login", params, options);
  }
}
