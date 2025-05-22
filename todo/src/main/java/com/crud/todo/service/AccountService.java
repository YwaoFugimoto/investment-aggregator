package com.crud.todo.service;

import com.crud.todo.client.BrapiClient;
import com.crud.todo.controller.responseDto.AccountStockResponseDto;
import com.crud.todo.controller.dto.AssociateAccountStockDto;
import com.crud.todo.entity.AccountStock;
import com.crud.todo.entity.AccountStockId;
import com.crud.todo.repository.AccountRepository;
import com.crud.todo.repository.AccountStockRepository;
import com.crud.todo.repository.StockRepository;
import jakarta.transaction.Transactional;
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

    // associate a stock to an account
    @Transactional
    public void associateStock (String accountId, AssociateAccountStockDto associateAccountStockDto) {

        // finding account
        var account = accountRepository.findById(UUID.fromString(accountId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account does not exist"));

        // finding stock
        var stock = stockRepository.findById(associateAccountStockDto.stockId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Stock does not exist"));

        var id = new AccountStockId(account.getAccountId(), stock.getStockId());

        var accountStockEntity = new AccountStock(id, account, stock, associateAccountStockDto.quantity());

        accountStockRepository.save(accountStockEntity);
    }

    // list all stocks of an account
    public List<AccountStockResponseDto> listStocks (String accountId) {

        // finding account
        var account = accountRepository.findById(UUID.fromString(accountId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account does not exist"));

        return account.getAccountStocks()
                .stream()
                .map(accountStock -> new AccountStockResponseDto(
                        accountStock.getStock().getStockId(),
                        accountStock.getQuantity(),
                        0.0))
                .toList();

    }

    // get total what
//    private double getTotal(int quantity, String stockId) {
//        var response = brapiClient.getQuote(TOKEN, stockId);
//
//        var price = response.results().getFirst().regularMarketPrice();
//
//        return quantity * price;
//    }
}
