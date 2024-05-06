import {Customer} from "./customer.model";

export interface Account {
  type: string;
  id: string;
  balance: number;
  createdAt: string;
  status: any;
  customerDTO: Customer;
  overDraft: number;
  interestRate: number;
}
