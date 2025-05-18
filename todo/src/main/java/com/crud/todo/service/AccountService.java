package com.crud.todo.service;

import com.crud.todo.client.BrapiClient;
import com.crud.todo.controller.dto.AccountStockResponseDto;
import com.crud.todo.controller.dto.AccountStockDto;
import com.crud.todo.entity.AccountStock;
import com.crud.todo.entity.AccountStockId;
import com.crud.todo.repository.AccountRepository;
import com.crud.todo.repository.AccountStockRepository;
import com.crud.todo.repository.StockRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class AccountService {

//    @Value("#{environment.TOKEN}")
    private String TOKEN;
    private StockRepository stockRepository;
    private AccountRepository accountRepository;
    private AccountStockRepository accountStockRepository;
    private BrapiClient brapiClient;

    public AccountService(StockRepository stockRepository,
                          AccountRepository accountRepository,
                          AccountStockRepository accountStockRepository) {
        this.stockRepository = stockRepository;
        this.accountRepository = accountRepository;
        this.accountStockRepository = accountStockRepository;
    }

    @Transactional
    public void associateStock(String accountId, AccountStockDto accountStockDto) {

        var account = accountRepository.findById(UUID.fromString(accountId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account nao existe"));

        var stock = stockRepository.findById(accountStockDto.stockId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Stock nao existe"));

        var id = new AccountStockId(account.getAccountId(), stock.getStockId());

        var accountStockEntity = new AccountStock(id, account, stock, accountStockDto.quantity());

        accountStockRepository.save(accountStockEntity);
    }

    public List<AccountStockResponseDto> listStocks(String accountId) {

        var account = accountRepository.findById(UUID.fromString(accountId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account nao existe"));

        return account.getAccountStocks()
                .stream()
                .map(ac -> new AccountStockResponseDto(ac.getStock().getStockId(), ac.getQuantity(), getTotal(ac.getQuantity(), ac.getStock().getStockId())))
                .toList();

    }

    private double getTotal(int quantity, String stockId) {
        var response = brapiClient.getQuote(TOKEN, stockId);

        var price = response.results().getFirst().regularMarketPrice();

        return quantity * price;
    }
}
