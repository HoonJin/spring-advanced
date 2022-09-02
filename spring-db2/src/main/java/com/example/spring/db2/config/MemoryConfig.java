package com.example.spring.db2.config;

import com.example.spring.db2.repository.ItemRepository;
import com.example.spring.db2.repository.memory.MemoryItemRepository;
import com.example.spring.db2.service.ItemService;
import com.example.spring.db2.service.ItemServiceV1;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MemoryConfig {

    @Bean
    public ItemService itemService() {
        return new ItemServiceV1(itemRepository());
    }

    @Bean
    public ItemRepository itemRepository() {
        return new MemoryItemRepository();
    }

}
