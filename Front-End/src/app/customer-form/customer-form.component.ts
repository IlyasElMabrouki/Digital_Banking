import {Component, OnInit} from '@angular/core';
import {CustomerService} from "../services/customer.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-customer-form',
  templateUrl: './customer-form.component.html',
  styleUrls: ['./customer-form.component.css']
})
export class CustomerFormComponent implements OnInit{

  customerformGroup! : FormGroup;
  constructor(private customerService : CustomerService, private fb: FormBuilder) {
  }

  ngOnInit(): void {
    this.customerformGroup = this.fb.group({
      name : this.fb.control(null, [Validators.required, Validators.minLength(4)]),
      email : this.fb.control(null, [Validators.required, Validators.email])
    })
  }

  handlerSubmit() : void {
    let customer = this.customerformGroup.value;
    this.customerService.saveCustomer(customer).subscribe({
      next : value => {
        alert("Customer has been saved!!")
      },
      error : err => {
        console.log(err);
      }
    })
  }

}
