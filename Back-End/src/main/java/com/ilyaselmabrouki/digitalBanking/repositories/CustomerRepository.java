package com.ilyaselmabrouki.digitalBanking.repositories;

import com.ilyaselmabrouki.digitalBanking.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findByNameContains(String keyword);
}
