package com.ilyaselmabrouki.digitalBanking.dtos;

import lombok.Data;

@Data
public class AccountRequestDTO {
    private double balance;
    private Long customerId;
    private double interestRate;
    private double overDraft;
    private String type;
}
