package com.example.spring.db2.repository.v2;

import com.example.spring.db2.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepositoryV2 extends JpaRepository<Item, Long> {
}
