package com.example.spring.db2.config;

import com.example.spring.db2.repository.ItemRepository;
import com.example.spring.db2.repository.jdbctemplate.JdbcTemplateItemRepositoryV1;
import com.example.spring.db2.repository.jdbctemplate.JdbcTemplateItemRepositoryV2;
import com.example.spring.db2.repository.memory.MemoryItemRepository;
import com.example.spring.db2.service.ItemService;
import com.example.spring.db2.service.ItemServiceV1;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class JdbcTemplateV1Config {

    private final DataSource dataSource;

    @Bean
    public ItemService itemService() {
        return new ItemServiceV1(itemRepository());
    }

    @Bean
    public ItemRepository itemRepository() {
        return new JdbcTemplateItemRepositoryV2(dataSource);
    }

}
