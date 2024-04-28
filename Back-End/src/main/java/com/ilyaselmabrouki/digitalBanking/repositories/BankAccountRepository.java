package com.ilyaselmabrouki.digitalBanking.repositories;

import com.ilyaselmabrouki.digitalBanking.entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount, String> {
}
