package com.ilyaselmabrouki.digitalBanking;

import com.ilyaselmabrouki.digitalBanking.entities.*;
import com.ilyaselmabrouki.digitalBanking.enums.AccountStatus;
import com.ilyaselmabrouki.digitalBanking.enums.OperationType;
import com.ilyaselmabrouki.digitalBanking.repositories.AccountOperationRepository;
import com.ilyaselmabrouki.digitalBanking.repositories.BankAccountRepository;
import com.ilyaselmabrouki.digitalBanking.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class DigitalBankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(DigitalBankingApplication.class, args);
	}

	@Bean
	public CommandLineRunner start(CustomerRepository customerRepository,
								   BankAccountRepository bankAccountRepository,
								   AccountOperationRepository accountOperationRepository) {
		return args -> {
			Stream.of("Ilyas", "Anass", "Hamza").forEach(name -> {
				Customer customer = new Customer();
				customer.setName(name);
				customer.setEmail(name + "@gmail.com");
				customerRepository.save(customer);
			});

			customerRepository.findAll().forEach(customer -> {
				CurrentAccount currentAccount = new CurrentAccount();
				currentAccount.setId(UUID.randomUUID().toString());
				currentAccount.setBalance(100000);
				currentAccount.setCreatedAt(new Date());
				currentAccount.setStatus(AccountStatus.CREATED);
				currentAccount.setCustomer(customer);
				currentAccount.setOverDraft(9000);
				bankAccountRepository.save(currentAccount);

				SavingAccount savingAccount = new SavingAccount();
				savingAccount.setId(UUID.randomUUID().toString());
				savingAccount.setBalance(100000);
				savingAccount.setCreatedAt(new Date());
				savingAccount.setStatus(AccountStatus.CREATED);
				savingAccount.setCustomer(customer);
				savingAccount.setInterestRate(5.5);
				bankAccountRepository.save(savingAccount);
			});

			bankAccountRepository.findAll().forEach(bankAccount -> {
				for (int i=0;i<5;i++){
					AccountOperation accountOperation = new AccountOperation();
					accountOperation.setAmount(Math.random() * 2000);
					accountOperation.setOperationDate(new Date());
					accountOperation.setType(Math.random() > 0.5 ? OperationType.DEBIT : OperationType.CREDIT);
					accountOperation.setBankAccount(bankAccount);
					accountOperationRepository.save(accountOperation);
				}
			});
		};
	}
}
