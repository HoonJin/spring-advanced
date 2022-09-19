package com.example.spring.file.domain;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class ItemRepository {

    private final Map<Long, Item> store = new HashMap<>();
    private long sequence = 0L;

    public Item save(Item item) {
        Item newItem = new Item(++sequence, item.iteName(), item.attacheFile(), item.imageFiles());
        store.put(newItem.id(), newItem);
        return item;
    }

    public Item findById(Long id) {
        return store.get(id);
    }
}
