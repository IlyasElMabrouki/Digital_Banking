package com.ilyaselmabrouki.digitalBanking.services;

import com.ilyaselmabrouki.digitalBanking.dtos.*;
import com.ilyaselmabrouki.digitalBanking.entities.*;
import com.ilyaselmabrouki.digitalBanking.enums.OperationType;
import com.ilyaselmabrouki.digitalBanking.exceptions.BalanceNotSufficientException;
import com.ilyaselmabrouki.digitalBanking.exceptions.BankAccountNotFoundException;
import com.ilyaselmabrouki.digitalBanking.exceptions.CustomerNotFoundException;
import com.ilyaselmabrouki.digitalBanking.mappers.BankAccountMapperImpl;
import com.ilyaselmabrouki.digitalBanking.repositories.AccountOperationRepository;
import com.ilyaselmabrouki.digitalBanking.repositories.BankAccountRepository;
import com.ilyaselmabrouki.digitalBanking.repositories.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class BankAccountServiceImpl implements IBankAccountService{

    private AccountOperationRepository accountOperationRepository;
    private CustomerRepository customerRepository;
    private BankAccountRepository bankAccountRepository;
    private BankAccountMapperImpl bankAccountMapper;

    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        Customer customer = bankAccountMapper.fromCustomerDTO(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);
        return bankAccountMapper.fromCustomer(savedCustomer);
    }

    @Override
    public CustomerDTO updateCustomer(CustomerDTO customerDTO) {
        Customer customer = bankAccountMapper.fromCustomerDTO(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);
        return bankAccountMapper.fromCustomer(savedCustomer);
    }

    @Override
    public void deleteCustomer(Long customerId) {
        customerRepository.deleteById(customerId);
    }

    @Override
    public CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(()->new CustomerNotFoundException("Customer Not Found"));
        return bankAccountMapper.fromCustomer(customer);
    }

    @Override
    public CurrentBankAccountDTO saveCurrentBankAccount(double initialBalance, Long customerId, double overDraft) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(customerId).orElse(null);
        if (customer == null)
            throw new CustomerNotFoundException("Customer not found");

        CurrentAccount currentAccount = new CurrentAccount();
        currentAccount.setId(UUID.randomUUID().toString());
        currentAccount.setBalance(initialBalance);
        currentAccount.setCreatedAt(new Date());
        currentAccount.setCustomer(customer);
        currentAccount.setOverDraft(overDraft);
        return bankAccountMapper.fromCurrentAccount(bankAccountRepository.save(currentAccount));
    }

    @Override
    public SavingBankAccountDTO saveSavingBankAccount(double initialBalance, Long customerId, double interestRate) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(customerId).orElse(null);
        if (customer == null)
            throw new CustomerNotFoundException("Customer not found");

        SavingAccount savingAccount = new SavingAccount();
        savingAccount.setId(UUID.randomUUID().toString());
        savingAccount.setBalance(initialBalance);
        savingAccount.setCreatedAt(new Date());
        savingAccount.setCustomer(customer);
        savingAccount.setInterestRate(interestRate);
        return bankAccountMapper.fromSavingAccount(bankAccountRepository.save(savingAccount));
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream()
                .map(customer -> bankAccountMapper.fromCustomer(customer))
                .collect(Collectors.toList());
    }

    @Override
    public BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException {
        BankAccount bankAccount = bankAccountRepository.findById(accountId)
                .orElseThrow(()-> new BankAccountNotFoundException("Bank Account Not Found"));
        if(bankAccount instanceof CurrentAccount)
            return bankAccountMapper.fromCurrentAccount((CurrentAccount) bankAccount);
        return bankAccountMapper.fromSavingAccount((SavingAccount) bankAccount);
    }

    @Override
    public List<BankAccountDTO> getAllBankAccounts() {
        List<BankAccount> bankAccounts = bankAccountRepository.findAll();
        return bankAccounts.stream()
                .map(bankAccount -> {
                    if(bankAccount instanceof CurrentAccount)
                        return bankAccountMapper.fromCurrentAccount((CurrentAccount) bankAccount);
                    return bankAccountMapper.fromSavingAccount((SavingAccount) bankAccount);
                })
                .collect(Collectors.toList());
    }

    @Override
    public AccountHistoryDTO getAccountHistory(String accountId, int page, int size) throws BankAccountNotFoundException {
        BankAccount bankAccount = bankAccountRepository.findById(accountId)
                .orElseThrow(()-> new BankAccountNotFoundException("Bank Account Not Found"));
        Page<AccountOperation> accountOperations = accountOperationRepository
                .findByBankAccount_Id(accountId, PageRequest.of(page,size));
        AccountHistoryDTO accountHistoryDTO = new AccountHistoryDTO();
        accountHistoryDTO.setAccountOperationDTOS(accountOperations.getContent().stream()
                .map(accountOperation -> bankAccountMapper.fromAccountOperation(accountOperation))
                .collect(Collectors.toList()));
        accountHistoryDTO.setAccountId(bankAccount.getId());
        accountHistoryDTO.setBalance(bankAccount.getBalance());
        accountHistoryDTO.setCurrentPage(page);
        accountHistoryDTO.setPageSize(size);
        accountHistoryDTO.setTotalPages(accountOperations.getTotalPages());
        return accountHistoryDTO;
    }

    @Override
    public List<AccountOperationDTO> getAccountHistory(String AccountId) {
        List<AccountOperation> accountOperations = accountOperationRepository.findByBankAccount_Id(AccountId);
        return accountOperations.stream()
                .map(accountOperation -> bankAccountMapper.fromAccountOperation(accountOperation))
                .collect(Collectors.toList());
    }

    @Override
    public void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientException {
        BankAccount bankAccount = bankAccountRepository.findById(accountId)
                .orElseThrow(()-> new BankAccountNotFoundException("Bank Account Not Found"));

        if (bankAccount.getBalance() < amount)
            throw new BalanceNotSufficientException("Balance not sufficient");

        AccountOperation accountOperation = new AccountOperation();
        accountOperation.setAmount(amount);
        accountOperation.setOperationDate(new Date());
        accountOperation.setDescription(description);
        accountOperation.setBankAccount(bankAccount);
        accountOperation.setType(OperationType.DEBIT);
        accountOperationRepository.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance()-amount);
        bankAccountRepository.save(bankAccount);
    }

    @Override
    public void credit(String accountId, double amount, String description) throws BankAccountNotFoundException {
        BankAccount bankAccount = bankAccountRepository.findById(accountId)
                .orElseThrow(()-> new BankAccountNotFoundException("Bank Account Not Found"));

        AccountOperation accountOperation = new AccountOperation();
        accountOperation.setAmount(amount);
        accountOperation.setOperationDate(new Date());
        accountOperation.setDescription(description);
        accountOperation.setBankAccount(bankAccount);
        accountOperation.setType(OperationType.CREDIT);
        accountOperationRepository.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance()+amount);
        bankAccountRepository.save(bankAccount);
    }

    @Override
    public void transfer(String accountIdSource, String accountIdDestination, double amount) throws BankAccountNotFoundException, BalanceNotSufficientException {
        this.debit(accountIdSource, amount,"Transfer to " + accountIdDestination);
        this.credit(accountIdDestination, amount, "Transfer from " + accountIdSource);
    }
}
