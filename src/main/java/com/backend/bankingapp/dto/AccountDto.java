package com.backend.bankingapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AccountDto {
    private Long id;
    private String accountHolderName;
     private double balance;
}
