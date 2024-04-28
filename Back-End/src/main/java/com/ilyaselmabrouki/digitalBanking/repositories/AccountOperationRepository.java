package com.ilyaselmabrouki.digitalBanking.repositories;

import com.ilyaselmabrouki.digitalBanking.entities.AccountOperation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountOperationRepository extends JpaRepository<AccountOperation, Long> {
}
