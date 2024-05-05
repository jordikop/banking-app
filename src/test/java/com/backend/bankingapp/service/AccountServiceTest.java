package com.backend.bankingapp.service;

import com.backend.bankingapp.dto.AccountDto;
import com.backend.bankingapp.entity.Account;
import com.backend.bankingapp.repository.AccountRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.backend.bankingapp.accountMapper.AccountMapper.mapperToAccount;
import static com.backend.bankingapp.accountMapper.AccountMapper.mapperToAccountDto;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;
    @InjectMocks
    private AccountServiceImpl accountService;

    @Test
    public void createAccount() {
        AccountDto accountDtoInput = new AccountDto(2L,"jordi kerol",30000.00);
        Account account = mapperToAccount(accountDtoInput );
        when(accountRepository.save(ArgumentMatchers.any(Account.class))).thenReturn(account);
        AccountDto accountDtoMapped= mapperToAccountDto(accountRepository.save(account));
        AccountDto accountDtoOutput = accountService.createAccount(accountDtoMapped);

        verify(accountRepository).save(account);
        assertEquals(accountDtoOutput.getAccountHolderName(),account.getAccountHolderName());

    }
    @Test
    public void getAllAccount() {
        List<Account> accounts = new ArrayList();
        accounts.add(new Account(2L,"jordi kerol",30000.00));
        accounts.add(new Account(3L,"heaven",50000.00));
        given(accountRepository.findAll()).willReturn(accounts);

        List  <AccountDto> expected = accountService.getAllAccount();
        assertEquals(expected.size(), accounts.size());
        assertEquals(expected.get(0).getId(), accounts.get(0).getId());
        assertEquals(expected.get(0).getAccountHolderName(), accounts.get(0).getAccountHolderName());
        assertEquals(expected.get(0).getBalance(), accounts.get(0).getBalance());

        assertEquals(expected.get(1).getId(), accounts.get(1).getId());
        assertEquals(expected.get(1).getAccountHolderName(), accounts.get(1).getAccountHolderName());
        assertEquals(expected.get(1).getBalance(), accounts.get(1).getBalance());

        verify(accountRepository).findAll();
    }
    @Test//(expected = RuntimeException.class)
    public void whenGivenId_shouldDeleteUser_ifFound(){
        Long accountId = 123L;

        Account account = new Account();

       when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
        accountService.deleteAccount(accountId);
        verify(accountRepository).delete  (account); /**/
    }

    @Test
    public void testDeleteAccount_WhenAccountDoesNotExist() {
        // 1. Prepare test data
        Long nonExistentAccountId = 999L;

        // 2. Stub the behavior of accountRepository
        when(accountRepository.findById(nonExistentAccountId)).thenReturn(Optional.empty());

        // 3. Call the method
        try {
            accountService.deleteAccount(nonExistentAccountId);
            fail("Expected RuntimeException");
        } catch (RuntimeException e) {
            // 4. Verify that the exception message is as expected
            assertEquals("Account does not exists", e.getMessage());
        }

        // 5. Verify that accountRepository.delete() was not called
        verify(accountRepository, never()).delete(any());
    }


    @Test
    public void testGetAccountById_WhenAccountExists() {
        // 1. Prepare test data
        Long accountId = 2L;
        Account dummyAccount = new Account(2L,"jordi kerol",30000.00); // Assuming Account is your entity class

        // 2. Stub the behavior of accountRepository
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(dummyAccount));
        AccountDto expectedAccountDto = mapperToAccountDto(dummyAccount);

        // 3. Call the method
        AccountDto result = accountService.getAccountByid(accountId);

        // 4. Verify that the result is as expected
        assertEquals(expectedAccountDto, result);
    }
    @Test
    public void testGetAccountById_WhenAccountDoesNotExist() {
        // 1. Prepare test data
        Long nonExistentAccountId = 999L;

        // Stub the behavior of accountRepository
        when(accountRepository.findById(nonExistentAccountId)).thenReturn(Optional.empty());

        // 2. Call the method
        try {
            accountService.getAccountByid(nonExistentAccountId);
            fail("Expected RuntimeException");
        } catch (RuntimeException e) {
            // 3. Verify that the exception message is as expected
            assertEquals("Account does not exists", e.getMessage());
        }
    }

  /*  @Test
    public void testDeposit() {
        // 1. Prepare test data
        Account dummyAccount = new Account(2L,"jordi kerol",30000.00);
        double depositAmount = 500.0;
        double expectedNewBalance = dummyAccount.getBalance() + depositAmount;

        dummyAccount.setBalance(expectedNewBalance);

        // Stub the behavior of accountRepository
        when(accountRepository.findById(2L)).thenReturn(Optional.of(dummyAccount));

        // Stub the behavior of mapperToAccountDto
        AccountDto expectedAccountDto = mapperToAccountDto(dummyAccount);

        // 2. Call the method
        AccountDto result =accountService.deposit(2L, depositAmount);

        // 3. Verify that the account's balance was updated correctly
        assertEquals(expectedNewBalance, dummyAccount.getBalance());

        // 4. Verify that the result is as expected
        assertEquals(expectedAccountDto, result);
    }*/

}
