package com.ilyaselmabrouki.digitalBanking.web;

import com.ilyaselmabrouki.digitalBanking.dtos.AccountHistoryDTO;
import com.ilyaselmabrouki.digitalBanking.dtos.AccountOperationDTO;
import com.ilyaselmabrouki.digitalBanking.dtos.BankAccountDTO;
import com.ilyaselmabrouki.digitalBanking.dtos.CustomerDTO;
import com.ilyaselmabrouki.digitalBanking.exceptions.BankAccountNotFoundException;
import com.ilyaselmabrouki.digitalBanking.services.IBankAccountService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin("*")
public class BankAccountRestController {

    private IBankAccountService bankAccountService;

    @GetMapping("/account/{accountId}")
    public BankAccountDTO getBankAccount(@PathVariable String accountId) throws BankAccountNotFoundException {
        return bankAccountService.getBankAccount(accountId);
    }

    @GetMapping("/accounts")
    public List<BankAccountDTO> getAllBankAccounts() {
        return bankAccountService.getAllBankAccounts();
    }

    @GetMapping("/accounts/search")
    public List<BankAccountDTO> searchAccounts(@RequestParam(name = "keyword", defaultValue = "") String keyword){
        return bankAccountService.searchAccounts(keyword);
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
