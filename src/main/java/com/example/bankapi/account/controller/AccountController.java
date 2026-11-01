package com.example.bankapi.account.controller;

import com.example.bankapi.account.dto.AccountResponse;
import com.example.bankapi.account.dto.AmountRequest;
import com.example.bankapi.account.dto.OpenAccountRequest;
import com.example.bankapi.account.entity.Account;
import com.example.bankapi.account.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name = "Accounts", description = "Account operations")
@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService
                                     accountService) {
        this.accountService = accountService;
    }

    @Operation(summary = "Open a new account")
    @PostMapping
    public ResponseEntity<AccountResponse> open(@Valid @RequestBody OpenAccountRequest request) {
        Long id = accountService.open(request.iban(), request.owner());
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(accountService.getById(id)));
    }

    @Operation(summary = "Get account by id")
    @GetMapping("/{id}")
    public AccountResponse get(@PathVariable Long id) {
        return toResponse(accountService.getById(id));
    }

    @GetMapping
    public List<AccountResponse> getAll() {
        return accountService.getAll().stream().map(this::toResponse).toList();
    }

    @PostMapping("/{id}/deposit")
    public void deposit(@PathVariable Long id, @Valid @RequestBody AmountRequest request) {
        accountService.deposit(id, request.amount());
    }

        @PostMapping("/{id}/withdraw")
    public void withdraw(@PathVariable Long id, @Valid @RequestBody AmountRequest request) {
        accountService.withdraw(id, request.amount());
    }

    @PostMapping("/{id}/block")
    public void block(@PathVariable Long id) {
        accountService.block(id);
    }

    private AccountResponse toResponse(Account account) {
        return new AccountResponse(
                account.getId(),
                account.getIban(),
                account.getOwner(),
                account.getBalance(),
                account.getStatus());
    }
}