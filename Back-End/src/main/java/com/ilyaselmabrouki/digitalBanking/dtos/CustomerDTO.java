package com.ilyaselmabrouki.digitalBanking.dtos;

import com.ilyaselmabrouki.digitalBanking.entities.BankAccount;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
public class CustomerDTO {
    private Long id;
    private String name;
    private String email;
}
