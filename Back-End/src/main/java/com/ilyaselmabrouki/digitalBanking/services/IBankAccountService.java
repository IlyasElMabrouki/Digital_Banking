package com.ilyaselmabrouki.digitalBanking.services;

import com.ilyaselmabrouki.digitalBanking.dtos.BankAccountDTO;
import com.ilyaselmabrouki.digitalBanking.dtos.CurrentBankAccountDTO;
import com.ilyaselmabrouki.digitalBanking.dtos.CustomerDTO;
import com.ilyaselmabrouki.digitalBanking.dtos.SavingBankAccountDTO;
import com.ilyaselmabrouki.digitalBanking.entities.BankAccount;
import com.ilyaselmabrouki.digitalBanking.entities.CurrentAccount;
import com.ilyaselmabrouki.digitalBanking.entities.Customer;
import com.ilyaselmabrouki.digitalBanking.entities.SavingAccount;
import com.ilyaselmabrouki.digitalBanking.exceptions.BalanceNotSufficientException;
import com.ilyaselmabrouki.digitalBanking.exceptions.BankAccountNotFoundException;
import com.ilyaselmabrouki.digitalBanking.exceptions.CustomerNotFoundException;

import java.util.List;

public interface IBankAccountService {
    CustomerDTO saveCustomer(CustomerDTO customer);
    CustomerDTO updateCustomer(CustomerDTO customerDTO);
    void deleteCustomer(Long customerId);
    CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException;
    CurrentBankAccountDTO saveCurrentBankAccount(double initialBalance, Long customerId, double overDraft)
            throws CustomerNotFoundException;
    SavingBankAccountDTO saveSavingBankAccount(double initialBalance, Long customerId, double interestRate)
            throws CustomerNotFoundException;
    List<CustomerDTO> getAllCustomers();
    BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException;
    List<BankAccountDTO> getAllBankAccounts();
    void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientException;
    void credit(String accountId, double amount, String description) throws BankAccountNotFoundException;
    void transfer(String accountIdSource, String accountIdDestination, double amount) throws BankAccountNotFoundException, BalanceNotSufficientException;
}
