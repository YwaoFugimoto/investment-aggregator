package com.crud.todo.service;

import com.crud.todo.controller.dto.CreateStockDto;
import com.crud.todo.controller.responseDto.StockResponseDto;
import com.crud.todo.entity.Stock;
import com.crud.todo.repository.StockRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockService {
    private StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public void createStock(CreateStockDto createStockDto) {

        // dto -> entity
        var stock = new Stock(
                createStockDto.stockId(),
                createStockDto.description()
        );

        stockRepository.save(stock);
    }

    public List<StockResponseDto> listStocks() {
        return stockRepository.findAll().stream().map(stock -> new StockResponseDto(
                stock.getStockId(),
                stock.getDescription()
        )).toList();
    }
}
