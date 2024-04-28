package com.backend.bankingapp.accountMapper;

import com.backend.bankingapp.dto.AccountDto;
import com.backend.bankingapp.entity.Account;

public class AccountMapper {

    public static Account mapperToAccount (AccountDto accountDto){
        Account account = new Account(
                accountDto.getId(),
                accountDto.getAccountHolderName(),
                accountDto.getBalance());
        return account;
    }

    public static AccountDto mapperToAccountDto (Account account){
        AccountDto accountDto = new AccountDto(
                account.getId(),
                account.getAccountHolderName(),
                account.getBalance());
        return accountDto;
    }
}
