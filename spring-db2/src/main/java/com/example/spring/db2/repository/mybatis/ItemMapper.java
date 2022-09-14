package com.example.spring.db2.repository.mybatis;

import com.example.spring.db2.domain.Item;
import com.example.spring.db2.repository.ItemSearchCond;
import com.example.spring.db2.repository.ItemUpdateDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface ItemMapper {

    void save(Item item);

    void update(@Param("id") Long id, @Param("updateParam")ItemUpdateDto updateDto);

    Optional<Item> findById(Long id);

    List<Item> findAll(ItemSearchCond itemSearchCond);
}
