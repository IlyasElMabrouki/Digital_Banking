package com.ilyaselmabrouki.digitalBanking;

import com.ilyaselmabrouki.digitalBanking.dtos.BankAccountDTO;
import com.ilyaselmabrouki.digitalBanking.dtos.CurrentBankAccountDTO;
import com.ilyaselmabrouki.digitalBanking.dtos.CustomerDTO;
import com.ilyaselmabrouki.digitalBanking.dtos.SavingBankAccountDTO;
import com.ilyaselmabrouki.digitalBanking.entities.*;
import com.ilyaselmabrouki.digitalBanking.enums.AccountStatus;
import com.ilyaselmabrouki.digitalBanking.enums.OperationType;
import com.ilyaselmabrouki.digitalBanking.exceptions.BalanceNotSufficientException;
import com.ilyaselmabrouki.digitalBanking.exceptions.BankAccountNotFoundException;
import com.ilyaselmabrouki.digitalBanking.exceptions.CustomerNotFoundException;
import com.ilyaselmabrouki.digitalBanking.repositories.AccountOperationRepository;
import com.ilyaselmabrouki.digitalBanking.repositories.BankAccountRepository;
import com.ilyaselmabrouki.digitalBanking.repositories.CustomerRepository;
import com.ilyaselmabrouki.digitalBanking.services.BankAccountServiceImpl;
import com.ilyaselmabrouki.digitalBanking.services.IBankAccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class DigitalBankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(DigitalBankingApplication.class, args);
	}

	@Bean
	public CommandLineRunner start(IBankAccountService bankAccountService) {
		return args -> {
			Stream.of("Ilyas", "Anass", "Hamza").forEach(name -> {
				CustomerDTO customer = new CustomerDTO();
				customer.setName(name);
				customer.setEmail(name.toLowerCase() + "@gmail.com");
				bankAccountService.saveCustomer(customer);
			});

			bankAccountService.getAllCustomers().forEach(customer -> {
				try {
					bankAccountService.saveCurrentBankAccount(100000,customer.getId(),9000);
					bankAccountService.saveSavingBankAccount(100000, customer.getId(), 40);

				} catch (CustomerNotFoundException e) {
					e.printStackTrace();
				}
            });

			List<BankAccountDTO> bankAccounts = bankAccountService.getAllBankAccounts();
			for (BankAccountDTO bankAccount : bankAccounts) {
				for(int i=0;i<10;i++){
					String accountId;
					if(bankAccount instanceof CurrentBankAccountDTO){
						accountId = ((CurrentBankAccountDTO) bankAccount).getId();
					}
					else{
						accountId = ((SavingBankAccountDTO) bankAccount).getId();
					}
					bankAccountService.credit(accountId, 100000, "Credit");
					bankAccountService.debit(accountId, 1000, "Debit");
				}
			}
		};
	}
}
