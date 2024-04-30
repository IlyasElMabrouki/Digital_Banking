package com.ilyaselmabrouki.digitalBanking.web;

import com.ilyaselmabrouki.digitalBanking.dtos.AccountHistoryDTO;
import com.ilyaselmabrouki.digitalBanking.dtos.AccountOperationDTO;
import com.ilyaselmabrouki.digitalBanking.dtos.BankAccountDTO;
import com.ilyaselmabrouki.digitalBanking.entities.BankAccount;
import com.ilyaselmabrouki.digitalBanking.exceptions.BankAccountNotFoundException;
import com.ilyaselmabrouki.digitalBanking.services.IBankAccountService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class BankAccountRestController {

    private IBankAccountService bankAccountService;

    @GetMapping("/bankAccount/{accountId}")
    public BankAccountDTO getBankAccount(@PathVariable String accountId) throws BankAccountNotFoundException {
        return bankAccountService.getBankAccount(accountId);
    }

    @GetMapping("/bankAccounts")
    public List<BankAccountDTO> getAllBankAccounts() {
        return bankAccountService.getAllBankAccounts();
    }

    @GetMapping("/account/{accountId}/operations")
    public List<AccountOperationDTO> getAccountHistory(@PathVariable String accountId){
        return bankAccountService.getAccountHistory(accountId);
    }

    @GetMapping("/account/{accountId}/pageOperations")
    public AccountHistoryDTO getAccountHistory(@PathVariable String accountId,
                                               @RequestParam(name = "page", defaultValue = "0") int page,
                                               @RequestParam(name = "size", defaultValue = "5") int size) throws BankAccountNotFoundException {
        return bankAccountService.getAccountHistory(accountId, page, size);
    }
}
