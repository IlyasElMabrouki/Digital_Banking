package com.ilyaselmabrouki.digitalBanking.web;

import com.ilyaselmabrouki.digitalBanking.dtos.*;
import com.ilyaselmabrouki.digitalBanking.exceptions.BankAccountNotFoundException;
import com.ilyaselmabrouki.digitalBanking.exceptions.CustomerNotFoundException;
import com.ilyaselmabrouki.digitalBanking.services.IBankAccountService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin("*")
public class BankAccountRestController {

    private IBankAccountService bankAccountService;

    @GetMapping("/accounts")
    public List<BankAccountDTO> getAllBankAccounts() {
        return bankAccountService.getAllBankAccounts();
    }

    @GetMapping("/accounts/{customerId}")
    public List<BankAccountDTO> getAllBankAccounts(@PathVariable Long customerId) {
        return bankAccountService.getCustomerBankAccounts(customerId);
    }

    @GetMapping("/accounts/search")
    public List<BankAccountDTO> searchAccounts(@RequestParam(name = "keyword", defaultValue = "") String keyword){
        return bankAccountService.searchAccounts(keyword);
    }

    @CrossOrigin("*")
    @PostMapping("/account/save")
    public BankAccountDTO saveAccount(@RequestBody AccountRequestDTO accountDTO) throws CustomerNotFoundException {
        if ("CurrentAccount".equals(accountDTO.getType())) {
            return bankAccountService.saveCurrentBankAccount(accountDTO.getBalance(), accountDTO.getCustomerId(), accountDTO.getOverDraft());
        } else {
            return bankAccountService.saveSavingBankAccount(accountDTO.getBalance(), accountDTO.getCustomerId(), accountDTO.getInterestRate());
        }
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
