import { Component } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {OperationService} from "../services/operation.service";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-operation-form',
  templateUrl: './operation-form.component.html',
  styleUrls: ['./operation-form.component.css']
})
export class OperationFormComponent {
  operationFormGroup!: FormGroup;
  operationTypes: string[] = ['DEBIT', 'CREDIT', 'TRANSFER'];
  accountId!: string | null;

  constructor(private fb: FormBuilder,
              private operationService: OperationService,
              private router:Router,
              private route:ActivatedRoute) {}

  ngOnInit(): void {
    this.accountId = this.route.snapshot.paramMap.get('id');
    this.operationFormGroup = this.fb.group({
      type: ['', Validators.required],
      accountId: this.accountId,
      amount: [null],
      description: [''],
      destinationAccountId: ['']
    });

    this.operationFormGroup.get('type')?.valueChanges.subscribe(type => {
      if (type === 'DEBIT' || type === 'CREDIT') {
        this.operationFormGroup.get('amount')?.setValidators([Validators.required, Validators.min(0.01)]);
        this.operationFormGroup.get('description')?.setValidators([Validators.required]);
        this.operationFormGroup.get('destinationAccountId')?.clearValidators();
      } else if (type === 'TRANSFER') {
        this.operationFormGroup.get('destinationAccountId')?.setValidators([Validators.required]);
        this.operationFormGroup.get('amount')?.setValidators([Validators.required, Validators.min(0.01)]);
        this.operationFormGroup.get('description')?.clearValidators();
      }
      this.operationFormGroup.get('amount')?.updateValueAndValidity();
      this.operationFormGroup.get('description')?.updateValueAndValidity();
      this.operationFormGroup.get('destinationAccountId')?.updateValueAndValidity();
    });
  }

  handlerSubmit(): void {
    if (this.operationFormGroup.valid) {
      const formValue = this.operationFormGroup.value;
      const type = formValue.type;

      if (type === 'DEBIT') {
        const operationDTO = {
          accountId: formValue.accountId,
          amount: formValue.amount,
          description: formValue.description
        };
        this.operationService.debitOperation(operationDTO).subscribe(
          response => {
          console.log('Debit operation successful', response);
        });

      } else if (type === 'CREDIT') {
        const operationDTO = {
          accountId: formValue.accountId,
          amount: formValue.amount,
          description: formValue.description
        };
        this.operationService.creditOperation(operationDTO).
        subscribe(response => {
          console.log('Credit operation successful', response);
        });

      } else if (type === 'TRANSFER') {
        const transferDTO = {
          accountIdSource: formValue.accountId,
          accountIdDestination: formValue.destinationAccountId,
          amount: formValue.amount
        };
        this.operationService.transferOperation(transferDTO).
        subscribe(response => {
          console.log('Transfer operation successful', response);
        });
      }
      this.router.navigateByUrl("/operations/"+this.accountId);
    }
  }
}
