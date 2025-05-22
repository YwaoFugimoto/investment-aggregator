package com.crud.todo.controller;


import com.crud.todo.controller.dto.CreateStockDto;
import com.crud.todo.controller.dto.CreateUserDto;
import com.crud.todo.controller.responseDto.StockResponseDto;
import com.crud.todo.entity.User;
import com.crud.todo.service.StockService;
import feign.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1/stocks")
public class StockController {

    private StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }


    // create stock
    @PostMapping
    public ResponseEntity<Void> createStock (@RequestBody CreateStockDto createStockDto) {

        stockService.createStock(createStockDto);

        return ResponseEntity.ok().build();
    }

    // display all stocks
    @GetMapping
    public ResponseEntity<List<StockResponseDto>> listStocks (){

        var stocks = stockService.listStocks();

        return ResponseEntity.ok(stocks);
    }
}























