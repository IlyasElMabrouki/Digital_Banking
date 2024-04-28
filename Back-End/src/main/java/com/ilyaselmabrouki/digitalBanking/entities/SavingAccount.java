package com.ilyaselmabrouki.digitalBanking.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("SAV")
@Data @AllArgsConstructor @NoArgsConstructor
public class SavingAccount extends BankAccount{
    private double interestRate;
}
