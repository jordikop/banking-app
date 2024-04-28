package com.backend.bankingapp.controller;


import com.backend.bankingapp.dto.AccountDto;
import com.backend.bankingapp.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping
    public ResponseEntity<AccountDto> createAccount(@RequestBody AccountDto accountDto) {
        return new ResponseEntity<>(accountService.createAccount(accountDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDto> getAccount(@PathVariable Long id) {
        return ResponseEntity.ok(accountService.getAccountByid(id));
    }

    @PutMapping("/{id}/deposit")
    public ResponseEntity<AccountDto> deposit(@PathVariable Long id, @RequestBody Map<String, Double> request) {
        Double amount = request.get("amount");
        return ResponseEntity.ok(accountService.deposit(id, amount));
    }
    @PutMapping("/{id}/withdraw")
    public ResponseEntity<AccountDto> withdraw(@PathVariable Long id, @RequestBody Map<String, Double> request) {
        Double amount = request.get("amount");
        return ResponseEntity.ok(accountService.withdraw(id, amount));
    }
    @GetMapping("/listAccounts")
    public ResponseEntity<List<AccountDto>> getAllAccounts(){
    List<AccountDto> accountDtoList = accountService.getAllAccount();
    return ResponseEntity.ok(accountDtoList);
    }
    @DeleteMapping("/{id}/deleteAccount")
    public ResponseEntity<String> deleteAccount(@PathVariable Long id ){
    accountService.deleteAccount(id);
    return ResponseEntity.ok("Account is deleted successfully!");
    }


}
