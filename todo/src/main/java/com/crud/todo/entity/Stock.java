package com.crud.todo.entity;

import jakarta.persistence.*;

import java.util.UUID;
@Entity
@Table(name = "tb_stocks")
public class Stock {

    @Id
    @Column(name = "stock_id")
    private String stock_id; //PETR4 M4G4L

    @Column(name = "description")
    private String description;

    public Stock(String stock_id, String description) {
        this.stock_id = stock_id;
        this.description = description;
    }

    public String getStock_id() {
        return stock_id;
    }

    public void setStock_id(String stock_id) {
        this.stock_id = stock_id;
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
