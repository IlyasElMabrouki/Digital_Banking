package com.ilyaselmabrouki.digitalBanking.dtos;

import lombok.Data;

@Data
public class OperationRequestDTO {
    private String accountId;
    private double amount;
    private String description;
}
