package com.crud.todo.controller;

import com.crud.todo.controller.dto.AccountStockDto;
import com.crud.todo.controller.dto.AccountStockResponseDto;
import com.crud.todo.entity.User;
import com.crud.todo.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/accounts")
public class AccountController {

    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/{accountId}/stocks")
    public ResponseEntity<User> associateStock(@PathVariable("accountId") String accountId,
                                               @RequestBody AccountStockDto accountStockDto) {

        accountService.associateStock(accountId, accountStockDto);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{accountId}/stocks")
    public ResponseEntity<List<AccountStockResponseDto>> associateStock(@PathVariable("accountId") String accountId) {

        var stocks = accountService.listStocks(accountId);

        return ResponseEntity.ok(stocks);
    }
}