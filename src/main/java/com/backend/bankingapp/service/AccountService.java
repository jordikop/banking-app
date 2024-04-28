package com.backend.bankingapp.service;

import com.backend.bankingapp.dto.AccountDto;
import com.backend.bankingapp.entity.Account;

import java.util.List;

public interface AccountService {

    AccountDto createAccount(AccountDto account);
    AccountDto getAccountByid(Long id);
    AccountDto deposit(Long id , double amount);
    AccountDto withdraw(Long id , double amount );
    List<AccountDto> getAllAccount ();
    void deleteAccount(Long id);
}
