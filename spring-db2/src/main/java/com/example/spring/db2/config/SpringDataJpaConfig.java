package com.example.spring.db2.config;

import com.example.spring.db2.repository.ItemRepository;
import com.example.spring.db2.repository.jpa.JpaItemRepositoryV1;
import com.example.spring.db2.repository.jpa.JpaItemRepositoryV2;
import com.example.spring.db2.repository.jpa.SpringDataJpaItemRepository;
import com.example.spring.db2.service.ItemService;
import com.example.spring.db2.service.ItemServiceV1;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;

@Configuration
@RequiredArgsConstructor
public class SpringDataJpaConfig {

    private final SpringDataJpaItemRepository repository;

    @Bean
    public ItemService itemService() {
        return new ItemServiceV1(itemRepository());
    }

    @Bean
    public ItemRepository itemRepository() {
        return new JpaItemRepositoryV2(repository);
    }

}
