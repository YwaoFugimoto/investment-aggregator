package com.crud.todo.entity;

import jakarta.persistence.*;


@Entity
@Table(name = "tb_stocks")
public class Stock {

    @Id
    @Column(name = "stock_id")
    private String stockId; //PETR4 M4G4L

    @Column(name = "description")
    private String description;

    public Stock(String stockId, String description) {
        this.stockId = stockId;
        this.description = description;
    }

    public String getstockId() {
        return stockId;
    }

    public void setstockId(String stockId) {
        this.stockId = stockId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Stock() {
    }
}
