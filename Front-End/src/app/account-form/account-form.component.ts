import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {CustomerService} from "../services/customer.service";
import {ActivatedRoute, Router} from "@angular/router";
import {AccountService} from "../services/account.service";

@Component({
  selector: 'app-account-form',
  templateUrl: './account-form.component.html',
  styleUrls: ['./account-form.component.css']
})
export class AccountFormComponent implements OnInit{

  accountFormGroup! : FormGroup;
  accountTypes = ['CurrentAccount', 'SavingAccount'];
  customerId! : string | null;
  constructor(private accountService : AccountService,
              private fb: FormBuilder,
              private router:Router,
              private route:ActivatedRoute) {
  }

  ngOnInit(): void {
    this.customerId = this.route.snapshot.paramMap.get('id');
    this.accountFormGroup = this.fb.group({
      customerId: [this.customerId],
      type: ['CurrentAccount', Validators.required],
      // Common fields
      balance: ['', Validators.required],
      // Specific fields for Current account
      overDraft: [''],
      // Specific fields for Saving account
      interestRate: ['']
    })
  }

  handlerSubmit() : void {
    let account = this.accountFormGroup.value;
    this.accountService.saveAccount(account).subscribe({
      next : value => {
        alert("Account has been saved!!")
        this.accountFormGroup.reset()
        this.router.navigateByUrl("/accounts/"+this.customerId);
      },
      error : err => {
        console.log(err);
      }
    })
  }
}
