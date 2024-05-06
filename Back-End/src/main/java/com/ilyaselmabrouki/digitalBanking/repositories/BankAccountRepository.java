package com.ilyaselmabrouki.digitalBanking.repositories;

import com.ilyaselmabrouki.digitalBanking.entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BankAccountRepository extends JpaRepository<BankAccount, String> {
    List<BankAccount> findByCustomer_Name(String keyword);
    List<BankAccount> findBankAccountsByCustomer_Id(Long customerId);
}
