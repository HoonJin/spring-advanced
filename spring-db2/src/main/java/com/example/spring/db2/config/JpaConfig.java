package com.example.spring.db2.config;

import com.example.spring.db2.repository.ItemRepository;
import com.example.spring.db2.repository.jpa.JpaItemRepositoryV1;
import com.example.spring.db2.repository.mybatis.ItemMapper;
import com.example.spring.db2.repository.mybatis.MyBatisItemRepository;
import com.example.spring.db2.service.ItemService;
import com.example.spring.db2.service.ItemServiceV1;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;

@Configuration
@RequiredArgsConstructor
public class JpaConfig {

    private final EntityManager em;

    @Bean
    public ItemService itemService() {
        return new ItemServiceV1(itemRepository());
    }

    @Bean
    public ItemRepository itemRepository() {
        return new JpaItemRepositoryV1(em);
    }

}
