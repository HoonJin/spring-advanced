package com.example.spring.db2.config;

import com.example.spring.db2.repository.ItemRepository;
import com.example.spring.db2.repository.jpa.JpaItemRepositoryV3;
import com.example.spring.db2.service.ItemService;
import com.example.spring.db2.service.ItemServiceV1;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;

@Configuration
@RequiredArgsConstructor
public class QueryDslConfig {

    private final EntityManager em;

    @Bean
    public ItemService itemService() {
        return new ItemServiceV1(itemRepository());
    }

    @Bean
    public ItemRepository itemRepository() {
        return new JpaItemRepositoryV3(em);
    }

}
