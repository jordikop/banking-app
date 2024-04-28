package com.backend.bankingapp.service;

import com.backend.bankingapp.accountMapper.AccountMapper;
import com.backend.bankingapp.dto.AccountDto;
import com.backend.bankingapp.entity.Account;
import com.backend.bankingapp.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.backend.bankingapp.accountMapper.AccountMapper.*;

@Service
public class AccountServiceImpl implements AccountService{
    @Autowired
    private AccountRepository accountRepository;
    @Override
    public AccountDto createAccount(AccountDto accountDto) {
       Account account = mapperToAccount(accountDto);
       accountRepository.save(account);
       return mapperToAccountDto(accountRepository.save(account));
    }

    @Override
    public AccountDto getAccountByid(Long id) {
      Account account=  accountRepository
              .findById(id)
              .orElseThrow(()->new RuntimeException("Account does not exists"));
        return mapperToAccountDto(account);
    }

    @Override
    public AccountDto deposit(Long id, double amount) {
        Account account=  accountRepository
                .findById(id)
                .orElseThrow(()->new RuntimeException("Account does not exists"));

        double total = account.getBalance()+ amount;
        account.setBalance(total);
        Account savedAccount = accountRepository.save(account);
        return mapperToAccountDto(savedAccount);
    }

    @Override
    public AccountDto withdraw(Long id, double amount) {
        Account account=  accountRepository
                .findById(id)
                .orElseThrow(()->new RuntimeException("Account does not exists"));
        if(account.getBalance()<amount){
            throw new  RuntimeException("Insufficient amount");
        }

        double total = account.getBalance()-amount;
        account.setBalance(total);
        Account savedAccount = accountRepository.save(account);
        return mapperToAccountDto(savedAccount);
    }

    @Override
    public List<AccountDto> getAllAccount() {
        List<Account> accounts = accountRepository.findAll();
        return accounts.stream().map((account)-> AccountMapper.mapperToAccountDto(account)).collect(Collectors.toList());

    }

    @Override
    public void deleteAccount(Long id) {
        Account account=  accountRepository
                .findById(id)
                .orElseThrow(()->new RuntimeException("Account does not exists"));
        accountRepository.delete(account);
    }
}
