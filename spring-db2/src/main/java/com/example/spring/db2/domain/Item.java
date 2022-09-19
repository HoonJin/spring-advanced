package com.example.spring.db2.domain;

import lombok.Data;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "item_name", length = 20)
    private String itemName;

    private Integer price;

    private Integer quantity;

    public Item() {
    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
