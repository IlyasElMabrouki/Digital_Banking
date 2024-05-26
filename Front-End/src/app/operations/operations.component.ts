import { Component } from '@angular/core';
import {catchError, Observable, throwError} from "rxjs";
import {Customer} from "../models/customer.model";
import {FormBuilder, FormGroup} from "@angular/forms";
import {CustomerService} from "../services/customer.service";
import {ActivatedRoute, Router} from "@angular/router";
import {OperationService} from "../services/operation.service";
import {Operation} from "../models/operation.model";

@Component({
  selector: 'app-operations',
  templateUrl: './operations.component.html',
  styleUrls: ['./operations.component.css']
})
export class OperationsComponent {

  operations! : Observable<Array<Operation>>;
  errorMessage! : string;
  accountId! : string;

  constructor(private operationService : OperationService, private route: ActivatedRoute) {
  }
  ngOnInit(): void {
    const idParam = this.route.snapshot.paramMap.get('id');
    if (idParam !== null) {
      this.accountId = idParam;
    }

    this.operations = this.operationService.getOperations(this.accountId).pipe(
      catchError(err => {
        this.errorMessage = err.message;
        return throwError(err);
      })
    )
  }

}
