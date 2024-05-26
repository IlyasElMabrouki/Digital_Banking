import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AuthService} from "../services/auth.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit{

  loginformGroup! : FormGroup;
  constructor(private authService : AuthService, private fb: FormBuilder) {
  }

  ngOnInit(): void {
    this.loginformGroup = this.fb.group({
      username : this.fb.control(null, [Validators.required]),
      password : this.fb.control(null, [Validators.required])
    })
  }

  handlerLogin() : void {
    let username = this.loginformGroup.value.username;
    let password = this.loginformGroup.value.password;
    this.authService.login(username, password).subscribe({
      next: value => {
        console.log(value);
      },
      error: err => {
        console.log(err);
      }
    });
  }

}
